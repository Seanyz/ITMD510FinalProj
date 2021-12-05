package controllers;
/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The AdminMenuController takes responsibility of handling interaction 
 * of user behavior on "Admin Menu" page:
 * 	1. Click "User Management" button, navigate to user list page.
 *  2. Click "Record Management" button to navigate to records list page.
 */
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AdminMenuController implements Initializable {
	
	// Declare all elements on this page
	@FXML
	private Button usermgt;
	@FXML
	private Button recmgt;
	@FXML
	private Button logout;
	
	private LoginController loginctrl;
	public String accountid;
	public Boolean isMgr;
	public Boolean isAdm;
	Parent root = null;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Initialize Admin menu page view...");
	}
	
	// Initialize data, get user accoundid, and role indicators
	public void initData(String accountid, Boolean isMgr, Boolean isAdmin) {
		this.accountid = accountid;
		this.isMgr = isMgr;
		this.isAdm = isAdmin;
		this.loginctrl = new LoginController();
		System.out.println("Admin menu page loaded successfully.");
	}
	
	// This method is executed when user click 'User management" button, navigate
	// to user management page
	public void gotoUserList() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserListView.fxml"));
			root = loader.load();
			UserListController contrl = loader.getController();
			contrl.initData(accountid);
			Scene scene = new Scene((AnchorPane)root);
			Main.stage.setScene(scene);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// This method is executed when user click 'record management" button, navigate
	// to records management page
	public void gotoRecList() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecordListView.fxml"));
			root = loader.load();
			RecordListController contrl = loader.getController();
			contrl.initData(accountid, isMgr, isAdm);
			Scene scene = new Scene((AnchorPane)root);
			Main.stage.setScene(scene);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// logout method
	public void logout() {
		loginctrl.logout();
	}

	
}
