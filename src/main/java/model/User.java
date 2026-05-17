package model;

public class User {
	private int userId; // pm for user
	private String email;
	private String userName;
	private String password;
	private String defaultCurrency; // CAD, US, YEN, EUR
	
	
	public User(int userId, String email, String userName, String password, String defaultCurrency) {
		super();
		this.userId = userId;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.defaultCurrency = defaultCurrency;
	}


	public int getUserId() {
		return userId;
	}


	public String getEmail() {
		return email;
	}


	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}


	public String getDefaultCurrency() {
		return defaultCurrency;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", userName=" + userName + ", password=" + password
				+ ", defaultCurrency=" + defaultCurrency + "]";
	}
	
	
	
	
	
	
}
