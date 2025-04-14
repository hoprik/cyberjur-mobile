package ru.egmohf.cyberjur.api.helpers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import ru.egmohf.cyberjur.api.interfaces.User;

public class ApiSocket {
    private static final String SOCKET_URL = "wss://school.kiberlandia.ru";
    private static final String LOG_TAG = "SOCKET_API";
    private static ApiSocket instance;
    private Socket socket;

    private ApiSocket() {
        IO.Options options = new IO.Options();
        options.timeout = 5000;
        options.transports = new String[]{"websocket"};
        try {
            this.socket = IO.socket(SOCKET_URL, options);
            setupSocketListeners();
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, "Invalid URI: " + e.getMessage(), e);
        }
    }

    private void setupSocketListeners() {
        // Обработчик ошибки подключения
        socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            Exception e = (Exception) args[0];
            Log.e(LOG_TAG, "Connection error: " + e.getMessage(), e);
            scheduleReconnect();
        });


        // Обработчик разрыва соединения
        socket.on(Socket.EVENT_DISCONNECT, args -> {
            String reason = args.length > 0 ? (String) args[0] : "unknown";
            Log.w(LOG_TAG, "Disconnected. Reason: " + reason);
            scheduleReconnect();
        });

        // Обработчик успешного подключения
        socket.on(Socket.EVENT_CONNECT, args -> {
            Log.i(LOG_TAG, "Successfully connected to server");
        });
    }

    public static synchronized ApiSocket getInstance() {
        if (instance == null) {
            instance = new ApiSocket();
        }
        return instance;
    }

    public void connect(String userPublicId) {
        if (!socket.connected()) {
            Log.d(LOG_TAG, "Initiating connection...");

            // Добавляем однократный обработчик успешного подключения
            socket.on(Socket.EVENT_CONNECT, args -> {
                Log.d(LOG_TAG, "Post-connect operations");
                String sessionId = User.getSession_id();
                emit("userConnect", new UserConnectData(userPublicId, sessionId).toJson());
            });

            // Добавляем обработчик ошибки подключения
            socket.once(Socket.EVENT_CONNECT_ERROR, args -> {
                Exception e = (Exception) args[0];
                Log.e(LOG_TAG, "Final connection error: " + e.getMessage());
                scheduleReconnect();
            });

            socket.open();
            socket.connect();
        } else {
            // Повторная отправка данных, если уже подключены
            String sessionId = User.getSession_id();
            emit("userConnect", new UserConnectData(userPublicId, sessionId));
        }
    }

    public void connectWithoutUser() {
        Log.d(LOG_TAG, "Connecting without user...");
        socket.connect();
    }

    public void emit(String event, Object data) {
        Log.d(LOG_TAG, "Emitting event: " + event);
        socket.emit(event, data);
    }

    public void on(String event, Emitter.Listener listener) {
        Log.d(LOG_TAG, "Subscribing to event: " + event);
        socket.on(event, listener);
    }

    public void off(String event) {
        Log.d(LOG_TAG, "Unsubscribing from event: " + event);
        socket.off(event);
    }

    public void close() {
        Log.d(LOG_TAG, "Closing connection...");
        socket.disconnect();
    }

    private void scheduleReconnect() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (User.getMyProfile() != null) {
                this.connect(User.getMyProfile().getPublicId());
            }
        }, 5000); // Повторная попытка через 5 секунд
    }

    private static class UserConnectData {
        private String userPublicId;
        private String sessionId;

        public UserConnectData(String userPublicId, String sessionId) {
            this.userPublicId = userPublicId;
            this.sessionId = sessionId;
        }

        public JSONObject toJson() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("userPublicId", userPublicId);
                jsonObject.put("sessionId", sessionId);
                return jsonObject;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}