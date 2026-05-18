package controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import model.Transactions;
import service.CategoryService;
import service.TransactionService;
import util.SessionManager;
/**
 * Controller for managing the AddTransaction view.
 * Handles adding and editing transactions for the current user.
 */
public class AddTransactionController {
	
	private TransactionService tsv = new TransactionService();
	private CategoryService csv = new CategoryService();
	private String transactionType;
	
	private int transactionId;
	private boolean isEditing = false;

    @FXML
    private TextField amountTb;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<String> categoryCb;

    @FXML
    private Button confirmBtn;

    @FXML
    private DatePicker dateDp;

    @FXML
    private TextField descriptionTb;

    /**
     * Initializes the controller, populates the category ComboBox with the user's categories.
     */
    @FXML
    public void initialize() {
    	int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
    	List<Category> categories = csv.getCategoriesByUser(userId);
    	for (Category c : categories) {
    		categoryCb.getItems().add(c.getName());
    	}
    }

    /**
     * Sets the transaction type, converting from localized to English for DB storage.
     * @param type the localized transaction type
     */
    public void setTransactionType(String type) {
        this.transactionType = toEnglishType(type);
    }

    /**
     * Converts a localized type string to its English equivalent for DB storage.
     * @param type the localized type string
     * @return the English type string ("Income" or "Expense")
     */
    private String toEnglishType(String type) {
        ResourceBundle enBundle = ResourceBundle.getBundle("i18n/messages", Locale.ENGLISH);
        if (type.equals(enBundle.getString("app.income.type"))) return "Income";
        return "Expense";
    }

    /**
     * Handles the confirm button, saves or updates the transaction.
     */
    @FXML
    public void handleConfirm(){
    	try {
    		double amount = Double.parseDouble(amountTb.getText());
    		if (transactionType.equals("Expense")) {
    			amount = -Math.abs(amount);
    		}
    		
    		// validation == null checks
    		if (dateDp.getValue() == null) {
    			showAlert("Please select a date!");
    			return;
    		}
    		
    		if (descriptionTb.getText().isEmpty()) {
    			showAlert("Please enter a description!");
    			return;
    		}
    		if (amountTb.getText().isEmpty()) {
    			showAlert("Please enter an amount!");
    			return;
    		}
    		
    		
    		String description = descriptionTb.getText();
    		String date = dateDp.getValue().toString();
    		int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
    		int categoryIndex = categoryCb.getSelectionModel().getSelectedIndex();
    		Integer categoryId; // object wrapper, it can hold null value
    		if (categoryIndex >= 0) {
    			categoryId = csv.getCategoriesByUser(userId).get(categoryIndex).getCategoryId();
    		} else {
    			categoryId = null;
    		}
    		
    		if (isEditing) {
    			tsv.updateTransaction(transactionId,amount, date, description, transactionType, userId, categoryId);
    		} else {
    			tsv.saveTransaction(amount, date, description, transactionType, userId, categoryId);
    		}
    		handleCancel();
    	} catch (Exception e) {
    		System.out.println("Invalid Input" + e.getMessage());
    	}
    }

    /**
     * Handles the cancel button, closes the current stage.
     */
    @FXML
    public void handleCancel() {
    	Stage stage = (Stage) cancelBtn.getScene().getWindow();
    	stage.close();
    }

    /**
     * Populates the form fields with an existing transaction for editing.
     * @param t the transaction to edit
     */
	public void setTransaction(Transactions t) {
		
		descriptionTb.setText(t.getDescription());
		amountTb.setText(String.valueOf(Math.abs(t.getAmount())));
		dateDp.setValue(LocalDate.parse(t.getDate()));
		
		this.transactionId=t.getTransacId();
		this.isEditing=true;
	}
	
	private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
}
