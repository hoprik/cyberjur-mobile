package ru.egmohf.cyberjur.api.objects;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class NotificationObject {
    private String message;
    private NotifyType type;
    private Action[] actions;
    private int timeout;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotifyType getType() {
        return type;
    }

    public void setType(NotifyType type) {
        this.type = type;
    }

    public Action[] getActions() {
        return actions;
    }

    public void setActions(Action[] actions) {
        this.actions = actions;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public enum NotifyType{
        @SerializedName("error") ERROR,
        @SerializedName("success") SUCCESS,
        @SerializedName("info") INFO
    }

    public static class Action{
        private NotificationCallbacks name;
        private Object data;

        public NotificationCallbacks getName() {
            return name;
        }

        public void setName(NotificationCallbacks name) {
            this.name = name;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public enum NotificationCallbacks{
            @SerializedName("inviteCubes") INVITE_CUBES,
            @SerializedName("cancelInviteCubes") CANCEL_INVITE_CUBES,
            @SerializedName("openEventCalendar") OPEN_EVENT_CALENDAR
        }

        @Override
        public String toString() {
            return "Action{" +
                    "name=" + name +
                    ", data=" + data +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NotificationObject{" +
                "message='" + message + '\'' +
                ", type=" + type +
                ", actions=" + Arrays.toString(actions) +
                ", timeout=" + timeout +
                '}';
    }
}
