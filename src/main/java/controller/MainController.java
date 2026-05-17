package controller;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Transactions;
import service.CategoryService;
import service.TransactionService;
import util.SessionManager;

public class MainController {
	private TransactionService tsv = new TransactionService();
	private CategoryService csv = new CategoryService();

    @FXML
    private TableColumn<Transactions, Double> amountCol;

    @FXML
    private TableColumn<Transactions, String> dateCol;

    @FXML
    private Button deleteBtn,  editBtn, addBtn;

    @FXML
    private TableColumn<Transactions, String> descripCol;

    @FXML
    private Button enBtn, frBtn;    

    @FXML
    private TableView<Transactions> recentTransacsTv;

    @FXML
    private Label totalIncomeLabel, welcomeBackLabel, totalExpensesLabel, currentBalanceLabel;

    @FXML
    private ComboBox<String> typeCb;

    @FXML
    private TableColumn<Transactions, String> typeCol;

    @FXML
    public void initialize() {
    	
    	// we welcome the current user logged in
    	welcomeBackLabel.setText("Welcome Back," + SessionManager.getInstance().getUserLoggedIn().getUserName());
    	
    	// load transactions
    	int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
    	List<Transactions> transacs = tsv.getTransactionsByUser(userId);
    	
    	// we use observable array list since TableView doesnt accept regular lists
    	// auto updates when theres changes
    	recentTransacsTv.setItems(FXCollections.observableArrayList(transacs));
    	
    	
    	// PropertyValueFactory basically connects the transaction model to the tableview columns
    	// for every row in the table, look at Transactions, call the getDate()>, get Type() 
    	// PVF uses reflection to find them automatically
    	dateCol.setCellValueFactory(new PropertyValueFactory<>("date")); 
    	descripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    	typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    	amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
    	
    	typeCb.getItems().addAll("Income" , "Expense");
    	typeCb.setValue("Income");
    }
    
    
    
    @FXML
    public void handleAdd(ActionEvent event) {
    	// we get value from type to differentiate
    	String type = typeCb.getValue();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTransaction.fxml")); // gets methods from AddTransaction
    	Stage stage = new Stage();
    	try {
			stage.setScene(new Scene(loader.load()));
			AddTransactionController addTransacCtl = loader.getController();
			addTransacCtl.setTransactionType(type);
			stage.showAndWait();
			
			int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
		    recentTransacsTv.setItems(FXCollections.observableArrayList(tsv.getTransactionsByUser(userId)));
		    currentBalanceLabel.setText(String.valueOf(tsv.getTotalBalance(userId)));
		    totalIncomeLabel.setText(String.valueOf(tsv.getTotalIncome(userId)));
		    totalExpensesLabel.setText(String.valueOf(tsv.getTotalExpense(userId)));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    public void handleDelete(ActionEvent event) {
    	Transactions selected = recentTransacsTv.getSelectionModel().getSelectedItem();
    	
    	if (selected == null) {
    		System.out.println("No transactions selected");
    		return;
    	}
    	
    	tsv.deleteTransaction(selected.getTransacId());
    	int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
	    recentTransacsTv.setItems(FXCollections.observableArrayList(tsv.getTransactionsByUser(userId)));
	    currentBalanceLabel.setText(String.valueOf(tsv.getTotalBalance(userId)));
	    totalIncomeLabel.setText(String.valueOf(tsv.getTotalIncome(userId)));
	    totalExpensesLabel.setText(String.valueOf(tsv.getTotalExpense(userId)));
    }

    @FXML
    public void handleEdit(ActionEvent event) {
    	Transactions selected = recentTransacsTv.getSelectionModel().getSelectedItem();
    	
    	if (selected == null) {
    		System.out.println("No transactions selected");
    		return;
    	}
    	
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTransaction.fxml"));
    		Stage stage = new Stage();
    		stage.setScene(new Scene(loader.load()));
    		AddTransactionController addTransacCtl = loader.getController();
    		addTransacCtl.setTransactionType(selected.getType());
    		addTransacCtl.setTransaction(selected);
    		stage.showAndWait();
    		int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
    	    recentTransacsTv.setItems(FXCollections.observableArrayList(tsv.getTransactionsByUser(userId)));
    	    currentBalanceLabel.setText(String.valueOf(tsv.getTotalBalance(userId)));
    	    totalIncomeLabel.setText(String.valueOf(tsv.getTotalIncome(userId)));
    	    totalExpensesLabel.setText(String.valueOf(tsv.getTotalExpense(userId)));
    	} catch (IOException e) {
    		
    	}
    }

}
