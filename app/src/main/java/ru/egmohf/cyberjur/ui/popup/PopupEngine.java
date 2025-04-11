package ru.egmohf.cyberjur.ui.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.api.objects.NotificationObject;
import ru.egmohf.cyberjur.ui.popup.modal.Modal;
import ru.egmohf.cyberjur.ui.popup.modal.ModalDialog;
import ru.egmohf.cyberjur.ui.popup.sidebar.Sidebar;
import ru.egmohf.cyberjur.ui.popup.sidebar.SidebarDialog;
import ru.egmohf.cyberjur.ui.sidebar.TestSide;
import ru.egmohf.cyberjur.ui.widget.CButton;
import ru.egmohf.cyberjur.ui.widget.CConstraintLayout;
import ru.egmohf.cyberjur.ui.widget.CLinerLayout;

public class PopupEngine {
    private static PopupEngine INSTANCE;

    private Activity mainActivity;

    public PopupEngine(Activity mainActivity) {
        INSTANCE = this;
        this.mainActivity = mainActivity;
    }

    public static PopupEngine getINSTANCE() {
        if (INSTANCE == null) {
            throw new NullPointerException("PopupEngine is not init");
        }
        return INSTANCE;
    }

    public void changeActivity(Activity newActivity) {
        this.mainActivity = newActivity;
    }

    @SuppressLint("RestrictedApi")
    public void sendNotify(String message, String color) {
        mainActivity.runOnUiThread(() -> {
            LayoutInflater inflater = mainActivity.getLayoutInflater();
            CConstraintLayout layout = (CConstraintLayout) inflater.inflate(R.layout.toast, mainActivity.findViewById(R.id.toast_root), false);
            layout.setBgColor(color);
            layout.setRound(40);

            ((TextView)layout.findViewById(R.id.toastText)).setText(message);
            Snackbar snackbar = Snackbar.make(mainActivity.findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG);
            Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
            snackbarView.setBackgroundColor(Color.TRANSPARENT);
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackbarView.getLayoutParams();
            params.gravity = Gravity.TOP;
            snackbarView.setLayoutParams(params);

            snackbarView.setPadding(0, 0, 0, 0);
            snackbarView.addView(layout, 0);
            snackbarView.setOnClickListener(v -> snackbar.dismiss());
            snackbar.show();
        });
    }

    @SuppressLint("RestrictedApi")
    public void sendNotify(NotificationObject notificationObject) {
        mainActivity.runOnUiThread(() -> {
            LayoutInflater inflater = mainActivity.getLayoutInflater();
            CConstraintLayout layout = (CConstraintLayout) inflater.inflate(R.layout.toast, mainActivity.findViewById(R.id.toast_root), false);
            if (notificationObject.getType() == null){
                layout.setBgColor("blue");
            }
            else{
                switch (notificationObject.getType()) {
                    case SUCCESS:
                        layout.setBgColor("green");
                        break;
                    case INFO:
                        layout.setBgColor("blue");
                        break;
                    case ERROR:
                        layout.setBgColor("red");
                        break;
                }
            }
            layout.setRound(40);

            CLinerLayout linerLayout = layout.findViewById(R.id.toastButtonRoot);
            for (NotificationObject.Action action: notificationObject.getActions()) {
                CButton button = getcButton(action);
                linerLayout.addView(button);
            }

            ((TextView)layout.findViewById(R.id.toastText)).setText(notificationObject.getMessage());
            Snackbar snackbar = Snackbar.make(mainActivity.findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG);
            Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
            snackbarView.setBackgroundColor(Color.TRANSPARENT);
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackbarView.getLayoutParams();
            params.gravity = Gravity.TOP;
            snackbarView.setLayoutParams(params);
            snackbar.setDuration(notificationObject.getTimeout());

            snackbarView.setPadding(0, 0, 0, 0);
            snackbarView.addView(layout, 0);
            snackbarView.setOnClickListener(v -> snackbar.dismiss());
            snackbar.show();
        });
    }

    private @NotNull CButton getcButton(NotificationObject.Action action) {
        CButton button = new CButton(mainActivity);
        button.setRound(15);
        button.setBgColor("#80000000");
        button.setPadding(5,5,5,5);
        switch (action.getName()){
            case INVITE_CUBES:
                button.setText("Играть");
                break;
            case CANCEL_INVITE_CUBES:
                button.setText("Отказать");
                break;
            case OPEN_EVENT_CALENDAR:
                button.setText("Открыть урок");
                break;
        }
        button.setOnClickListener(v ->{
            switch (action.getName()){
                case INVITE_CUBES:
                    Log.d("TODO", "Кнопка принять");
                    break;
                case CANCEL_INVITE_CUBES:
                    Log.d("TODO", "Кнопка откзать");
                    break;
                case OPEN_EVENT_CALENDAR:
                    Log.d("TODO", "Кнопка открыть урок");
                    break;
            }
        });
        return button;
    }

    public void sendNotify(String message) {
        sendNotify(message, "blue");
    }

    public void createSidebar(Sidebar sidebar){
        SidebarDialog dialog = new SidebarDialog(mainActivity, sidebar);
        dialog.show();
    }

    public void createModal(Modal modal){
        ModalDialog dialog = new ModalDialog(mainActivity, modal);
        dialog.show();
    }

    public void reloadActivity() {
        mainActivity.finish();
        mainActivity.overridePendingTransition(0, 0);
        mainActivity.startActivity(mainActivity.getIntent());
        mainActivity.overridePendingTransition(0, 0);
    }

}
