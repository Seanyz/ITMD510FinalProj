package models;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The UserRecord class declares "UserRecord" object
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserRecord {
	
	// Declare properties
	private final SimpleStringProperty accountid;
	private final SimpleStringProperty dept;
	private final SimpleStringProperty mgr;
	private final SimpleIntegerProperty aot;
	private final SimpleIntegerProperty wot;
	private final SimpleIntegerProperty rhour;
	
	//Constructor
	public UserRecord(String accountid, String dept, String mgr, int aot, 
			int wot, int rhour){
		this.accountid = new SimpleStringProperty(accountid);
		this.dept = new SimpleStringProperty(dept);
		this.mgr = new SimpleStringProperty(mgr);
		this.aot = new SimpleIntegerProperty(aot);
		this.wot = new SimpleIntegerProperty(wot);
		this.rhour = new SimpleIntegerProperty(rhour);
	}
	
	// All sets and gets methods
	public String getAccountid() {
		return this.accountid.get();
	}
	public void setAccountid(String accountid) {
		this.accountid.set(accountid);
	}
	public String getDept() {
		return this.dept.get();
	}
	public void setDept(String dept) {
		this.dept.set(dept);
	}
	public String getMgr() {
		return this.mgr.get();
	}
	public void setMgr(String mgr) {
		this.mgr.set(mgr);
	}
	public int getAot() {
		return this.aot.get();
	}
	public void setAot(int aot) {
		this.aot.set(aot);
	}
	public int getWot() {
		return this.wot.get();
	}
	public void setWot(int wot) {
		this.wot.set(wot);
	}
	public int getRhour() {
		return this.rhour.get();
	}
	public void setRhour(int rhour) {
		this.rhour.set(rhour);
	}
}
