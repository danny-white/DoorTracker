import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputProcessor {
    private final RedisFacade output;
    private final DoorSerializer serde;
    private static final String username = "Danewhi";
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Inject
    public OutputProcessor(RedisFacade output, DoorSerializer serde) {
        this.output = output;
        this.serde = serde;
    }

    /**
     * Annotate + write to storage
     */
    public void process(String item) {
        process(item, LocalDateTime.now());
    }

    /**
     * Annotate + write to storage
     */
    public void process(String item, LocalDateTime timestamp) {
        DoorData data = new DoorData(item, timestamp);
        String serdeData = serde.serialize(data);
        output.set(username +  ":" + data.getTimestamp().format(timeFormat), serdeData);
    }
}
