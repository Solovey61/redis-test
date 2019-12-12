import redis.test.SiteEmulator;

import java.util.LinkedHashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Set<String> users = new LinkedHashSet<>();
        for (int i = 0; i < 20; i++) {
            users.add("User " + (i + 1));
        }
        SiteEmulator siteEmulator = new SiteEmulator(users);
        siteEmulator.showMainPageUser();
    }
}