package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	
	public void login() {
		String username = this.username.getText();
		String password = this.password.getText();
		
		System.out.printf("%s, %s \n", username, password);
		
		//Validations
		if(username == null || username.trim().equals("")) {
			sysMsg.setText("User Name Cannot be empty or spaces");
			return;
		}
		if(password == null || password.trim().equals("")) {
			sysMsg.setText("Password Cannot be empty or spaces");
			return;
		}
		if (username == null || username.trim().equals("") 
		&& (password == null || password.trim().equals(""))) {
			sysMsg.setText("User name / Password Cannot be empty or spaces");
			return;
		}
		if (username.equals("user") && password.equals("psw")) {
			sysMsg.setText("login succeeded!");
			return;
		}
		
		// User name and password are not null, then to do authentication check
		checkCredentials(username, password);
	}
	
	public void checkCredentials(String username, String password) {
		Boolean isValid = model.getCredentials(username, password);
		if (!isValid) {
			sysMsg.setText("User does not exist!");
			return;
		}
		try {
			AnchorPane root;
			if (model.isAdmin() && isValid) {
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
				Main.stage.setTitle("AdminView");
			} else {
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/UserView.fxml"));
				Main.stage.setTitle("UserView");
			}
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}
}
