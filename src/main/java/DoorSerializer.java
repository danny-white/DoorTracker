import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import java.time.format.DateTimeFormatter;

public class DoorSerializer {
    private final Gson gson;

    public DoorSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonSerializer<DoorData> serializer = (src, typeOfSrc, context) -> {
            JsonObject doorData = new JsonObject();
            doorData.addProperty("type", src.getType());
            doorData.addProperty("timestamp", src.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return doorData;
        };

        gsonBuilder.registerTypeAdapter(DoorData.class, serializer);
        gson = gsonBuilder.create();
    }

    public String serialize(DoorData d) {
        return gson.toJson(d);
    }
}
