import redis.clients.jedis.Jedis;

public class RedisFacade {
    //https://redis.io/docs/data-types/tutorial/
    private final Jedis jedis;
    private static final String address = "127.0.0.1";
    private static final int port = 6379;

    public RedisFacade() {
        jedis = new Jedis(address, port);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public void set(String key, String value) {
        jedis.set(key, value);
    }
}
