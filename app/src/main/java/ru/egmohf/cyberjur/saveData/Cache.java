package ru.egmohf.cyberjur.saveData;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private static Cache instance = null;
    private final SharedPreferences sharedPreferences;
    private final Map<String, Object> tempCache;

    private Cache(Activity activity) {
        this.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.tempCache = new HashMap<>();
    }

    private Cache(Activity activity, Map<String, Object> tempCache) {
        this.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.tempCache = tempCache;
    }

    public Cache(){
        instance = this;
        this.sharedPreferences = null;
        this.tempCache = new HashMap<>();
        for (ConfigKeys key: ConfigKeys.values()) {
            tempCache.put(key.getKey(), key.getDefaultValue());
        }
    }

    public static synchronized void init(Activity activity) {
        if (instance != null) {
            new Cache(activity, instance.tempCache);
        }
        instance = new Cache(activity);
        for (ConfigKeys key: ConfigKeys.values()) {
            if (!instance.hasData(key.getKey())){
                instance.setData(key.getKey(), key.getDefaultValue());
            }
        }
    }

    public static Cache getInstance() {
        if (instance == null) {
            new Cache();
        }
        return instance;
    }

    public void setData(String key, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        this.tempCache.put(key, value);
    }

    public void setData(String key, int value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
        this.tempCache.put(key, value);
    }


    public void setData(String key, Boolean value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        this.tempCache.put(key, value);
    }

    public String getStringData(String key, String defValue) {
        if (this.sharedPreferences == null){
            return (String) this.tempCache.getOrDefault(key, defValue);
        }
        return this.sharedPreferences.getString(key, defValue);
    }

    public Boolean getBooleanData(String key, boolean defValue) {
        if (this.sharedPreferences == null){
            return (boolean) this.tempCache.getOrDefault(key, defValue);
        }
        return this.sharedPreferences.getBoolean(key, defValue);
    }

    public int getIntData(String key, int defValue) {
        if (this.sharedPreferences == null){
            return (int) this.tempCache.getOrDefault(key, defValue);
        }
        return this.sharedPreferences.getInt(key, defValue);
    }

    public boolean hasData(String key){
        if (this.sharedPreferences == null){
            return this.tempCache.containsKey(key);
        }
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
