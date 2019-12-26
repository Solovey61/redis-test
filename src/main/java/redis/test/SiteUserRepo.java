package redis.test;

import java.util.LinkedHashSet;
import java.util.Set;

public class SiteUserRepo {
	private final String KEY = "USERS_REGISTERED";
	private final RedisConnector connector;

	private Set<String> usersRegistered;

	public SiteUserRepo() {
		this(new LinkedHashSet<>());
	}

	public SiteUserRepo(Set<String> usersRegistered) {
		this.connector = new RedisConnector(KEY);
		fillUserRepo(usersRegistered);
	}

	private void fillUserRepo(Set<String> usersRegistered) {
		this.usersRegistered = usersRegistered;
		connector.addAll(usersRegistered);
	}

	public void addUser(String user) {
		connector.add(user);
		usersRegistered.add(user);
	}

	public void removeUser(String user) {
		connector.remove(user);
		usersRegistered.remove(user);
	}

	public void clear() {
		connector.clear();
		usersRegistered.clear();
	}

	public void removeUsers(Set<String> users) {
		connector.removeAll(users);
		usersRegistered.removeAll(users);
	}

	public String donate() {
		int random = (int) Math.round(Math.random() * (usersRegistered.size() - 1));
		String user = getUser(random);
		System.out.println(user + " donated some money to our service");
		return user;
	}

	public String getLastUser() {
		return getUser(0);
	}

	public String getUser(int offset) {
		String user = (String) connector.getSortedSed("-inf", "+inf", offset, 1).toArray()[0];
		moveUserToTheEnd(user);
		return user;
	}

	public Set<String> getUsers() {
		return usersRegistered;
	}

	private void moveUserToTheEnd(String user) {
		connector.move(user);
	}
}
