import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

public class MQTTFacade {
    private static final String publisherId = UUID.randomUUID().toString();
    private final IMqttClient client;
    private final ExecutorService threadpool;

    private Queue<String> dataBuffer;

    public MQTTFacade(ExecutorService threadpool) throws MqttException {
        client = new MqttClient("tcp://localhost:1883",publisherId);

        dataBuffer = new ConcurrentLinkedQueue<>();
        this.threadpool = threadpool;

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect(options);
    }

    public void subscribe() throws MqttException {
        client.subscribe("topic/state", (topic, msg) -> {
            threadpool.submit(() -> {
                byte[] payload = msg.getPayload();
                dataBuffer.add(new String(payload, StandardCharsets.UTF_8));
            });
        });
    }

    public String read() {
        return dataBuffer.poll();
    }
}
