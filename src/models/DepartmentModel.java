package models;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The DepartmentModel completes CRUD operations on DB department 
 * table, 
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

import dao.DBConnect;

public class DepartmentModel extends DBConnect {

	// Add a new department
	public void addDepartment(String name) {
		String query = "INSERT INTO yzh204_department (department) VALUES (?);";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, name);
			if (stmt.executeUpdate() > 0) {
				System.out.println("Insert department successfully.");
			} else {
				System.out.println("Insert department failed!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Get department name from table
	public String getDept(int deptid) {
		String dept = null;
		String query = "SELECT department FROM yzh204_department WHERE id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, deptid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				dept = rs.getString("department");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dept;
	}

	// delete department from table
	public void delDepartment(String name) {
		String query = "DELETE FROM yzh204_department WHERE department = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, name);
			if (stmt.executeUpdate() > 0) {
				System.out.println("Delete department successfully.");
			} else {
				System.out.println("Delete department failed!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
