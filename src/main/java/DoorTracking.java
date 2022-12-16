import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DoorTracking {
    public static void main(String[] args ) throws MqttException, InterruptedException {

        ExecutorService threadpool = Executors.newFixedThreadPool(1) ;

        MQTTFacade m = new MQTTFacade(threadpool);
        m.subscribe();
        int i = 0;
        while (true) {

            Thread.sleep(1000);
            System.out.println(m.read());
            System.out.println(i++);
        }
    }
}
