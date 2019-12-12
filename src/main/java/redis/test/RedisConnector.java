package redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ZAddParams;

import java.util.Date;
import java.util.Set;

public class RedisConnector {
    private final String KEY;
    private static final String REDIS_SERVER_HOST_ADDRESS = "172.17.0.3";
    private static final int REDIS_SERVER_PORT = 6379;
    private Set<String> usersRegistered;

    private Jedis jedis = new Jedis(REDIS_SERVER_HOST_ADDRESS, REDIS_SERVER_PORT);

    public RedisConnector(String key, Set<String> usersRegistered) throws InterruptedException {
        this.usersRegistered = usersRegistered;
        this.KEY = key;
        for (String u : usersRegistered) {
            double score = Long.valueOf(new Date().getTime()).doubleValue();
            jedis.zadd(KEY, score, u);
            Thread.sleep(1);
        }
    }

    public String getUserToShow() {
        return Math.random() >= 0.9 ? getRichUser() : getLastUser();
    }

    private String getRichUser() {
        int random = (int) Math.round(Math.random() * (usersRegistered.size() - 1));
        String user = getUser(random);
        System.out.println(user + " donated some money to our service");
        return user;
    }

    private String getLastUser() {
        return getUser(0);
    }

    private String getUser(int offset) {
        String user = (String) jedis.zrangeByScore(KEY, "-inf", "+inf", offset, 1).toArray()[0];
        moveToTheEnd(user);
        return user;
    }

    private void moveToTheEnd(String member) {
        double score = Long.valueOf(new Date().getTime()).doubleValue();
        jedis.zadd(KEY, score, member, ZAddParams.zAddParams().ch());
    }
}
