package model;

public class Category {
	private int categoryId;
	private int userId;
	private String name;
	private String type;
	public Category(int categoryId, int userId, String name, String type) {
		super();
		this.categoryId = categoryId;
		this.userId = userId;
		this.name = name;
		this.type = type;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public int getUserId() {
		return userId;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", userId=" + userId + ", name=" + name + ", type=" + type + "]";
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
