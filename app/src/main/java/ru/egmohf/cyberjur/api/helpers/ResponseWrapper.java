package ru.egmohf.cyberjur.api.helpers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import okhttp3.Response;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import ru.egmohf.cyberjur.Utils;
import ru.egmohf.cyberjur.api.objects.UserObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseWrapper {
    public final boolean isError;
    public final String errorMessage;

    public final String answer;
    public final Response response;

    public ResponseWrapper(boolean isError, String errorMessage) {
        this.isError = isError;
        this.errorMessage = errorMessage;
        this.answer = "";
        this.response = null;
    }

    public ResponseWrapper(String answer, Response response) {
        this.answer = answer;
        this.isError = false;
        this.errorMessage = "";
        this.response = response;
    }

    private static String getDataJson(String json, String fromStart) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has(fromStart)) {
                    JsonElement dataElement = jsonObject.get(fromStart);
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

    public boolean isError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getAnswer() {
        return answer;
    }

    public Response getResponse() {
        return response;
    }

    public <T> T getParsedResponse(Class<T> responseType) {
        return getParsedResponse(this.answer, responseType);
    }

    public <T> T getParsedResponse(Class<T> responseType, String fromStart) {
        return getParsedResponse(getDataJson(this.answer, fromStart), responseType);
    }

    public <T> T getParsedResponse(String json, Class<T> responseType) {
        return Utils.parse(json, responseType);
    }

}
