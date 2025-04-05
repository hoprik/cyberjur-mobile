package ru.egmohf.cyberjur;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;


public class CyberJur extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
