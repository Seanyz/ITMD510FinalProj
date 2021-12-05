package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DBConnect;

public class OTRecordModel extends DBConnect {
	private ArrayList<OTRecord> otrecords = new ArrayList<OTRecord>();
	private DepartmentModel deptmodel = null;
	private int lastRcdid;

	public OTRecordModel() {
		deptmodel = new DepartmentModel();
	}

	public ArrayList<OTRecord> getOTRecords(int role, String accountid) {
		readOTRecord(role, accountid);
		return this.otrecords;
	}

	public void readOTRecord(int role, String accountid) {
		/*
		 * read OT records based on role: 1=normal user, get all own records 2=manager,
		 * get all department records
		 */
		String query = "";
		UserModel um = new UserModel();
		int udeptid = um.findUser(accountid).getDeptid();

		// normal user only get his own records
		if (role == 1) {
			query = "SELECT * FROM yzh204_otrecord WHERE accountid = ?;";
			try (PreparedStatement stmt = connection.prepareStatement(query)) {
				stmt.setString(1, accountid);
				exeQueOTRecord(stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Manager will get all employee's record in this department
		if (role == 2) {
			query = "SELECT * FROM yzh204_otrecord WHERE deptid = ?;";
			try (PreparedStatement stmt = connection.prepareStatement(query)) {
				stmt.setInt(1, udeptid);
				exeQueOTRecord(stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Admin user get all records in all department
		if (role == 3) {
			query = "SELECT * FROM yzh204_otrecord WHERE 1;";
			try (PreparedStatement stmt = connection.prepareStatement(query)) {
				exeQueOTRecord(stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void exeQueOTRecord(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("id");
			String account = rs.getString("accountid");
			int othours = rs.getInt("othours");
			int status = rs.getInt("statusid");
			int deptid = rs.getInt("deptid");
			String department = rs.getString("department");
			OTRecord getOtrecord = new OTRecord(id, account, othours, status, deptid, department);
			otrecords.add(getOtrecord);
		}
	}

	public void addOTRecord(OTRecord newotr) {
		String department = deptmodel.getDept(newotr.getDeptid());
		String query = "INSERT INTO yzh204_otrecord (accountid, othours, statusid, " + "deptid, department) "
				+ "VALUES (?, ?, ?, ?, ?);";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, newotr.getAccountid());
			stmt.setInt(2, newotr.getOthours());
			stmt.setInt(3, newotr.getStatusid());
			stmt.setInt(4, newotr.getDeptid());
			stmt.setString(5, department);
			if (stmt.executeUpdate() > 0) {
				System.out.println("Insert OTRecord successfully.");
				getLastRecord();
				procAddRecord(lastRcdid);
			} else {
				System.out.println("Insert OTRecord failed!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getLastRecord() {
		String query = "SELECT * FROM yzh204_otrecord ORDER BY id desc LIMIT 1;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				lastRcdid = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastRcdid;
	}

	public int getOTRecordStatus(int id) {
		String query = "SELECT statusid FROM yzh204_otrecord WHERE id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("statusid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void updateOTRecordStatus(int id, int status) {
		if (status == 3) {
			procDelRecord(id);
		}
		String query = "UPDATE yzh204_otrecord SET statusid = ? WHERE id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, status);
			stmt.setInt(2, id);
			if (stmt.executeUpdate() > 0) {
				System.out.printf("Status updated to: %d\n", status);
			} else {
				System.out.println("Status updated failed!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteOTRecord(int id) {
		// DELETE FROM `otrecord` WHERE `id = xxx`
		procDelRecord(id);
		String query = "DELETE FROM yzh204_otrecord WHERE id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id);
			int effectRowNum = stmt.executeUpdate();
			System.out.printf("Delete %d row(s) successfully.\n", effectRowNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void procAddRecord(int rcdid) {
		String query = "CALL yzh204_addrecord(?);";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, rcdid);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void procDelRecord(int rcdid) {
		String query = "CALL yzh204_delrecord(?);";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, rcdid);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
