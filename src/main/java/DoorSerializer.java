import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.json.JSONString;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DoorSerializer {
    private final Gson g;

    public DoorSerializer() {

        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonSerializer<DoorData> serializer = new JsonSerializer<DoorData>() {
            @Override
            public JsonElement serialize(DoorData src, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject doorData = new JsonObject();

                doorData.addProperty("type", src.getType());
                doorData.addProperty("timestamp", src.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                return doorData;
            }
        };
        gsonBuilder.registerTypeAdapter(DoorData.class, serializer);
        g = gsonBuilder.create();
    }

    public String serialize(DoorData d) {
        return g.toJson(d);
    }


}
