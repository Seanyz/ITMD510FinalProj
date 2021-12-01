package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DBConnect;

public class UserModel extends DBConnect{
	private ArrayList<User> users = new ArrayList<User>();
	
	public ArrayList<User> getUsers(){
		return readUser();
	}
	
	public int addUser(User setUser) {
		String query = "INSERT INTO user (accountid, first, last, deptid, password, isAdmin, isMgr)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		int rs = 0;
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, setUser.getAccountid());
			stmt.setString(2, setUser.getFirst());
			stmt.setString(3, setUser.getLast());
			stmt.setInt(4, setUser.getDeptid());
			stmt.setString(5, setUser.getPassword());
			stmt.setInt(6, setUser.getIsAdmin());
			stmt.setInt(7, setUser.getIsMgr());
			
			rs = stmt.executeUpdate();
			if(rs > 0) {
				System.out.println("Insert a new user.");
			}else {
				System.out.println("Insert user fail!");
				rs = -1;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ArrayList<User> readUser() {
		String query = "SELECT * FROM user;";
		
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
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
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public int updateUser(User setUser) {
		int rs = 0;
		String query = "UPDATE user "
				+ "SET accountid = ?, "
				+ "first = ?, last = ?, deptid = ?, password = ?, "
				+ "isAdmin = ?, isMgr = ? "
				+ "WHERE accountid = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, setUser.getAccountid());
			stmt.setString(2, setUser.getFirst());
			stmt.setString(3, setUser.getLast());
			stmt.setInt(4, setUser.getDeptid());
			stmt.setString(5, setUser.getPassword());
			stmt.setInt(6, setUser.getIsAdmin());
			stmt.setInt(7, setUser.getIsMgr());
			stmt.setString(8, setUser.getAccountid());
			rs = stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public int deleteUser(User delUser) {
		int rs = 0;
		String query = "DELETE FROM user WHERE accountid = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, delUser.getAccountid());
			rs = stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
