package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.AdminModel;
import models.User;
import models.UserModel;

public class AdminController {

	// Table
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
	private Button refBtn;
	@FXML
	private Button addBtn;
	@FXML
	private Button updtBtn;
	@FXML
	private Button delBtn;

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

	public AdminController() {
		model = new UserModel();
	}

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

		TableViewSelectionModel<User> selectionModel = userTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldVal, User newVal) {
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

	public void addUser() {
		System.out.println("Add user");
		if (checkUserInfo() != null) {
			rs = model.addUser(setUser);
			if (rs > 0) {
				userTable.getItems().add(setUser);
			} else {
				System.out.println("insert failed!");
			}
		}
	}

	public void updateUser() {
		System.out.println("updated user");
		if (checkUserInfo() != null) {
			rs = model.updateUser(setUser);
			if (rs > 0) {
				userList.removeAll(users);
				getUserList();
			}
		}
	}

	public void delUser() {
		System.out.println("del user");
		if (checkUserInfo() != null) {
			rs = model.deleteUser(setUser);
			if (rs > 0) {
				userList.removeAll(users);
				getUserList();
			}
		}
	}

}
