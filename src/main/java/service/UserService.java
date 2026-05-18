package service;

import java.util.Optional;

import dao.UserDaoImpl;
import model.User;

/**
 * Service class for managing User business logic.
 * Handles user authentication and registration validation.
 */
public class UserService {
	
	// Service = code logic, validations

    private UserDaoImpl userDao = new UserDaoImpl();

    /**
     * Authenticates a user by email and password.
     * @param email the user's email address
     * @param password the user's password
     * @return an Optional containing the user if credentials are valid, empty otherwise
     */
    public Optional<User> userLogin(String email, String password) {
        Optional<User> user = userDao.getUserByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) { // takes data/ checks password if match
            return user;
        } else {
            return Optional.empty();
        }
    }

    /**
     * Registers a new user after validating all fields.
     * @param email the user's email address
     * @param userName the user's username
     * @param password the user's password
     * @param defaultCurrency the user's preferred currency
     * @return true if registration was successful, false if validation fails
     */
    public boolean registerUser(String email, String userName, String password, String defaultCurrency) {
    	if (email.isEmpty() || userName.isEmpty() || password.isEmpty() || defaultCurrency.isEmpty()) {
    		return false;
    	}
    	
        User user = new User(0, email, userName, password, defaultCurrency);
        return userDao.saveUser(user);
    }
}
