package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class ManagerModel extends DBConnect {
	public void addManager(String accountid, int deptid) {
		String query = "INSERT INTO manager (accountid, deptid) VALUES (?, ?);";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, accountid);
			stmt.setInt(2, deptid);
			if (stmt.executeUpdate() > 0) {
				System.out.println("Insert manager successfully.");
				String p_query = "{CALL updateIsMgr(?, ?)}";
				try(PreparedStatement p_stmt = connection.prepareStatement(p_query)){
					p_stmt.setString(1, accountid);
					p_stmt.setInt(2, 1);
					if(p_stmt.executeUpdate() > 0) {
						System.out.println("Update manager indicator in user table successfully.");
					} else {
						System.out.println("Cannot update manager indicator in user table!");
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
	
	public void deleteMgr(String accountid) {
		String query = "DELETE FROM manager WHERE accountid = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, accountid);
			if(stmt.executeUpdate() > 0) {
				System.out.println("Delete manager successfully, wait to update user table...");
				String p_query = "{CALL updateIsMgr(?, ?)}";
				try(PreparedStatement p_stmt = connection.prepareStatement(p_query)){
					p_stmt.setString(1, accountid);
					p_stmt.setInt(2, 0);
					if(p_stmt.executeUpdate() > 0) {
						System.out.println("Update manager indicator successfully.");
					} else {
						System.out.println("Update manager indicator fail!");
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Delete manager fail!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getManager(int deptid) {
		String mgrAccountId = "";
		
		String query = "SELECT accountid FROM manager WHERE deptid = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1, deptid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				mgrAccountId = rs.getString("accountid");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return mgrAccountId;
	}
	
	/*Test code*/
	public static void main(String[] args) {
		ManagerModel test = new ManagerModel();
		//System.out.println(test.getManager(1));
		test.deleteMgr("2021002");
		//System.out.println(test.isManager("2021002"));
	}
}
