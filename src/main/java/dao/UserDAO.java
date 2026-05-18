package dao;

import java.util.List;
import java.util.Optional;

import model.User;

/**
 * DAO interface for managing User database operations.
 */
public interface UserDAO {
    /**
     * Retrieves a user by their ID.
     * @param id the user ID
     * @return an Optional containing the user if found, empty otherwise
     */
	Optional<User> getUserById(int id);
    /**
     * Retrieves a user by their email address.
     * @param email the user's email
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> getUserByEmail(String email);
    /**
     * Retrieves all users from the database.
     * @return list of all users
     */
	List<User> getAllUsers();
    /**
     * Saves a new user to the database.
     * @param user the user to save
     * @return true if successful, false otherwise
     */
	boolean saveUser(User user);
    /**
     * Updates an existing user in the database.
     * @param user the user with updated values
     */
	void updateUser(User user);
    /**
     * Deletes a user by their ID.
     * @param id the ID of the user to delete
     */
	void deleteUser(int id);
	
}
