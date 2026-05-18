package controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
    private Button manageCategoriesButton;

    @FXML
    private PieChart spendingChart;

    @FXML
    public void initialize() {
    	
    	// we welcome the current user logged in
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        welcomeBackLabel.setText(MessageFormat.format(bundle.getString("app.welcome"), SessionManager.getInstance().getUserLoggedIn().getUserName()));
    	
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

        currentBalanceLabel.setText(MessageFormat.format(bundle.getString("app.balance"), tsv.getTotalBalance(userId)));
        totalIncomeLabel.setText(MessageFormat.format(bundle.getString("app.income"), tsv.getTotalIncome(userId)));
        totalExpensesLabel.setText(MessageFormat.format(bundle.getString("app.expense"), tsv.getTotalExpense(userId)));

        typeCb.getItems().addAll(bundle.getString("app.income.type"), bundle.getString("app.expense.type"));
        typeCb.setValue(bundle.getString("app.income.type"));

        // Pie chart initialising
        Map<String, Double> spending = tsv.getSpendingByCategory(userId);
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : spending.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        spendingChart.setData(pieData);
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
            ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
            currentBalanceLabel.setText(MessageFormat.format(bundle.getString("app.balance"), tsv.getTotalBalance(userId)));
            totalIncomeLabel.setText(MessageFormat.format(bundle.getString("app.income"), tsv.getTotalIncome(userId)));
            totalExpensesLabel.setText(MessageFormat.format(bundle.getString("app.expense"), tsv.getTotalExpense(userId)));
            refreshChart();
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
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        currentBalanceLabel.setText(MessageFormat.format(bundle.getString("app.balance"), tsv.getTotalBalance(userId)));
        totalIncomeLabel.setText(MessageFormat.format(bundle.getString("app.income"), tsv.getTotalIncome(userId)));
        totalExpensesLabel.setText(MessageFormat.format(bundle.getString("app.expense"), tsv.getTotalExpense(userId)));
        refreshChart();
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
            ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
            currentBalanceLabel.setText(MessageFormat.format(bundle.getString("app.balance"), tsv.getTotalBalance(userId)));
            totalIncomeLabel.setText(MessageFormat.format(bundle.getString("app.income"), tsv.getTotalIncome(userId)));
            totalExpensesLabel.setText(MessageFormat.format(bundle.getString("app.expense"), tsv.getTotalExpense(userId)));
            refreshChart();
    	} catch (IOException e) {
    		
    	}
    }

    @FXML
    public void handleManageCategories(ActionEvent event) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Category.fxml"), bundle);
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void handleEn(ActionEvent event) {
        SessionManager.getInstance().setCurrentLocale(Locale.ENGLISH);
        reloadScene();
    }

    @FXML
    public void handleFr(ActionEvent event) {
        SessionManager.getInstance().setCurrentLocale(Locale.FRENCH);
        reloadScene();
    }

    private void reloadScene() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"), bundle);
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            refreshChart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getEnglishType(String localizedType) {
        ResourceBundle enBundle = ResourceBundle.getBundle("i18n/messages", Locale.ENGLISH);
        if (localizedType.equals(enBundle.getString("app.income.type"))) {
            return "Income";
        } else {
            return "Expense";
        }
    }

    private void refreshChart() {
        int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
        Map<String, Double> spending = tsv.getSpendingByCategory(userId);
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : spending.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        spendingChart.setData(pieData);
    }
}
