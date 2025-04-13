package ru.egmohf.cyberjur.api.interfaces;


import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import ru.egmohf.cyberjur.api.helpers.ApiHelper;
import ru.egmohf.cyberjur.api.helpers.ResponseCallback;
import ru.egmohf.cyberjur.api.helpers.ResponseWrapper;
import ru.egmohf.cyberjur.api.objects.UserObject;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.saveData.ConfigKeys;
import ru.egmohf.cyberjur.ui.popup.PopupEngine;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private static String user_id = "";
    private static String session_id = "";
    private static UserObject myProfile;
    private static UserCallback callBack;

    public static UserObject getMyProfile() {
        return myProfile;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static String getSession_id() {
        return session_id;
    }

    public static boolean login() {
        Cache cache = Cache.getInstance();
        if (!cache.getStringData(ConfigKeys.SESSION.getKey(), "").isEmpty() || !cache.getStringData(ConfigKeys.USER_ID.getKey(), "").isEmpty() ) {
            ResponseWrapper responseWrapper = new ResponseWrapper(cache.getStringData(ConfigKeys.USER_PROFILE.getKey(), ""), null);
            myProfile = responseWrapper.getParsedResponse(UserObject.class, "user");
            Cache.getInstance().setData(ConfigKeys.USER_PROFILE.getKey(),responseWrapper.getAnswer());
            updateTheme();
            session_id = cache.getStringData(ConfigKeys.SESSION.getKey(), "");
            user_id = cache.getStringData(ConfigKeys.USER_ID.getKey(), "");
            return true;
        }
        return false;
    }

    public static MutableLiveData<Boolean> login(String usernameOrEmail, String password) {
        String[] jsonInfo = validateProfile(usernameOrEmail, password);
        if (jsonInfo.length == 0) {
            return new MutableLiveData<>(false);
        }
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        ApiHelper.postToBack("user/login", String.format("{\"email\":\"%s\",\"login\":\"%s\",\"password\":\"%s\"}", jsonInfo[0], jsonInfo[1], jsonInfo[2]), new ResponseCallback() {
            @Override
            public void onSuccess(ResponseWrapper wrapper) {
                session_id = wrapper.response.header("set-cookie").split("session_id=")[1].split(";")[0];
                ApiHelper.postToBack("session/saveDevice", String.format("{\"password\":\"%s\"}", password), new ResponseCallback() {
                    @Override
                    public void onSuccess(ResponseWrapper wrapper) {

                    }

                    @Override
                    public void onFailure(ResponseWrapper wrapper) {

                    }
                });
                Cache.getInstance().setData(ConfigKeys.SESSION.getKey(), session_id);
                result.postValue(true);
            }

            @Override
            public void onFailure(ResponseWrapper wrapper) {
                result.postValue(false);
            }
        });
        return result;
    }

    public static void getOne(UserCallback callback) {
        callBack = callback;
        ApiHelper.postToBack("user/getOne", "{}", new ResponseCallback() {
            @Override
            public void onSuccess(ResponseWrapper wrapper) {
                myProfile = wrapper.getParsedResponse(UserObject.class, "user");
                Cache.getInstance().setData(ConfigKeys.USER_PROFILE.getKey(),wrapper.getAnswer());
                updateTheme();
                callback.onSuccess(myProfile);
            }

            @Override
            public void onFailure(ResponseWrapper wrapper) {
            }
        });
    }

    public static MutableLiveData<Void> auth(){
        MutableLiveData<Void> result = new MutableLiveData<>();
        ApiHelper.postToBack("user/getOne", "{}", new ResponseCallback() {
            @Override
            public void onSuccess(ResponseWrapper wrapper) {
                myProfile = wrapper.getParsedResponse(UserObject.class, "user");
                updateTheme();
                user_id = wrapper.response.header("set-cookie").split("user_id=")[1].split(";")[0];
                Cache.getInstance().setData(ConfigKeys.USER_PROFILE.getKey(),wrapper.getAnswer());
                Cache.getInstance().setData(ConfigKeys.USER_ID.getKey(),user_id);
                result.postValue(null);
            }

            @Override
            public void onFailure(ResponseWrapper wrapper) {
                result.postValue(null);
            }
        });
        return result;
    }

    private static void updateTheme(){
        Map<String, String> elements = myProfile.getCustomization().getTheme();
        elements.forEach((k,v)->{
            Cache.getInstance().setData(k.replace("--", "").replace("-color", ""), v);
        });
    }

    private static String[] validateProfile(String usernameOrEmail, String password) {
        if (password.isEmpty() || usernameOrEmail.isEmpty()) {
            return new String[]{};
        }
        Pattern pattern = Pattern.compile("/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)/");
        Matcher matcher = pattern.matcher(usernameOrEmail);
        if (matcher.find()) {
            return new String[]{usernameOrEmail, "", password};
        }
        return new String[]{"", usernameOrEmail, password};
    }

    public static void updateCallback() {
        if (callBack != null) {
            callBack.onSuccess(getMyProfile());
        }
    }

    public static void logout(){
        ApiHelper.postToBack("user/deleteSession", String.format("{sessionId:\"%s\"}", session_id), new ResponseCallback() {
            @Override
            public void onSuccess(ResponseWrapper wrapper) {
                Cache.getInstance().removeData(ConfigKeys.USER_PROFILE.getKey());
                Cache.getInstance().removeData(ConfigKeys.SESSION.getKey());
                Cache.getInstance().removeData(ConfigKeys.USER_ID.getKey());
                PopupEngine.getINSTANCE().sendNotify("Вы успешно вышли из аккаунта!", "red");
                PopupEngine.getINSTANCE().logout();
            }

            @Override
            public void onFailure(ResponseWrapper wrapper) {

            }
        });
    }

    public interface UserCallback {
        void onSuccess(UserObject profile);
    }

}
