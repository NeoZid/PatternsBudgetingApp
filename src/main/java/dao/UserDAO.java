package dao;

import java.util.List;
import java.util.Optional;

import model.User;

public interface UserDAO {
	Optional<User> getUserById(int id);
    Optional<User> getUserByEmail(String email);
	List<User> getAllUsers();
	boolean saveUser(User user);
	void updateUser(User user);
	void deleteUser(int id);
	
}
