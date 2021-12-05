package models;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The StatusModel is used to do get status name from status table 
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class StatusModel extends DBConnect {
	public String getStatusName(int status) {
		String statusName = "";
		String query = "SELECT status FROM yzh204_status WHERE id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, status);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				statusName = rs.getString("status");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statusName;
	}
}
