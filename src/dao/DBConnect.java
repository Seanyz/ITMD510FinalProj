package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	protected Connection connection;
	private static final String url = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
	private static final String USER = "fp510", PASS = "510";
	
	// Test local DB connection
	//private static final String url = "jdbc:mysql://localhost:3306/workhours?autoReconnect=true&useSSL=false";
	//private static final String USER = "user1", PASS = "111111";
	
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
