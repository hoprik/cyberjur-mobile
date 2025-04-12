package ru.egmohf.cyberjur.api;

import android.util.Log;
import org.json.JSONObject;
import ru.egmohf.cyberjur.Utils;
import ru.egmohf.cyberjur.api.helpers.ApiSocket;
import ru.egmohf.cyberjur.api.interfaces.User;
import ru.egmohf.cyberjur.api.objects.NotificationObject;
import ru.egmohf.cyberjur.api.objects.UserObject;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.ui.popup.PopupEngine;

public class SocketWrapper {
    private static SocketWrapper wrapper;
    public SocketWrapper() {
        wrapper = this;
        ApiSocket.getInstance().connect(User.getMyProfile().getPublicId());
        register();
    }

    public static void init() {
        if (wrapper != null) {
            throw new RuntimeException("Double socket initialization");
        }
        new SocketWrapper();
    }

    public void register(){
        ApiSocket socket = ApiSocket.getInstance();

        socket.on("notifyUser", (event) -> {
            JSONObject jsonObject = (JSONObject) event[0];
            NotificationObject notice = Utils.parse(jsonObject.toString(), NotificationObject.class);
            PopupEngine.getINSTANCE().sendNotify(notice);
        });

        socket.on("notifyAll", (event) -> {
            JSONObject jsonObject = (JSONObject) event[0];
            NotificationObject notice = Utils.parse(jsonObject.toString(), NotificationObject.class);
            PopupEngine.getINSTANCE().sendNotify(notice);
        });

        socket.on("newAchievement", (event) -> {
            PopupEngine.getINSTANCE().sendNotify("Вы получили новое достижение!");
        });

        socket.on("closeSession", (event) -> {
            // TODO: Выход пользователя из приложения
        });
    }

}
