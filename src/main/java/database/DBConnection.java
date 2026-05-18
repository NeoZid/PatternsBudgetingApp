package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for managing the SQLite database connection.
 * Ensures only one instance of the connection manager exists.
 */
public class DBConnection {
	private static DBConnection instance;
	
	private DBConnection() {};
	
	private static final String url = "jdbc:sqlite:budget.db";

    /**
     * Private constructor to prevent instantiation.
     */
	public static DBConnection getInstance(){
		if (instance == null) {
			instance = new DBConnection();
			return instance;
		} else {
			return instance;
		}
	}

    /**
     * Establishes and returns a connection to the SQLite database.
     * @return a Connection object, or null if the connection fails
     */
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
