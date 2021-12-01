package models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import dao.DBConnect;

public class OTRecordModel extends DBConnect{
	
	public void addOTRecord(String accountid, int week, double othours) {
		//INSERT INTO `otrecord` (`accountid`, `createdate`, `updatedate`, `otweek`, `othours`, `status`, `changeby`) 
		//VALUES ('2021002', '2021-11-25', '2021-11-25', '1', '2', '1', '2021002');
		Date dNow = new java.sql.Date(new java.util.Date().getTime());
		SimpleDateFormat ftime = new SimpleDateFormat("yyyyMMdd");
		String query = "INSERT INTO otrecord (accountid, createdate, updatedate, otweek, othours, status, changeby) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, accountid);
			stmt.setDate(2, dNow);
			stmt.setDate(3, dNow);
			stmt.setInt(4, week);
			stmt.setDouble(5, othours);
			stmt.setInt(6, 1);
			stmt.setString(7, accountid);
			if(stmt.executeUpdate() > 0) {
				System.out.println("Insert OTRecord successfully.");
			} else {
				System.out.println("Insert OTRecord failed!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void readOTRecord(int level, String accountid) {
		// read record based on level: 1=normal user, 2=manager, 3=admin
		
		String query = "";
		switch(level) {
		case 1:
			query = "SELECT * FROM otrecord WHERE accountid = ?;";
			break;
		case 2:
			query = "SELECT * FORM otrecord WHERE deptid = ?;";
		}
			
	}
	
	public int getOTRecordStatus(int id) {
		String query = "SELECT status FROM otrecord WHERE id = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("status");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void updateOTRecordStatus(int id, int status) {
		String query = "UPDATE otrecord SET status = ? WHERE id = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1, status);
			stmt.setInt(2, id);
			if(stmt.executeUpdate() > 0) {
				System.out.printf("Status updated to: %d\n", status);
			}else {
				System.out.println("Status updated failed!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void deleteOTRecord(int id) {
		// DELETE FROM `otrecord` WHERE `id = xxx`
		String query = "DELETE FROM otrecord WHERE id = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1, id);
			int effectRowNum = stmt.executeUpdate();
			System.out.printf("Delet %d row(s) successfully.\n", effectRowNum);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Test code*/
	public static void main(String[] args) {
		OTRecordModel test = new OTRecordModel();
		//test.addOTRecord("2021004", 4, 30);
		//test.deleteOTRecord(2);
		System.out.println(test.getOTRecordStatus(4));
		test.updateOTRecordStatus(4, 2);
		System.out.println(test.getOTRecordStatus(4));
	}
}
