package models;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The OTRecord class declares "OTRecord" object
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class OTRecord {
	
	// Declare properties
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty accountid;
	private final SimpleIntegerProperty othours;
	private final SimpleIntegerProperty statusid;
	private final SimpleIntegerProperty deptid;
	private final SimpleStringProperty department;
	
	// Constructor
	public OTRecord(int id, String accountid, int othours, int status,
			int deptid, String department){
		this.id = new SimpleIntegerProperty(id);
		this.accountid = new SimpleStringProperty(accountid);
		this.othours = new SimpleIntegerProperty(othours);
		this.statusid = new SimpleIntegerProperty(status);
		this.deptid = new SimpleIntegerProperty(deptid);
		this.department = new SimpleStringProperty(department);
	}
	
	// All sets and gets methods
	public int getId() {
		return this.id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public String getAccountid() {
		return this.accountid.get();
	}
	public void setAccountid(String accountid) {
		this.accountid.set(accountid);
	}
	public int getOthours() {
		return this.othours.get();
	}
	public void setOthours(int othours) {
		this.othours.set(othours);
	}
	public int getStatusid() {
		return this.statusid.get();
	}
	public void setStatusid(int status) {
		this.statusid.set(status);
	}
	public int getDeptid() {
		return this.deptid.get();
	}
	public void setDeptid(int deptid) {
		this.deptid.set(deptid);
	}
	public String getDepartment() {
		return this.department.get();
	}
	public void setDepartment(String deparment) {
		this.department.set(deparment);
	}
}
