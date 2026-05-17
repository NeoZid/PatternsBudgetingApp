package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	private static DBConnection instance;
	
	private DBConnection() {};
	
	private static final String url = "jdbc:sqlite:budget.db";
	
	
	public static DBConnection getInstance(){
		if (instance == null) {
			instance = new DBConnection();
			return instance;
		} else {
			return instance;
		}
	}
	
	
	public Connection connect() {
		try {
			Connection conn = DriverManager.getConnection(url);
			return conn;
			
		} catch (SQLException e) {
			System.out.println("Connectiong fialed:" + e.getMessage());
			return null;
		}
	}
}
