import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoorSerializerTest {
    DoorSerializer d = new DoorSerializer();

    @Test
    public void test() {
        DoorData data = new DoorData("Unlock", LocalDateTime.of(2022, 12, 17, 21, 40, 11));
        assertEquals("{\"type\":\"Unlock\",\"timestamp\":\"2022-12-17T21:40:11\"}",d.serialize(data));
    }
}