package ru.egmohf.cyberjur.saveData;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import androidx.lifecycle.MutableLiveData;
import com.jakewharton.disklrucache.DiskLruCache;
import ru.egmohf.cyberjur.ui.widget.image.BitmapCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cache {
    private static Cache instance = null;
    private final SharedPreferences sharedPreferences;
    private final Map<String, Object> tempCache;
    private LruCache<String, Bitmap> memoryCache;
    private DiskLruCache diskLruCache;

    private Cache(Activity activity) {
        this.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.tempCache = new HashMap<>();
        initBitmapTemp();
        initBitmapCache(activity.getApplicationContext());
    }

    private Cache(Activity activity, Map<String, Object> tempCache, LruCache<String, Bitmap> memoryCache) {
        this.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.tempCache = tempCache;
        this.memoryCache = memoryCache;
    }

    public Cache() {
        instance = this;
        this.sharedPreferences = null;
        this.tempCache = new HashMap<>();
        for (ConfigKeys key : ConfigKeys.values()) {
            tempCache.put(key.getKey(), key.getDefaultValue());
        }
        initBitmapTemp();
    }

    public static synchronized void init(Activity activity) {
        if (instance != null) {
            new Cache(activity, instance.tempCache, instance.memoryCache);
        }
        instance = new Cache(activity);
        for (ConfigKeys key : ConfigKeys.values()) {
            if (!instance.hasData(key.getKey())) {
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

    private void initBitmapTemp() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    private void initBitmapCache(Context context) {
        File cacheDir = new File(context.getCacheDir(), "bitmapCache");
        int DISK_CACHE_SIZE = 100 * 1024 * 1024;
        try {
            this.diskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void setData(String key, Bitmap bitmap) {
        memoryCache.put(key, bitmap);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            DiskLruCache.Editor editor = null;
            try {
                editor = diskLruCache.edit(key);
                OutputStream outputStream = editor.newOutputStream(0);
                boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();

                if (success) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String getStringData(String key, String defValue) {
        if (this.sharedPreferences == null || this.tempCache.containsKey(key)) {
            return (String) this.tempCache.getOrDefault(key, defValue);
        }
        this.tempCache.put(key, sharedPreferences.getString(key, defValue));
        return this.sharedPreferences.getString(key, defValue);
    }

    public Boolean getBooleanData(String key, boolean defValue) {
        if (this.sharedPreferences == null || this.tempCache.containsKey(key)) {
            return (boolean) this.tempCache.getOrDefault(key, defValue);
        }
        this.tempCache.put(key, sharedPreferences.getBoolean(key, defValue));
        return this.sharedPreferences.getBoolean(key, defValue);
    }

    public int getIntData(String key, int defValue) {
        if (this.sharedPreferences == null || this.tempCache.containsKey(key)) {
            return (int) this.tempCache.getOrDefault(key, defValue);
        }
        this.tempCache.put(key, sharedPreferences.getInt(key, defValue));
        return this.sharedPreferences.getInt(key, defValue);
    }

    public void getBitmapFromDisk(String key, BitmapCallback callback) {
        Bitmap bitmap = this.memoryCache.get(key);
        if (bitmap != null) {
            callback.onDone(bitmap);
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            DiskLruCache.Snapshot snapshot;
            try {
                snapshot = diskLruCache.get(key);
                if (snapshot == null) {
                    callback.onDone(null);
                    return;
                }

                InputStream inputStream = snapshot.getInputStream(0);
                Bitmap diskBitmap = BitmapFactory.decodeStream(inputStream);
                this.memoryCache.put(key, diskBitmap);
                callback.onDone(diskBitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public boolean hasData(String key) {
        if (this.sharedPreferences == null) {
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
