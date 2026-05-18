package service;

import java.util.Optional;

import dao.UserDaoImpl;
import model.User;

public class UserService {
	
	// Service = code logic, validations

    private UserDaoImpl userDao = new UserDaoImpl();

    public Optional<User> userLogin(String email, String password) {
        Optional<User> user = userDao.getUserByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) { // takes data/ checks password if match
            return user;
        } else {
            return Optional.empty();
        }
    }

    public boolean registerUser(String email, String userName, String password, String defaultCurrency) {
    	if (email.isEmpty() || userName.isEmpty() || password.isEmpty() || defaultCurrency.isEmpty()) {
    		return false;
    	}
    	
        User user = new User(0, email, userName, password, defaultCurrency);
        return userDao.saveUser(user);
    }
}
