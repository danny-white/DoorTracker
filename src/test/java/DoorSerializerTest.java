import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DoorSerializerTest {
    DoorSerializer d = new DoorSerializer();

    @Test
    public void test() {
        DoorData data = new DoorData("asd", LocalDateTime.now());
        System.out.println(d.serialize(data));
    }

}