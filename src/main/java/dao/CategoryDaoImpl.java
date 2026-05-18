package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.DBConnection;
import model.Category;

/**
 * Implementation of CategoryDAO interface.
 * Handles all database operations for Category using SQLite.
 */
public class CategoryDaoImpl implements CategoryDAO{

    /**
     * Retrieves a category by its ID.
     * @param id the category ID
     * @return an Optional containing the category if found, empty otherwise
     */
	@Override
	public Optional<Category> getCategoryById(int id) {
		String sql = "SELECT * FROM categories WHERE id = ?";
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Category category = new Category(
						rs.getInt("id"),
						rs.getInt("user_id"),
						rs.getString("name"),
						rs.getString("type")
						);
				return Optional.of(category);
						
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return Optional.empty();
	}

    /**
     * Retrieves all categories belonging to a specific user.
     * @param userId the user ID
     * @return list of categories for the user
     */
	@Override
	public List<Category> getCategoriesByUser(int userId) {
		List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE user_id = ?";

        try(Connection conn = DBConnection.getInstance().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1,userId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                list.add(new Category(
                	rs.getInt("id"),
                	rs.getInt("user_id"),
                	rs.getString("name"),
                	rs.getString("type")
                	));
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return list;
	}

    /**
     * Saves a new category to the database.
     * @param c the category to save
     */
	@Override
	public void saveCategory(Category c) {
		String sql = "INSERT INTO categories (name, type, user_id) VALUES (?,?,?)";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, c.getName());
			pstmt.setString(2, c.getType());
			pstmt.setInt(3, c.getUserId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

    /**
     * Updates an existing category in the database.
     * @param c the category with updated values
     */
	@Override
	public void updateCategory(Category c) {
		String sql = "UPDATE categories SET name=?, type =? WHERE id=?";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, c.getName());
			pstmt.setString(2, c.getType());
			pstmt.setInt(3, c.getCategoryId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

    /**
     * Deletes a category by its ID.
     * @param id the ID of the category to delete
     */
	@Override
	public void deleteCategory(int id) {
		String sql = "DELETE FROM categories WHERE id=?";
		
		try (Connection conn = DBConnection.getInstance().connect()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	

}
