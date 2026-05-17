package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitialize {
	
	public static void initialize() {
		String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
							"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
							"email TEXT UNIQUE NOT NULL," +
							"username TEXT NOT NULL, " +
							"password TEXT NOT NULL," +
							"currency TEXT NOT NULL" +
							");";
		String categoriesTable = "CREATE TABLE IF NOT EXISTS categories (" + 
							"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
							"name TEXT NOT NULL, " + 
							"type TEXT NOT NULL, " +
							"user_id INTEGER NOT NULL, " + 
							"FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
							");";
		String transactionTable = "CREATE TABLE IF NOT EXISTS transacs (" + 
							"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
							"amount REAL NOT NULL," +
							"date TEXT NOT NULL," +
							"description TEXT NOT NULL," +
							"type TEXT NOT NULL," +
							
							"user_id INTEGER NOT NULL, " +
							"category_id INTEGER, " + 
							
							"FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE," +
							"FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL" +
							");";
		
		try (Connection conn = DBConnection.getInstance().connect();
			Statement stmt = conn . createStatement () ) {
				stmt.execute("PRAGMA foreign_keys = ON;");
				stmt.execute (usersTable) ;
				stmt.execute(categoriesTable);
				stmt.execute(transactionTable);
		} catch (SQLException e) {
			System.out.println (e.getMessage());
		}
		
		
		
	}
}
