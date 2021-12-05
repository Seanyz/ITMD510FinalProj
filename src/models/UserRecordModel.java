package models;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The UserRecordModel class declares all CURD operations on DB 
 * 'UserRecord' table, in this project, it only implements query data from table,
 * insert, delete, update operation are executed by storage procedures in DB
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class UserRecordModel extends DBConnect {
	private String accountid;
	private UserRecord curUserRecord = null;

	// constructor to get user accountid for all operations
	public UserRecordModel(String accountid) {
		this.accountid = accountid;
	}

	// To get user record information for 'userrecords' table
	public UserRecord getCurUserRecord() {
		String query = "SELECT * FROM yzh204_userrecords WHERE accountid = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, this.accountid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String account = rs.getString("accountid");
				String dept = rs.getString("dept");
				String manager = rs.getString("manager");
				int aot = rs.getInt("aot");
				int wot = rs.getInt("wot");
				int rhour = rs.getInt("remainhour");
				curUserRecord = new UserRecord(account, dept, manager, aot, wot, rhour);
				System.out.printf("Get record data from 'userrecords' for user - %s", curUserRecord.getAccountid());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(curUserRecord.getAccountid());
		return curUserRecord;
	}
}
