package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Transactions;
import model.User;
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
    	welcomeBackLabel.setText("Welcome Back," + SessionManager.getInstance().getUserLoggedIn().getUserName());
    	
    	recentTransacsTv.setItems(null);
    	
    	dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	
    	typeCb.getItems().addAll("Income" , "Expense");
    	typeCb.setValue("Income");
    }
    
    
    
    
    @FXML
    public void handleAdd(ActionEvent event) {
    	
    }

    @FXML
    public void handleDelete(ActionEvent event) {

    }

    @FXML
    public void handleEdit(ActionEvent event) {

    }

}
