import com.google.inject.Inject;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Interfaces with IOT facade and is responsible for Memory management of the inbound stream
 * DI
 */
public class InputProcessor {
    MQTTFacade iotInput;

    private Queue<String> dataBuffer;

    private final IMqttMessageListener listener = (__, msg) -> {
        byte[] payload = msg.getPayload();
        dataBuffer.add(new String(payload, StandardCharsets.UTF_8));
    };

    /**
     * Blocking Method
     */
    @Inject
    public InputProcessor(Queue<String> dataBuffer) {
        this.dataBuffer = dataBuffer;
        System.out.println("val: " + dataBuffer);
        System.out.println("class: " + dataBuffer.getClass());

        dataBuffer = new ConcurrentLinkedQueue<>();

        try {
            iotInput = new MQTTFacade(listener);
            iotInput.subscribe();
        } catch (MqttException e) {
            throw new RuntimeException("Failed to instantiate IOT Connection", e);
        }
    }

    public String read(int timeoutMillis) {
        long starttime = System.currentTimeMillis();
        while (System.currentTimeMillis() < starttime + timeoutMillis) {
            if (!dataBuffer.isEmpty()) {
                return dataBuffer.poll();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }

}
