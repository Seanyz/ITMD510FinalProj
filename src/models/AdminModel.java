package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class AdminModel extends DBConnect {
	
	public void addAdmin(String accountid) {
		String query = "INSERT INTO admin (accountid) VALUES (?);";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, accountid);
			if (stmt.executeUpdate() > 0) {
				System.out.println("Insert admin successfully.");
				String p_query = "{CALL updateIsAdmin(?, ?)}";
				try(PreparedStatement p_stmt = connection.prepareStatement(p_query)){
					p_stmt.setString(1, accountid);
					p_stmt.setInt(2, 1);
					if(p_stmt.executeUpdate() > 0) {
						System.out.println("Update admin indicator in user table successfully.");
					} else {
						System.out.println("Cannot update admin indicator in user table!");
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Insert manager failed!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAdmin(String accountid) {
		String query = "DELETE FROM admin WHERE accountid = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, accountid);
			if(stmt.executeUpdate() > 0) {
				System.out.println("Delete admin successfully, wait to update user table...");
				String p_query = "{CALL updateIsAdmin(?, ?)}";
				try(PreparedStatement p_stmt = connection.prepareStatement(p_query)){
					p_stmt.setString(1, accountid);
					p_stmt.setInt(2, 0);
					if(p_stmt.executeUpdate() > 0) {
						System.out.println("Update admin indicator successfully.");
					} else {
						System.out.println("Update admin indicator fail!");
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Delete admin fail!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*Test code*/
	public static void main(String[] args) {
		AdminModel test = new AdminModel();
		//System.out.println(test.getManager(1));
		test.deleteAdmin("2021002");
		//System.out.println(test.isManager("2021002"));
	}
}
