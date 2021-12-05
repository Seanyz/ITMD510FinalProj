package controllers;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The LoginController handles login action,
 * 1. Check user name and password
 * 2. Login and navigate to page base on user's role
 */

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.LoginModel;

public class LoginController {
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private Label sysMsg;
	private LoginModel model;
	
	public LoginController() {
		model = new LoginModel();
	}
	
	// Login 
	public void login() {
		String username = this.username.getText();
		String password = this.password.getText();
		
		//Validations
		if(username == null || username.trim().equals("")) {
			sysMsg.setText("User Name Cannot be empty or spaces");
			System.out.println("!!!User Name Cannot be empty or spaces");
			return;
		}
		if(password == null || password.trim().equals("")) {
			sysMsg.setText("Password Cannot be empty or spaces");
			System.out.println("!!!Password Cannot be empty or spaces");
			return;
		}
		if (username == null || username.trim().equals("") 
		&& (password == null || password.trim().equals(""))) {
			sysMsg.setText("User name / Password Cannot be empty or spaces");
			System.out.println("!!!User name / Password Cannot be empty or spaces");
			return;
		}
	
		// User name and password are not null, then to do authentication check
		checkCredentials(username, password);
	}
	
	// check credential and get roles
	public void checkCredentials(String username, String password) {
		Boolean isValid = model.getCredentials(username, password);
		if (!isValid) {
			sysMsg.setText("User does not exist or password incorrect!");
			System.out.println("!!!User does not exist or password incorrect!");
			return;
		}
		try {
			//AnchorPane root;
			Boolean isAdm = model.isAdmin();
			Boolean isMgr = model.isManager();
			Parent root = null;
			String mainTitle = "";
			
			// Go to admin page
			if (isAdm && isValid) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMenuView.fxml"));
				root = loader.load();
				AdminMenuController contrl = loader.getController();
				contrl.initData(username, isMgr, isAdm);
				mainTitle = "Admin login: " + username;
			} else if (isValid){ // go to user page
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecordListView.fxml"));
				root = loader.load();
				RecordListController contrl = loader.getController();
				contrl.initData(username, isMgr, isAdm);
				//root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/UserView.fxml"));
				if (isMgr) {
					mainTitle = "User login: " + username + " Manager";
				} else {
					mainTitle = "User login: " + username + " Normal User";
				}
			}
			Main.stage.setTitle(mainTitle);
			Scene scene = new Scene((AnchorPane)root);
			Main.stage.setScene(scene);
		} catch(Exception e) {
			System.out.println("!!!Error occured while inflating view: " + e);
		}
	}
	
	public void logout() {
		System.out.println("User logout.");
		showLogin();
	}
	
	public void showLogin() {
		System.out.println("Present login page.");
		AnchorPane root = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);
			Main.stage.setTitle("ITMD510 Final Project");
			Main.stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
