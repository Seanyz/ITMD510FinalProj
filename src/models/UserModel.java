package models;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: THe UserModel class implement CRUD operations on DB User table, 
 * to allow 'admin' role maintain user list
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DBConnect;

public class UserModel extends DBConnect {

	// Save users list into ArrayList
	private ArrayList<User> users = new ArrayList<User>();

	// Get method to get user list from DB
	public ArrayList<User> getUsers() {
		return readUser();
	}

	// Storage procedure, executed when add a new user, then create a user hour
	// applications record into userrecords table.
	public int procNewUserRecord(User setUser) {
		String query = "CALL yzh204_newuserrecord(?);";
		int rs = 0;
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, setUser.getAccountid());
			rs = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// Storage procedure, executed when delete a user, then delete this user's hour
	// applications record from userrecords table.
	public int procDelUserRecord(User setUser) {
		String query = "CALL yzh204_deluserrecord(?);";
		int rs = 0;
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, setUser.getAccountid());
			rs = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// Insert a new user into user table
	public int addUser(User setUser) {
		String query = "INSERT INTO yzh204_user (accountid, first, last, deptid, password, isAdmin, isMgr)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		int rs = 0;
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, setUser.getAccountid());
			stmt.setString(2, setUser.getFirst());
			stmt.setString(3, setUser.getLast());
			stmt.setInt(4, setUser.getDeptid());
			stmt.setString(5, setUser.getPassword());
			stmt.setInt(6, setUser.getIsAdmin());
			stmt.setInt(7, setUser.getIsMgr());

			rs = stmt.executeUpdate();
			if (rs > 0) {
				System.out.println("Insert a new user.");
				if (procNewUserRecord(setUser) == 1) {
					System.out.println("Storage Procedure: Create " + "application record for new user successfully.");
				}
			} else {
				System.out.println("Insert user fail!");
				rs = -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// Return user list by select query
	public ArrayList<User> readUser() {
		String query = "SELECT * FROM yzh204_user;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String account = rs.getString("accountid");
				String first = rs.getString("first");
				String last = rs.getString("last");
				int deptid = rs.getInt("deptid");
				String password = rs.getString("password");
				int isAdmin = rs.getInt("isAdmin");
				int isMgr = rs.getInt("isMgr");
				User setUser = new User(account, first, last, deptid, password, isAdmin, isMgr);
				users.add(setUser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	// Update user information in user table
	public int updateUser(User setUser) {
		int rs = 0;
		String query = "UPDATE yzh204_user " + "SET accountid = ?, " + "first = ?, last = ?, deptid = ?, password = ?, "
				+ "isAdmin = ?, isMgr = ? " + "WHERE accountid = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, setUser.getAccountid());
			stmt.setString(2, setUser.getFirst());
			stmt.setString(3, setUser.getLast());
			stmt.setInt(4, setUser.getDeptid());
			stmt.setString(5, setUser.getPassword());
			stmt.setInt(6, setUser.getIsAdmin());
			stmt.setInt(7, setUser.getIsMgr());
			stmt.setString(8, setUser.getAccountid());
			rs = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// Delete an exist user from user table
	public int deleteUser(User delUser) {
		int rs = 0;
		String query = "DELETE FROM yzh204_user WHERE accountid = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, delUser.getAccountid());
			if (procDelUserRecord(delUser) == 1) {
				System.out.println("Storage Procedure: Delete application record finished successfully.");
			}
			rs = stmt.executeUpdate();
			System.out.printf("Delete user: %s.", delUser.getAccountid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// Find an user by accountid
	public User findUser(String accountid) {
		String query = "SELECT * FROM yzh204_user WHERE accountid = ?;";
		User rsUser = null;
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, accountid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String account = rs.getString("accountid");
				String first = rs.getString("first");
				String last = rs.getString("last");
				int deptid = rs.getInt("deptid");
				String password = rs.getString("password");
				int isAdmin = rs.getInt("isAdmin");
				int isMgr = rs.getInt("isMgr");
				rsUser = new User(account, first, last, deptid, password, isAdmin, isMgr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rsUser;
	}
}
