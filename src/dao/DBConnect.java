package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	protected Connection connection;
	//private static final String url = "jdbc:mysql://localhost:3306/my510?autoReconnect=true&useSSL=false";
	//private static final String USER = "root", PASS = "123456";
	private static final String url = "jdbc:mysql://localhost:3306/workhours?autoReconnect=true&useSSL=false";
	private static final String USER = "user1", PASS = "111111";
	
	// Constructor
	public DBConnect() {
		try {
			connection = DriverManager.getConnection(url, USER, PASS);
		} catch (SQLException e) {
			System.out.print("Error to connect DB:" + e);
			System.exit(-1);
		}
	}
	
	// Get DB connection 
	public Connection getConnection() {return connection;}
}
