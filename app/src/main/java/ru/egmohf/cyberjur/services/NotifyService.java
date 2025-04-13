package ru.egmohf.cyberjur.services;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import org.json.JSONObject;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.Utils;
import ru.egmohf.cyberjur.api.helpers.ApiHelper;
import ru.egmohf.cyberjur.api.helpers.ApiSocket;
import ru.egmohf.cyberjur.api.interfaces.User;
import ru.egmohf.cyberjur.api.objects.NotificationObject;
import ru.egmohf.cyberjur.ui.activities.MainActivity;
import ru.egmohf.cyberjur.ui.popup.PopupEngine;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NotifyService extends Service {
    private static final String TAG = "NotifyService";
    private NotificationManager nm;
    private int notificationId;
    private ApiSocket apiSocket;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationId = new Random().nextInt(1000);
        Log.d(TAG, "Service created");

        // Инициализация сокета
        if (User.getMyProfile() != null) {
            apiSocket = ApiSocket.getInstance();
            apiSocket.connect(User.getMyProfile().getPublicId());
            setupSocketListeners();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Received intent: " + (intent != null ? intent.getAction() : "null"));

        if (intent != null && intent.getAction() != null) {
            handleAction(intent.getAction());
            return START_NOT_STICKY;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void handleAction(String action) {
        switch (action) {
            case "CANCEL_INVITE_CUBES":
                Log.d(TAG, "Canceling notification");
                nm.cancel(notificationId);
                break;
            // Добавьте обработку других действий при необходимости
        }
    }

    private void setupSocketListeners() {
        apiSocket.on("notifyUser", (event) -> {
            try {
                JSONObject jsonObject = (JSONObject) event[0];
                NotificationObject notice = Utils.parse(jsonObject.toString(), NotificationObject.class);

                if (notice == null || notice.getActions() == null) {
                    Log.e(TAG, "Invalid notification object");
                    return;
                }

                if (!isAppVisible()) {
                    sendNotif(notice);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error processing notification", e);
            }
        });

        apiSocket.on("notifyAll", (event) -> {
            try {
                JSONObject jsonObject = (JSONObject) event[0];
                NotificationObject notice = Utils.parse(jsonObject.toString(), NotificationObject.class);

                if (notice == null || notice.getActions() == null) {
                    Log.e(TAG, "Invalid notification object");
                    return;
                }

                if (!isAppVisible()) {
                    sendNotif(notice);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error processing notification", e);
            }
        });

        apiSocket.on("notifyPhone", (event) -> {
            try {
                JSONObject jsonObject = (JSONObject) event[0];
                NotificationObject notice = Utils.parse(jsonObject.toString(), NotificationObject.class);

                if (notice == null || notice.getActions() == null) {
                    Log.e(TAG, "Invalid notification object");
                    return;
                }

                if (!isAppVisible()) {
                    sendNotif(notice);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error processing notification", e);
            }
        });
    }

    private void sendNotif(NotificationObject notice) {
        String channelId = "notify_channel_" + notificationId;
        createNotificationChannel(channelId);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Cyberjur - уведомление")
                .setContentText(notice.getMessage())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 500, 200, 500})
                .setDefaults(Notification.DEFAULT_ALL);

        for (NotificationObject.Action action : notice.getActions()) {
            addNotificationAction(builder, action);
        }

        Notification notification = builder.build();
        nm.notify(notificationId, notification);
    }

    private void createNotificationChannel(String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Экстренные уведомления",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Важные оповещения");
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0, 500, 200, 500});
            nm.createNotificationChannel(channel);
        }
    }

    private void addNotificationAction(NotificationCompat.Builder builder, NotificationObject.Action action) {
        switch (action.getName()) {
            case INVITE_CUBES:
                builder.addAction(createActionIntent("Играть", MainActivity.class, "INVITE_CUBES", 1));
                break;
            case CANCEL_INVITE_CUBES:
                builder.addAction(createServiceActionIntent("Отказать", NotifyService.class, "CANCEL_INVITE_CUBES", 2));
                break;
            case OPEN_EVENT_CALENDAR:
                builder.addAction(createActionIntent("Открыть урок", MainActivity.class, "OPEN_EVENT_CALENDAR", 3));
                break;
        }
    }

    private NotificationCompat.Action createActionIntent(String title, Class<?> cls, String action, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );
        return new NotificationCompat.Action(0, title, pendingIntent);
    }

    private NotificationCompat.Action createServiceActionIntent(String title, Class<?> cls, String action, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );
        return new NotificationCompat.Action(0, title, pendingIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static boolean isAppVisible() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                || appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
    }
}