import com.google.gson.Gson;

import java.time.LocalDateTime;

/**
 * Door object POJO
 */
public class DoorData {
    private final String type;
    private final LocalDateTime timestamp;

    public String getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public DoorData(String type, LocalDateTime timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }
}
