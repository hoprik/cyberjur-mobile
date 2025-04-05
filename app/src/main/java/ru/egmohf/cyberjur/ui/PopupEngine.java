package ru.egmohf.cyberjur.ui;

import android.app.Activity;
import android.widget.Toast;

import static androidx.core.content.ContextCompat.startActivity;

public class PopupEngine {
    private static PopupEngine INSTANCE;

    private Activity mainActivity;

    public PopupEngine(Activity mainActivity){
        INSTANCE = this;
        this.mainActivity = mainActivity;
    }

    public static PopupEngine getINSTANCE() {
        if (INSTANCE == null) {
            throw new NullPointerException("PopupEngine is not init");
        }
        return INSTANCE;
    }

    public void changeActivity(Activity newActivity){
        this.mainActivity = newActivity;
    }

    public void sendNotify(String message){
        mainActivity.runOnUiThread(() -> {
            Toast.makeText(this.mainActivity, message, Toast.LENGTH_LONG).show();
        });
    }

    public void reloadActivity(){
        mainActivity.finish();
        mainActivity.overridePendingTransition(0, 0);
        mainActivity.startActivity(mainActivity.getIntent());
        mainActivity.overridePendingTransition(0, 0);
    }

}
