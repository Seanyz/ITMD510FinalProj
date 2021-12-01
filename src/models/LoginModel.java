package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class LoginModel extends DBConnect {

	private Boolean admin = false;
	private Boolean manager = false;

	private void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	private void setManager(Boolean manager) {
		this.manager = manager;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public Boolean isManager() {
		return manager;
	}

	public Integer isAdmin(String accountid) {
		String query = "SELECT isAdmin FROM user WHERE accountid = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, accountid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("isAdmin");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Integer isManager(String accountid) {
		String query = "SELECT isMgr FROM user WHERE accountid = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, accountid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("isMgr");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Boolean getCredentials(String username, String password) {
		String query = "SELECT * FROM user WHERE accountid = ? and password = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if (isAdmin(username) == 1) {
					setAdmin(true);
				}
				if (isManager(username) == 1) {
					setManager(true);
				}
				System.out.println(isAdmin(username));
				System.out.println(isManager(username));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/* test code */
	public static void main(String[] args) {
		LoginModel test = new LoginModel();
		test.getCredentials("2021001", "111111");
		System.out.println(test.isAdmin());
		System.out.println(test.isManager());
	}
}
