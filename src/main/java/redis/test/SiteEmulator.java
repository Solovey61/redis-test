package redis.test;

import java.util.Set;

public class SiteEmulator {
	private final SiteUserRepo userRepo;

	public SiteEmulator(Set<String> usersRegistered) {
		this.userRepo = new SiteUserRepo(usersRegistered);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Site user repository is cleaning...");
				userRepo.clear();
			}
		});

	}

	public void showMainPageUser() {
		try {
			for (;;) {
				System.out.println(Math.random() >= 0.9 ? userRepo.donate() : userRepo.getLastUser());
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
