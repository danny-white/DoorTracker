import com.google.inject.Inject;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Pass in a buffer and the thread pool, then you have control over everything.
 * The calling class then owns threading + data storage and you can swap Facade's easily
 */
public class MQTTFacade {
    private final IMqttMessageListener listener;
    private static final String topic = "topic/state";
    private final IMqttClient client;

    @Inject
    public MQTTFacade(IMqttMessageListener listener, MqttConnectOptions options, MqttClient client) throws MqttException {
        this.client = client;
        client.connect(options);
        this.listener = listener;
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
