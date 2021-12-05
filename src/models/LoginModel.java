package models;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The LoginModel takes responsibility of CRUD operation on DB 
 * "user" table
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class LoginModel extends DBConnect {

	private Boolean admin = false;
	private Boolean manager = false;
	private String accountid = "";

	// All gets and sets methods.
	public String getAccountId() {
		return accountid;
	}

	public void setAccountId(String accountid) {
		this.accountid = accountid;
	}

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

	// To determine if this user with "admin" role
	public Integer isAdmin(String accountid) {
		String query = "SELECT isAdmin FROM yzh204_user WHERE accountid = ?;";
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

	// To determine if this user with "manager" role
	public Integer isManager(String accountid) {
		String query = "SELECT isMgr FROM yzh204_user WHERE accountid = ?;";
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

	// To determine if this user and password are correct
	public Boolean getCredentials(String username, String password) {
		String query = "SELECT * FROM yzh204_user WHERE accountid = ? and password = ?;";
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
				setAccountId(username);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
