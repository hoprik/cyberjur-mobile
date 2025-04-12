package ru.egmohf.cyberjur;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import ru.egmohf.cyberjur.api.helpers.ResponseWrapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class Utils {
    public static <T> T parse(String json, Class<T> responseType){
        Gson gson = new GsonBuilder().
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).
                registerTypeAdapterFactory(new SafeMapTypeAdapterFactory()).
                create();
        return gson.fromJson(json, responseType);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
