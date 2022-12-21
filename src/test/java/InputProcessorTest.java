import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputProcessorTest {
    Queue<String> dataBuffer = new LinkedList<>();
    MQTTFacade iotInput = Mockito.mock(MQTTFacade.class);
    InputProcessor ip = new InputProcessor(dataBuffer, iotInput);

    @Test
    void testRead() {
        dataBuffer.add("test");
        assertEquals("test", ip.read(500));
    }

    @Test
    void testReadTimeout() {
        long start = System.currentTimeMillis();
        ip.read(500);
        assertTrue(System.currentTimeMillis() - start > 450);
    }

    @Test
    void testReadDelay() {
        Executors.newFixedThreadPool(1).submit(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException("Test interrupted", e);
            }
            dataBuffer.add("test");
        });
        assertEquals("test" , ip.read(1000));
    }
}