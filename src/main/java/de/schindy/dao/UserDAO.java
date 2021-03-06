package de.schindy.dao;

import java.util.List;

import de.schindy.model.User;

public interface UserDAO {

	public User findByUserId(int id);

	public List<User> getAll();
	
	public List<String> getRoles();

	public User findUserByLoginName(String loginname);

	public void saveUser(User user);
	
}
