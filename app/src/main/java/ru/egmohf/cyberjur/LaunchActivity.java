package ru.egmohf.cyberjur;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.saveData.ConfigKeys;
import ru.egmohf.cyberjur.ui.PopupEngine;
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cache.init(this);
        new PopupEngine();
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(Cache.getInstance().getStringData(ConfigKeys.BG_SECOND.getKey(), "")));
//        if (Auth.getInstance().login() && Auth.getInstance().auth()) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        } else {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }

    }
}
