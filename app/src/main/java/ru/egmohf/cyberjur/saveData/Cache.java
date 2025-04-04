package ru.egmohf.cyberjur.saveData;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Cache {
    private static Cache instance = null;
    private final SharedPreferences sharedPreferences;

    private Cache(Activity activity) {
        this.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public static synchronized void init(Activity activity) {
        if (instance != null) {
            throw new IllegalStateException("Cache already initialized");
        }
        instance = new Cache(activity);
        for (ConfigKeys key: ConfigKeys.values()) {
            if (instance.hasData(key.getKey())){
                instance.setData(key.getKey(), key.getDefaultValue());
            }

        }
    }

    public static Cache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Cache not initialized. Call Cache.init(activity) first.");
        }
        return instance;
    }

    public void setData(String key, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setData(String key, int value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public void setData(String key, Boolean value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringData(String key, String defValue) {
        return this.sharedPreferences.getString(key, defValue);
    }

    public Boolean getBooleanData(String key, boolean defValue) {
        return this.sharedPreferences.getBoolean(key, defValue);
    }

    public int getIntData(String key, int defValue) {
        return this.sharedPreferences.getInt(key, defValue);
    }

    public boolean hasData(String key){
        return this.sharedPreferences.contains(key);
    }

    public void removeData(String key) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clearData() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
