package dao;

import java.util.List;
import java.util.Optional;

import model.Category;


public interface CategoryDAO {
	Optional<Category> getCategoryById(int id); 
	List<Category> getCategoriesByUser(int userId);
	void saveCategory(Category c);
	void updateCategory(Category c);
	void deleteCategory(int id);
}
