package ru.egmohf.cyberjur.api.interfaces;


import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import ru.egmohf.cyberjur.api.helpers.ApiHelper;
import ru.egmohf.cyberjur.api.helpers.ResponseCallback;
import ru.egmohf.cyberjur.api.helpers.ResponseWrapper;
import ru.egmohf.cyberjur.api.objects.UserObject;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.saveData.ConfigKeys;
import ru.egmohf.cyberjur.api.objects.UserObject.Customization.ThemeName;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private static String user_id = "";
    private static String session_id = "";
    private static UserObject myProfile;

    public static UserObject getMyProfile() {
        return myProfile;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static String getSession_id() {
        return session_id;
    }

    public static MutableLiveData<Boolean> login() {
        Cache cache = Cache.getInstance();
        Log.d("LOGIN_TEST", String.valueOf(cache.hasData(ConfigKeys.LOGIN.getKey())));
        Log.d("PASSWORD_TEST", String.valueOf(cache.hasData(ConfigKeys.LOGIN.getKey())));
        if (!cache.hasData(ConfigKeys.LOGIN.getKey()) || !cache.hasData(ConfigKeys.PASSWORD.getKey())) {
            return new MutableLiveData<>(false);
        }
        return login(cache.getStringData(ConfigKeys.LOGIN.getKey(), ""), cache.getStringData(ConfigKeys.PASSWORD.getKey(), ""));
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
                Cache.getInstance().setData(ConfigKeys.LOGIN.getKey(), usernameOrEmail);
                Cache.getInstance().setData(ConfigKeys.PASSWORD.getKey(), password);
                result.postValue(true);
            }

            @Override
            public void onFailure(ResponseWrapper wrapper) {
                result.postValue(false);
            }
        });
        return result;
    }

    public static void getOne() {
        ApiHelper.postToBack("user/getOne", "{}", new ResponseCallback() {
            @Override
            public void onSuccess(ResponseWrapper wrapper) {
                myProfile = wrapper.getParsedResponse(UserObject.class);
            }

            @Override
            public void onFailure(ResponseWrapper wrapper) {

            }
        });
    }

    public static void auth(){
        ApiHelper.postToBack("user/getOne", "{}", new ResponseCallback() {
            @Override
            public void onSuccess(ResponseWrapper wrapper) {
                myProfile = wrapper.getParsedResponse(UserObject.class, "user");
                Log.d("PROFILE",myProfile.toString());
                Map<String, String> elements = myProfile.getCustomization().getTheme();
                elements.forEach((k,v)->{
                    Cache.getInstance().setData(k.replace("--", "").replace("-color", ""),v);
                });
                user_id = wrapper.response.header("set-cookie").split("user_id=")[1].split(";")[0];
            }

            @Override
            public void onFailure(ResponseWrapper wrapper) {

            }
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

}
