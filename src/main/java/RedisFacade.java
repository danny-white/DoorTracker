import com.google.inject.Inject;
import redis.clients.jedis.Jedis;

public class RedisFacade {
    //https://redis.io/docs/data-types/tutorial/
    private final Jedis jedis;

    @Inject
    public RedisFacade(Jedis jedis) {
        this.jedis = jedis;
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public void set(String key, String value) {
        jedis.set(key, value);
    }
}
