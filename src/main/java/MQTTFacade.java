import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;

/**
 * Pass in a buffer and the thread pool, then you have control over everything.
 * The calling class then owns threading + data storage and you can swap Facade's easily
 */
public class MQTTFacade {
    private static final String publisherId = UUID.randomUUID().toString();
    private final IMqttClient client;
    private final IMqttMessageListener listener;

    private static final String topic = "topic/state";
    private static final String server = "tcp://localhost:1883";

    private final MqttConnectOptions options;


    public MQTTFacade(IMqttMessageListener listener) throws MqttException {
        client = new MqttClient(server, publisherId);

        this.listener  = listener;

        options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        client.connect(options);
    }

    public void subscribe() throws MqttException {
        client.subscribe(topic, listener);
    }

    public void close() {
        try {
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException("Unable to Close", e);
        }
    }
}
