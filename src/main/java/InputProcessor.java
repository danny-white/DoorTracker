import com.google.inject.Inject;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Queue;

/**
 * Interfaces with IOT facade and is responsible for Memory management of the inbound stream
 * DI
 */
public class InputProcessor {
    private final MQTTFacade iotInput;
    private final Queue<String> dataBuffer;

    /**
     * Blocking Method
     */
    @Inject
    public InputProcessor(Queue<String> dataBuffer, MQTTFacade iotInput) {
        this.dataBuffer = dataBuffer;
        this.iotInput = iotInput;
        try {
            iotInput.subscribe();
        } catch (MqttException e) {
            throw new RuntimeException("Failed to instantiate IOT Connection", e);
        }
    }

    //Todo, update this to read as many items as possible in tk msec.
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
