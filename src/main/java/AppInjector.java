import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AppInjector extends AbstractModule {



    @Singleton
    @Provides
    public Queue<String> getQueue() {
        return new ConcurrentLinkedQueue<>();
    }


}
