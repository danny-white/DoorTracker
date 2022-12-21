import com.google.inject.Guice;
import com.google.inject.Injector;

public class DoorTracking {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DoorModule());

        InputProcessor i = injector.getInstance(InputProcessor.class);
        OutputProcessor o = injector.getInstance(OutputProcessor.class);

        for (;;) {
            String item = i.read(500);
            if (item != null) {
                o.process(item);
            }
        }
    }
}
