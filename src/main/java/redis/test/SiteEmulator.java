package redis.test;

import java.util.Set;

public class SiteEmulator {
    private final String KEY = "USERS_REGISTERED";
    private final RedisConnector redis;

    public SiteEmulator(Set<String> users_registered) throws InterruptedException {
        this.redis = new RedisConnector(KEY, users_registered);
    }

    public void showMainPageUser() throws InterruptedException {
        for (;;) {
            System.out.println(redis.getUserToShow());
            Thread.sleep(1000);
        }
    }

}
