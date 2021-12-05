package controllers;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The RecordListController handles interactions on record list page: 
 * 1. Get and present record list based on user role
 * 2. Add, delete, approve, reject records
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.LoginModel;
import models.OTRecord;
import models.OTRecordModel;
import models.UserModel;

public class RecordListController implements Initializable {

	// Table of OT records
	@FXML
	private TableView<OTRecord> otRecordTable;
	@FXML
	private TableColumn<OTRecord, Integer> rid;
	@FXML
	private TableColumn<OTRecord, String> account;
	@FXML
	private TableColumn<OTRecord, Integer> hour;
	@FXML
	private TableColumn<OTRecord, Integer> statusid;
	@FXML
	private TableColumn<OTRecord, String> dept;


	@FXML
	private Button addBtn;
	@FXML
	private Button delBtn;
	@FXML
	private Button logoutBtn;
	@FXML
	private Button aprvBtn;
	@FXML
	private Button rejBtn;

	private ArrayList<OTRecord> otrecords = new ArrayList<OTRecord>();
	private ObservableList<OTRecord> otrecordList;
	private UserModel usermd;
	private OTRecordModel otrmd;
	private LoginController loginctrl;
	private int curotrid;
	private int curstatus;
	private String accountid;
	private Boolean isMgr;
	private Boolean isAdm;

	// constructor
	public RecordListController() {
		this.usermd = new UserModel();
		this.otrmd = new OTRecordModel();
		this.loginctrl = new LoginController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Initialize record list page...");
	}
	
	// Initialize data which present on page
	public void initData(String accountid, Boolean isMgr, Boolean isAdm) {
		this.accountid = accountid;
		this.isMgr = isMgr;
		this.isAdm = isAdm;

		getOTRecords();
		monitorTable();
		System.out.println("Record list page loaded successfully.");
	}
	
	
	// Get records base on user role
	public void getOTRecords() {
		if (this.isAdm) { // get all records
			aprvBtn.setVisible(true);
			rejBtn.setVisible(true);
			otrecords = otrmd.getOTRecords(3, this.accountid);
		} else if (this.isMgr) { // get records in own department
			aprvBtn.setVisible(true);
			rejBtn.setVisible(true);
			otrecords = otrmd.getOTRecords(2, this.accountid);
		} else { // get records only for belong this user
			aprvBtn.setVisible(false);
			rejBtn.setVisible(false);
			otrecords = otrmd.getOTRecords(1, this.accountid);
		}

		if (otrecords.size() == 0) {
			return;
		}
		otrecordList = FXCollections.observableList(otrecords);

		rid.setCellValueFactory(new PropertyValueFactory<OTRecord, Integer>("id"));
		account.setCellValueFactory(new PropertyValueFactory<OTRecord, String>("accountid"));
		hour.setCellValueFactory(new PropertyValueFactory<OTRecord, Integer>("othours"));
		dept.setCellValueFactory(new PropertyValueFactory<OTRecord, String>("department"));
		statusid.setCellValueFactory(new PropertyValueFactory<OTRecord, Integer>("statusid"));
		
		otRecordTable.setItems(otrecordList);
	}

	// refresh table when add, update and delete records
	public void refrTable() {
		otrecordList.removeAll(otrecordList);
		getOTRecords();
	}
	
	// monitor user select row in table
	public void monitorTable() {
		TableViewSelectionModel<OTRecord> selectionModel = otRecordTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		otRecordTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OTRecord>() {
			@Override
			public void changed(ObservableValue<? extends OTRecord> observable, OTRecord oldVal, OTRecord newVal) {
				if (newVal == null) {
					return;
				}
				curotrid = newVal.getId();
				curstatus = newVal.getStatusid();
			}
		});
	}

	
	// delete one record from table
	public void delRecord() {
		
		if (isAdm) {
			otrmd.deleteOTRecord(curotrid);
			refrTable();
		} else {
			if (curstatus != 1) {
				System.out.println("This record has been approved or rejected, cannot delete!");
				return;
			} else {
				otrmd.deleteOTRecord(curotrid);
				refrTable();
			}
		}
	}
	
	// click add button, navigate to add record page
	public void addRecord() {
		Parent root = null;
		String mainTitle = "Add new application - user: " + accountid;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditRecordView.fxml"));
			root = loader.load();
			EditRecordController contrl = loader.getController();
			contrl.initData(accountid, isMgr, isAdm);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Main.stage.setTitle(mainTitle);
		Scene scene = new Scene((AnchorPane)root);
		Main.stage.setScene(scene);
	}
	
	// Approve one record
	public void aprvRecord() {
		
		if (curstatus != 1) {
			System.out.println("This record cannot be approved or rejected again!");
			return;
		} else {
			otrmd.updateOTRecordStatus(curotrid, 2);
			refrTable();
		}
	}

	// Reject one record
	public void rejRecord() {
		
		if (curstatus != 1) {
			System.out.println("This record cannot be approved or rejected again!");
			return;
		} else {
			otrmd.updateOTRecordStatus(curotrid, 3);
			refrTable();
		}
	}

	//logout
	public void logout() {
		loginctrl.logout();
	}

}
