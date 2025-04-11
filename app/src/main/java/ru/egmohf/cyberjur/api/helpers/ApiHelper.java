package ru.egmohf.cyberjur.api.helpers;

import android.util.Log;
import com.google.gson.*;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import ru.egmohf.cyberjur.api.interfaces.User;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.ui.popup.PopupEngine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiHelper {
    public static final String API = "https://school.kiberlandia.ru/api/";

    public static void post(String url, String json, ResponseCallback callback) {
        Cache cache = Cache.getInstance();
        MediaType JSON = MediaType.get("application/json");
        OkHttpClient client = new OkHttpClient.Builder().
                cache(cache.isWorkApiCache()? cache.getApiCache() : null).
                addNetworkInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxAge(365, TimeUnit.DAYS)
                            .build();
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl.toString())
                            .build();
                }).
                build();

        RequestBody body = RequestBody.create(json, JSON);

        Request cacheRequest = new Request.Builder()
                .header("Cookie", buildCookieHeader())
                .cacheControl(CacheControl.FORCE_CACHE)
                .url(url)
                .post(body)
                .build();


        Request netRequest = new Request.Builder()
                .header("Cookie", buildCookieHeader())
                .cacheControl(CacheControl.FORCE_NETWORK)
                .url(url)
                .post(body)
                .build();

        Callback reqCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(new ResponseWrapper(true, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body().string();

                    if (hasError(responseBody)) {
                        String error = getError(responseBody);
                        Log.e("API_ERROR", error);
                        callback.onFailure(new ResponseWrapper(true, error));
                    } else {
                        String data = getDataJson(responseBody);
                        callback.onSuccess(new ResponseWrapper(data, response));
                    }
                } catch (Exception e) {
                    PopupEngine.getINSTANCE().sendNotify(e.getMessage(), "red");
                    Log.e("API_ERROR", e.getMessage());
                    callback.onFailure(new ResponseWrapper(true, e.getMessage()));
                    throw new RuntimeException(e);
                }
            }
        };

        client.newCall(cacheRequest).enqueue(reqCallback);
        client.newCall(netRequest).enqueue(reqCallback);

    }

    public static void downloadImage(String imageUrl, ResponseCallback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.onSuccess(new ResponseWrapper("", response));
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("IMAGE_DOWNLOAD", "Failed to download image: " + e.getMessage());
            }
        });
    }

    private static String buildCookieHeader() {
        String userIdCookie = User.getUser_id().isEmpty() ?
                "" : String.format("user_id=%s;", User.getUser_id());
        String sessionCookie = User.getSession_id().isEmpty() ?
                "" : String.format("session_id=%s;", User.getSession_id());
        return userIdCookie + " " + sessionCookie;
    }

    public static void postToBack(String url, String json, ResponseCallback callback) {
        String fullUrl = API + url;
        post(fullUrl, json, callback);
    }

    public static <T> void postToBack(String url, Object parsedClass, ResponseCallback callback) {
        String fullUrl = API + url;
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        String json = gson.toJson(parsedClass);
        postToBack(fullUrl, json, callback);
    }

    private static boolean hasError(String json) {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.has("error");
        } catch (Exception e) {
            return true;
        }
    }

    private static String getDataJson(String json) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("data")) {
                    JsonElement dataElement = jsonObject.get("data");
                    if (dataElement.isJsonObject() || dataElement.isJsonArray()) {
                        return dataElement.toString();
                    } else if (dataElement.isJsonPrimitive()) {
                        return dataElement.getAsString();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }
            throw new JsonSyntaxException("Invalid JSON structure");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String getError(String json) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("error")) {
                    JsonElement errorElement = jsonObject.get("error");
                    if (errorElement.isJsonPrimitive()) {
                        return errorElement.getAsString();
                    } else if (errorElement.isJsonObject() || errorElement.isJsonArray()) {
                        return errorElement.toString();
                    } else {
                        return "Unknown error format";
                    }
                } else {
                    return "";
                }
            }
            throw new JsonSyntaxException("Invalid JSON structure");
        } catch (Exception e) {
            return e.getMessage();
        }
    }


}
