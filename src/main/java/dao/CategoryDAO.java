package dao;

import java.util.List;
import java.util.Optional;

import model.Category;

/**
 * DAO interface for managing Category database operations.
 */
public interface CategoryDAO {
    /**
     * Retrieves a category by its ID.
     * @param id the category ID
     * @return an Optional containing the category if found, empty otherwise
     */
	Optional<Category> getCategoryById(int id);
    /**
     * Retrieves all categories belonging to a specific user.
     * @param userId the user ID
     * @return list of categories for the user
     */
	List<Category> getCategoriesByUser(int userId);
    /**
     * Saves a new category to the database.
     * @param c the category to save
     */
	void saveCategory(Category c);
    /**
     * Updates an existing category in the database.
     * @param c the category with updated values
     */
	void updateCategory(Category c);
    /**
     * Deletes a category by its ID.
     * @param id the ID of the category to delete
     */
	void deleteCategory(int id);
}
