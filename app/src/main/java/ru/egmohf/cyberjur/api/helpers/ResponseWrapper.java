package ru.egmohf.cyberjur.api.helpers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import okhttp3.Response;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
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
        Gson gson = new GsonBuilder().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).
                registerTypeAdapterFactory(new SafeMapTypeAdapterFactory()).
                create();
        return gson.fromJson(json, responseType);
    }

    public static class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                // Преобразуем строку в LocalDateTime
                return LocalDateTime.parse(json.getAsString(), FORMATTER);
            } catch (Exception e) {
                throw new JsonParseException("Неверный формат даты: " + json.getAsString(), e);
            }
        }

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            // Преобразуем LocalDateTime обратно в строку
            return new JsonPrimitive(FORMATTER.format(src));
        }
    }

    public static class SafeMapTypeAdapterFactory implements TypeAdapterFactory {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() != Map.class) {
                return null;
            }

            TypeAdapter<Map> delegateAdapter =
                    (TypeAdapter<Map>) gson.getDelegateAdapter(this, type);

            return (TypeAdapter<T>) new TypeAdapter<Map>() {
                @Override
                public void write(JsonWriter out, Map value) throws IOException {
                    delegateAdapter.write(out, value);
                }

                @Override
                public Map read(JsonReader in) throws IOException {
                    Map<Object, Object> map = new LinkedHashMap<>();
                    in.beginObject();
                    while (in.hasNext()) {
                        String key = in.nextName();
                        Object value = gson.fromJson(in, Object.class);
                        // Игнорировать дубликаты или null-ключи
                        if (key != null && !map.containsKey(key)) {
                            map.put(key, value);
                        }
                    }
                    in.endObject();
                    return map;
                }
            };
        }
    }
}
