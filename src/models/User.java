package models;
/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The User class declares "user" object
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
	
	// Declare all properties of User object
	private final SimpleStringProperty accountid;
	private final SimpleStringProperty first;
	private final SimpleStringProperty last;
	private final SimpleIntegerProperty deptid;
	private final SimpleStringProperty password;
	private final SimpleIntegerProperty isAdmin;
	private final SimpleIntegerProperty isMgr;
	
	// Constructor
	public User(String accountid, String first, String last, int deptid, String password, int isAdmin, int isMgr){
		this.accountid = new SimpleStringProperty(accountid);
		this.first = new SimpleStringProperty(first);
		this.last = new SimpleStringProperty(last);
		this.deptid = new SimpleIntegerProperty(deptid);
		this.password = new SimpleStringProperty(password);
		this.isAdmin = new SimpleIntegerProperty(isAdmin);
		this.isMgr = new SimpleIntegerProperty(isMgr);
	}
	
	// All sets and gets methods
	public String getAccountid() {
		return this.accountid.get();
	}
	public void setAccountid(String accountid) {
		this.accountid.set(accountid);
	}
	public String getFirst() {
		return this.first.get();
	}
	public void setFirst(String first) {
		this.first.set(first);
	}
	public String getLast() {
		return this.last.get();
	}
	public void setLast(String last) {
		this.last.set(last);
	}
	public int getDeptid() {
		return this.deptid.get();
	}
	public void setDeptid(int deptid) {
		this.deptid.set(deptid);
	}
	public String getPassword() {
		return this.password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public int getIsAdmin() {
		return this.isAdmin.get();
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin.set(isAdmin);
	}
	public int getIsMgr() {
		return this.isMgr.get();
	}
	public void setIsMgr(int isMgr) {
		this.isMgr.set(isMgr);
	}
}
