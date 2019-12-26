package redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ZAddParams;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RedisConnector {
	private static final String REDIS_SERVER_HOST_ADDRESS = "127.0.0.1";
	private static final int REDIS_SERVER_PORT = 6379;
	private final String KEY;
	private Jedis jedis = new Jedis(REDIS_SERVER_HOST_ADDRESS, REDIS_SERVER_PORT);

	public RedisConnector(String key) {
		this.KEY = key;
	}

	public void addAll(Set<String> users) {
		try {
			for (String u : users) {
				add(u);
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void add(String user) {
		jedis.zadd(KEY, getScore(), user);
	}

	public void move(String user) {
		jedis.zadd(KEY, getScore(), user, ZAddParams.zAddParams().ch());
	}

	public void removeAll(Set<String> users) {
		users.forEach(this::remove);
	}

	public void remove(String user) {
		jedis.zrem(KEY, user);
	}

	public void clear() {
		Set<String> allUsers = jedis.zrangeByScore(KEY, "-inf", "+inf");
		removeAll(allUsers);
	}

	public Set<String> getSortedSed(String min, String max, int offset, int count) {
		return jedis.zrangeByScore(KEY, min, max, offset, count);
	}

	private double getScore() {
		return Long.valueOf(new Date().getTime()).doubleValue();
	}

}
