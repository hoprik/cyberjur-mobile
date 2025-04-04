package ru.egmohf.cyberjur.api.helpers;

import com.google.gson.*;

public class ApiHelper {


    private static String getError(String json) {;
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("error")) {
                    return jsonObject.getAsJsonObject("error").getAsString();
                } else {
                    return "";
                }
            }
            throw new JsonSyntaxException("Invalid JSON structure");
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String getDataJson(String json){
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("data")) {
                    return jsonObject.getAsJsonObject("data").getAsString();
                } else {
                    return "";
                }
            }
            throw new JsonSyntaxException("Invalid JSON structure");
        }catch (Exception e) {
            return e.getMessage();
        }
    }


}
