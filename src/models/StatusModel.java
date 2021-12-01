package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class StatusModel extends DBConnect {
	public String getStatusName(int status) {
		String statusName = "";
		String query = "SELECT status FROM status WHERE id = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1, status);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				statusName = rs.getString("status");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return statusName;
	}
	
	/* Test code */
	public static void main(String[] args) {
		StatusModel test = new StatusModel();
		System.out.println(test.getStatusName(1));
		
	}
}
