import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DoorModule extends AbstractModule {
    private static final String server = "tcp://localhost:1883";
    private static final String publisherId = UUID.randomUUID().toString();

    private static final String address = "127.0.0.1";
    private static final int port = 6379;

    @Provides
    @Singleton
    public MqttClient getMqttClient() throws MqttException {
        return new MqttClient(server, publisherId);
    }

    @Provides
    @Singleton
    public MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }

    @Singleton
    @Provides
    public Queue<String> getQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    @Singleton
    @Provides
    public Jedis getJedis() {
        return new Jedis(address, port);
    }

    @Singleton
    @Provides
    public DoorSerializer getSerde() {
        return new DoorSerializer();
    }

    @Provides
    @Singleton
    @Inject
    public IMqttMessageListener getListener(Queue<String> dataBuffer) {
        return (__, msg) -> {
            byte[] payload = msg.getPayload();
            dataBuffer.add(new String(payload, StandardCharsets.UTF_8));
        };
    }

    @Provides
    @Singleton
    @Inject
    public MQTTFacade getMQTT(IMqttMessageListener listener, MqttConnectOptions options, MqttClient client) {
        MQTTFacade iotInput;
        try {
            iotInput = new MQTTFacade(listener, options, client);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to instantiate IOT Connection", e);
        }
        return iotInput;
    }

    @Singleton
    @Provides
    @Inject
    public RedisFacade getRedis(Jedis jedis) {
         return new RedisFacade(jedis);
    }

}
