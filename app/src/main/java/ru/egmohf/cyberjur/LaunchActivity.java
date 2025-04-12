package ru.egmohf.cyberjur;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import ru.egmohf.cyberjur.api.SocketWrapper;
import ru.egmohf.cyberjur.api.interfaces.User;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.saveData.ConfigKeys;
import ru.egmohf.cyberjur.ui.popup.PopupEngine;
import ru.egmohf.cyberjur.ui.activities.LoginActivity;
import ru.egmohf.cyberjur.ui.activities.MainActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cache.init(this);
        new PopupEngine(this);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(Cache.getInstance().getStringData(ConfigKeys.BG_SECOND.getKey(), "")));
        if (User.login()) {
            SocketWrapper.init();
            runOnUiThread(() -> {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
        }else{
            runOnUiThread(() -> {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });
        }
    }
}
