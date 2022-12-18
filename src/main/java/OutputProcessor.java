import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SimpleTimeZone;

/**
 * DI
 */
public class OutputProcessor {
    private final RedisFacade output = new RedisFacade();;
    private final DoorSerializer serde = new DoorSerializer();
    private static final String username = "Danewhi";
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public OutputProcessor() {
    }

    /**
     * Annotate + write to storage
     */
    public void process(String item) {
        DoorData data = new DoorData(item, LocalDateTime.now());
        String serdeData = serde.serialize(data);
        output.set(username +  ":" + data.getTimestamp().format(timeFormat), serdeData);
    }
}
