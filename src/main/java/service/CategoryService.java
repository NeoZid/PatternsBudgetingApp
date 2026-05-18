package service;

import java.util.HashMap;
import java.util.List;

import dao.CategoryDaoImpl;
import model.Category;
import util.SessionManager;

/**
 * Service class for managing Category business logic.
 * Acts as an intermediary between the controller and DAO layers.
 * Uses a HashMap for lookups by ID.
 */
public class CategoryService {
	private CategoryDaoImpl catDaoImpl = new CategoryDaoImpl();


	// instead of loading db everytime we need category by id, we use hashmap to have a cache to look things up faster 0(1)
    /**
     * Cache for storing categories by ID to avoid repeated database calls.
     */
	private HashMap<Integer, Category> categoryCache = new HashMap<>();

    /**
     * Retrieves all categories for a specific user and populates the cache.
     * @param userId the user ID
     * @return list of categories for the user
     */
	public List<Category> getCategoriesByUser(int userId) {
		List<Category> categories = catDaoImpl.getCategoriesByUser(userId);
		
		// after getting list, we put them in the hashmap of cache 
		for (Category c : categories) {
			categoryCache.put(c.getCategoryId(), c);
		}
		return categories;
	}

    /**
     * Retrieves a category by its ID using the cache.
     * If the cache is empty, loads categories from the database first.
     * @param id the category ID
     * @return the category, or null if not found
     */
	public Category getCategoryById(int id) {
		if (categoryCache.isEmpty()) {
			getCategoriesByUser(SessionManager.getInstance().getUserLoggedIn().getUserId());
		}
		return categoryCache.get(id);
	}

    /**
     * Saves a new category for the currently logged-in user.
     * @param name the category name
     * @param type the category type (Income or Expense)
     * @return true if saved successfully, false if validation fails
     */
	public boolean saveCategory(String name, String type) {
		if (name.isEmpty() || type.isBlank()) {
			return false;
		}
												// this takes the user logged in when saving a category
		catDaoImpl.saveCategory(new Category(0, SessionManager.getInstance().getUserLoggedIn().getUserId(), name, type));
		return true;
	}

    /**
     * Deletes a category by its ID.
     * @param categoryId the ID of the category to delete
     */
	public void deleteCategory(int categoryId) {
		catDaoImpl.deleteCategory(categoryId);
	}

    /**
     * Updates an existing category for the currently logged-in user.
     * @param categoryId the ID of the category to update
     * @param name the new category name
     * @param type the new category type
     * @return true if updated successfully, false if validation fails
     */
	public boolean updateCategory(int categoryId, String name, String type) {
		if (name.isEmpty() || type.isBlank()) {
			return false;
		}
		
		
		catDaoImpl.updateCategory(new Category(categoryId, SessionManager.getInstance().getUserLoggedIn().getUserId(), name, type));
		return true;
	}
}
