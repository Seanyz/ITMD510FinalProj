package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class DepartmentModel extends DBConnect {
	public void addDepartment(String name) {
		String query = "INSERT INTO department (department) VALUES (?);";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, name);
			if(stmt.executeUpdate() > 0) {
				System.out.println("Insert department successfully.");
			} else {
				System.out.println("Insert department failed!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delDepartment(String name) {
		String query = "DELETE FROM department WHERE department = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, name);
			if(stmt.executeUpdate() > 0) {
				System.out.println("Delete department successfully.");
			} else {
				System.out.println("Delete department failed!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Test Code */
	public static void main(String[] args) {
		DepartmentModel test = new DepartmentModel();
		//test.addDepartment("Merchain");
		test.delDepartment("Merchain");
	}
}
