package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OTRecord {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty accountid;
	private final SimpleStringProperty createdate;
	private final SimpleStringProperty updateddate;
	private final SimpleIntegerProperty otweek;
	private final SimpleIntegerProperty othours;
	private final SimpleIntegerProperty status;
	private final SimpleStringProperty changeby;
	private final SimpleIntegerProperty deptid;
	private final SimpleStringProperty department;
	
	public OTRecord(int id, String accountid, String createdate, String updateddate, 
			int otweek, int othours, int status, String changeby, int deptid, String department){
		this.id = new SimpleIntegerProperty(id);
		this.accountid = new SimpleStringProperty(accountid);
		this.createdate = new SimpleStringProperty(createdate);
		this.updateddate = new SimpleStringProperty(updateddate);
		this.otweek = new SimpleIntegerProperty(otweek);
		this.othours = new SimpleIntegerProperty(othours);
		this.status = new SimpleIntegerProperty(status);
		this.changeby = new SimpleStringProperty(changeby);
		this.deptid = new SimpleIntegerProperty(deptid);
		this.department = new SimpleStringProperty(department);
	}
	
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
	public String getCreatedate() {
		return this.createdate.get();
	}
	public void setCreatedate(String createdate) {
		this.createdate.set(createdate);
	}
	public String getUpdateddate() {
		return this.updateddate.get();
	}
	public void setUpdateddate(String updateddate) {
		this.updateddate.set(updateddate);
	}
	public int getOtweek() {
		return this.otweek.get();
	}
	public void setOtweek(int otweek) {
		this.otweek.set(otweek);
	}
	public int getOthours() {
		return this.othours.get();
	}
	public void setOthours(int othours) {
		this.othours.set(othours);
	}
	public int getStatus() {
		return this.status.get();
	}
	public void setStatus(int status) {
		this.status.set(status);
	}
	public String getChangeby() {
		return this.changeby.get();
	}
	public void setChangeby(String changeby) {
		this.changeby.set(changeby);
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
