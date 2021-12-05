package controllers;

/**
 * @author: YiZhang
 * @date: Dec-01-2021
 * @version: 1.0
 * @description: The UserListController handles interaction on user list page:
 * 1. Present user list
 * 2. update user's information
 * 3. add / delete one user
 */

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import models.User;
import models.UserModel;

public class UserListController implements Initializable {

	// Table view
	@FXML
	private TableView<User> userTable;
	@FXML
	private TableColumn<User, String> accountid;
	@FXML
	private TableColumn<User, String> first;
	@FXML
	private TableColumn<User, String> last;
	@FXML
	private TableColumn<User, Integer> deptid;
	@FXML
	private TableColumn<User, String> password;
	@FXML
	private TableColumn<User, Integer> isAdmin;
	@FXML
	private TableColumn<User, Integer> isMgr;

	// User information set / get area
	@FXML
	private TextField curAccount;
	@FXML
	private TextField curFName;
	@FXML
	private TextField curLName;
	@FXML
	private TextField curDept;
	@FXML
	private TextField curPassword;
	@FXML
	private CheckBox curIsAdmin;
	@FXML
	private CheckBox curIsMgr;
	// Operation buttons
	
	@FXML
	private Button addBtn;
	@FXML
	private Button updtBtn;
	@FXML
	private Button delBtn;
	@FXML
	private Button logoutBtn;

	private String setAccount;
	private String setFirst;
	private String setLast;
	private int setDept;
	private String setPassword;
	private int setIsAdmin;
	private int setIsMgr;
	private User setUser;
	private int rs;

	private ArrayList<User> users = new ArrayList<User>();
	private ObservableList<User> userList;
	private UserModel model;
	private LoginController loginctrl;
	private String loginuser;
	
	// constructor
	public UserListController() {
		model = new UserModel();
		loginctrl = new LoginController();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Initialize user list page...");
		getUserList();
		monitorTable();
		System.out.println("User list page loaded successfully.");
	}
	
	// initialize data to get login user account id
	public void initData(String loginaccountid) {
		this.loginuser = loginaccountid;
	}

	// Check user input values
	public User checkUserInfo() {
		setAccount = curAccount.getText();
		setFirst = curFName.getText();
		setLast = curLName.getText();
		setDept = Integer.parseInt(curDept.getText());
		setPassword = curPassword.getText();
		setIsAdmin = curIsAdmin.isSelected() == true ? 1 : 0;
		setIsMgr = curIsMgr.isSelected() == true ? 1 : 0;

		if (setAccount == null || setAccount.trim().equals("")) {
			System.out.println("Please entry 'account'");
			return null;
		}
		if (setFirst == null || setFirst.trim().equals("")) {
			System.out.println("Please entry 'first name'");
			return null;
		}
		if (setLast == null || setLast.trim().equals("")) {
			System.out.println("Please entry 'last name'");
			return null;
		}
		if (setPassword == null || setPassword.trim().equals("")) {
			System.out.println("Please entry 'password");
			return null;
		}
		if (setDept < 1 || setDept > 4) {
			System.out.println("The deptid must between 1 - 4");
			return null;
		}
		if (setIsAdmin < 0 || setIsAdmin > 1 || setIsMgr < 0 || setIsMgr > 1) {
			System.out.println("The isAmdin, isMgr must 0 or 1");
			return null;
		}
		setUser = new User(setAccount, setFirst, setLast, setDept, setPassword, setIsAdmin, setIsMgr);
		return setUser;
	}
	
	// get all users list from DB
	public void getUserList() {
		users = model.getUsers();
		userList = FXCollections.observableList(users);

		accountid.setCellValueFactory(new PropertyValueFactory<User, String>("accountid"));
		first.setCellValueFactory(new PropertyValueFactory<User, String>("first"));
		last.setCellValueFactory(new PropertyValueFactory<User, String>("last"));
		deptid.setCellValueFactory(new PropertyValueFactory<User, Integer>("deptid"));
		password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
		isAdmin.setCellValueFactory(new PropertyValueFactory<User, Integer>("isAdmin"));
		isMgr.setCellValueFactory(new PropertyValueFactory<User, Integer>("isMgr"));
		userTable.setItems(userList);
	}
	
	// monitor table selected reow
	public void monitorTable() {
		TableViewSelectionModel<User> selectionModel = userTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldVal, User newVal) {
				if (newVal == null) { return; }
				curAccount.setText(newVal.getAccountid());
				curFName.setText(newVal.getFirst());
				curLName.setText(newVal.getLast());
				curDept.setText(String.valueOf(newVal.getDeptid()));
				curPassword.setText(newVal.getPassword());
				curIsAdmin.setSelected(newVal.getIsAdmin() == 1 ? true : false);
				curIsMgr.setSelected(newVal.getIsMgr() == 1 ? true : false);
			}
		});
	}
	
	// refresh table view
	public void refrTable() {
		userList.removeAll(userList);
		getUserList();
	}

	// add a new user
	public void addUser() {
		
		if (checkUserInfo() != null) {
			rs = model.addUser(setUser);
			if (rs > 0) {
				userTable.getItems().add(setUser);
				refrTable();
				System.out.println("Add a new user successfully.");
			} else {
				System.out.println("!!!Insert new user failed!");
				return;
			}
		}
	}

	// update user information
	public void updateUser() {
		
		if (checkUserInfo() != null) {
			rs = model.updateUser(setUser);
			if (rs > 0) {
				refrTable();
				System.out.println("Updated user info successfully.");
			} else {
				System.out.println("!!!update user info failed!");
				return;
			}
		}
	}

	// delete a user
	public void delUser() {
		if (curAccount.getText().equals(loginuser)) {
			System.out.println("!!!Your cannot delete current login user.");
			return;
		} else if (checkUserInfo() != null) {
			rs = model.deleteUser(setUser);
			if (rs > 0) {
				refrTable();
				System.out.println("Delete select user successfully.");
			} else {
				System.out.println("Delete user failed!");
				return;
			}
		}
	}
	
	// logout
	public void logout() {
		loginctrl.logout();
	}
	

}
