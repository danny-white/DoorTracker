import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DoorTracking {
    public static void main(String[] args ) throws MqttException, InterruptedException {
        Injector injector = Guice.createInjector(new AppInjector());

        ExecutorService threadpool = Executors.newFixedThreadPool(1) ;
        //why does this line not blcok anymore? I thought it

        InputProcessor i = injector.getInstance(InputProcessor.class);
        OutputProcessor o = injector.getInstance(OutputProcessor.class);

        for (;;) {
            String item = i.read(500);
            if (item != null) {
                o.process(item);
            }
        }

        /**
         * Overall is set up
         * Ingest
         * Format
         * Log
         * Write
         */
    }
}
