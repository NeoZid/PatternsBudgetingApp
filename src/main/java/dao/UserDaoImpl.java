package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.DBConnection;
import model.User;

/**
 * Implementation of UserDAO interface.
 * Handles all database operations for User using SQLite.
 */
public class UserDaoImpl implements UserDAO{
    /**
     * Retrieves a user by their ID.
     * @param id the user ID
     * @return an Optional containing the user if found, empty otherwise
     */
	@Override
	public Optional<User> getUserById(int id) {
		String sql = "SELECT * FROM users WHERE id = ?";
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				User user = new User(
						rs.getInt("id"),
						rs.getString("email"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("currency")
						);
				return Optional.of(user);
						
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return Optional.empty();
	}

    /**
     * Retrieves a user by their email address.
     * @param email the user's email
     * @return an Optional containing the user if found, empty otherwise
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getInstance().connect()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("currency")
                );
                return Optional.of(user);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }
    /**
     * Retrieves all users from the database.
     * @return list of all users
     */
	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users";
		
		try (Connection conn = DBConnection.getInstance().connect()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				User user = new User(
						rs.getInt("id"),
						rs.getString("email"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("currency")
						);
				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return users;
	}
    /**
     * Saves a new user to the database.
     * @param user the user to save
     * @return true if successful, false otherwise
     */
	@Override
	public boolean saveUser(User user) {
		String sql = "INSERT INTO users(email, username, password, currency) VALUES(?,?,?,?)";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getDefaultCurrency());
			pstmt.executeUpdate();

            return true;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
            return false;
		}
	}
    /**
     * Updates an existing user in the database.
     * @param user the user with updated values
     */
	@Override
	public void updateUser(User user) {
		String sql = "UPDATE users SET email=?, username=?, password=?, currency=? WHERE id=?";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getDefaultCurrency());
            pstmt.setInt(5, user.getUserId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
    /**
     * Deletes a user by their ID.
     * @param id the ID of the user to delete
     */
	@Override
	public void deleteUser(int id) {
		String sql = "DELETE FROM users WHERE id=?";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
