package controllers;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The EditReocrdController handles interaction on add a new work
 * hour request page
 * 1. Input apply work hours
 * 2. Click save to insert new record and update user-records information, and then
 * navigate to record list page
 * 3. Click cancel back to record list page
 */

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.OTRecord;
import models.OTRecordModel;
import models.User;
import models.UserModel;
import models.UserRecord;
import models.UserRecordModel;

public class EditRecordController implements Initializable {
	
	// Elements in scene
	@FXML
	private Label accountidLabel;
	@FXML
	private Label dept;
	@FXML
	private Label mgr;
	@FXML
	private Label aprvhour;
	@FXML
	private Label apphour;
	@FXML
	private Label remainhour;
	@FXML
	private TextField addhour;
	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;
	
	private String accountid;
	private Boolean isMgr;
	private Boolean isAdm;
	
	private UserRecordModel urcdm;
	private UserRecord userRecd;
	private OTRecordModel recdm;
	private OTRecord rec;
	private User um;
	private Alert alert;
	private String errmsg;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Load and initialize add record page...");
	}
	
	// Load data base on user accountid
	public void initData(String accountid, Boolean isMgr, Boolean isAdm) {
		this.accountid = accountid;
		this.isMgr = isMgr;
		this.isAdm = isAdm;
		urcdm = new UserRecordModel(accountid);
		getData();
		alert = new Alert(AlertType.INFORMATION);
		alert.titleProperty().set("Error Message");
		System.out.println("Add record page loaded successfully.");
	}
	
	// set user's account info and present on page
	public void getData() {
		userRecd = urcdm.getCurUserRecord();
		this.accountidLabel.setText(userRecd.getAccountid());
		this.dept.setText(userRecd.getDept());
		this.mgr.setText(userRecd.getMgr());
		this.aprvhour.setText(String.valueOf(userRecd.getAot()));
		this.apphour.setText(String.valueOf(userRecd.getWot()));
		this.remainhour.setText(String.valueOf(userRecd.getRhour()));
	}
	
	// save a new record, insert into otrecord table and update userrecords table
	public void save() {
		if (Integer.valueOf(this.remainhour.getText()) < 
				Integer.valueOf(this.addhour.getText())) {
			errmsg = "Apply hours must less than your remain hours.";
			alert.headerTextProperty().set(errmsg);
			alert.showAndWait();
			return;
		}
		um = new UserModel().findUser(accountid);
		int dept = um.getDeptid();
		rec = new OTRecord(1, this.accountidLabel.getText(), 
				Integer.valueOf(this.addhour.getText()), 1, 
				dept, this.dept.getText());
		System.out.println(rec.getStatusid());
		recdm = new OTRecordModel();
		recdm.addOTRecord(rec);
		backToRecordList();
	}
	
	// cancel and back to records list page
	public void cancel() {
		backToRecordList();
	}
	
	public void backToRecordList() {
		Parent root = null;
		String mainTitle = "";
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecordListView.fxml"));
			root = loader.load();
			RecordListController contrl = loader.getController();
			contrl.initData(accountid, isMgr, isAdm);
			if (isAdm) {
				mainTitle = "Admin login: " + accountid;
			}
			else if (isMgr) {
				mainTitle = "User login: " + accountid + " Manager";
			} else {
				mainTitle = "User login: " + accountid + " Normal User";
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		Main.stage.setTitle(mainTitle);
		Scene scene = new Scene((AnchorPane)root);
		Main.stage.setScene(scene);
	}

}
