package ru.egmohf.cyberjur.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.ui.widget.CConstraintLayout;

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
            CConstraintLayout layout = (CConstraintLayout) inflater.inflate(R.layout.toast, mainActivity.findViewById(R.id.toast_root));
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

    public void sendNotify(String message) {
        sendNotify(message, "blue");
    }

    public void reloadActivity() {
        mainActivity.finish();
        mainActivity.overridePendingTransition(0, 0);
        mainActivity.startActivity(mainActivity.getIntent());
        mainActivity.overridePendingTransition(0, 0);
    }

}
