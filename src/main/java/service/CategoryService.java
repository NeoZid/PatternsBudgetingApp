package service;

import java.util.HashMap;
import java.util.List;

import dao.CategoryDaoImpl;
import model.Category;
import util.SessionManager;

public class CategoryService {
	private CategoryDaoImpl catDaoImpl = new CategoryDaoImpl();
	
	// instead of loading db everytime we need category by id, we use hashmap to have a cache to look things up faster 0(1)
	private HashMap<Integer, Category> categoryCache = new HashMap<>();
	
	public List<Category> getCategoriesByUser(int userId) {
		List<Category> categories = catDaoImpl.getCategoriesByUser(userId);
		
		// after getting list, we put them in the hashmap of cache 
		for (Category c : categories) {
			categoryCache.put(c.getCategoryId(), c);
		}
		return categories;
	}
	
	public Category getCategoryById(int id) {
		if (categoryCache.isEmpty()) {
			getCategoriesByUser(SessionManager.getInstance().getUserLoggedIn().getUserId());
		}
		return categoryCache.get(id);
	}
	
	public boolean saveCategory(String name, String type) {
		if (name.isEmpty() || type.isBlank()) {
			return false;
		}
												// this takes the user logged in when saving a category
		catDaoImpl.saveCategory(new Category(0, SessionManager.getInstance().getUserLoggedIn().getUserId(), name, type));
		return true;
	}
	
	public void deleteCategory(int categoryId) {
		catDaoImpl.deleteCategory(categoryId);
	}
	
	public boolean updateCategory(int categoryId, String name, String type) {
		if (name.isEmpty() || type.isBlank()) {
			return false;
		}
		
		
		catDaoImpl.updateCategory(new Category(categoryId, SessionManager.getInstance().getUserLoggedIn().getUserId(), name, type));
		return true;
	}
	
	
	
}
