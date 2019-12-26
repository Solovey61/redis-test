import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.test.SiteUserRepo;

import java.util.LinkedHashSet;

public class TestUserRepo {
	SiteUserRepo userRepo;

	@Before
	public void init() {
		userRepo = new SiteUserRepo();
	}

	@Test
	public void testEmptyConstructor() {
		assert userRepo.getUsers().equals(new LinkedHashSet<>());
	}

	@Test
	public void testConstructorWithSet() {
		LinkedHashSet<String> testSet = new LinkedHashSet<>();
		for (int i = 0; i < 5; i++) {
			testSet.add("user" + i);
		}
		userRepo = new SiteUserRepo(testSet);
		assert userRepo.getUsers().equals(testSet);
	}

	@Test
	public void testAddUserAndGetLatUser() {
		String testUser = "New user";
		userRepo.addUser(testUser);
		assert userRepo.getLastUser().equals(testUser);
	}

	@Test
	public void testAddUserAndRemoveUser() {
		String testUser = "New user";
		userRepo.addUser(testUser);
		userRepo.removeUser(testUser);
		assert userRepo.getUsers().isEmpty();
	}

	@After
	public void onShutdown() {
		userRepo.clear();
	}
}
