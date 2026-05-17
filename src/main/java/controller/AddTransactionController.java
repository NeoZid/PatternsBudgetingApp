package controller;


import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
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
    
    @FXML
    public void initialize() {
    	int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
    	List<Category> categories = csv.getCategoriesByUser(userId);
    	for (Category c : categories) {
    		categoryCb.getItems().add(c.getName());
    	}
    }
    
    public void setTransactionType(String type) {
    	this.transactionType=type;
    }
    
    @FXML
    public void handleConfirm(){
    	try {
    		double amount = Double.parseDouble(amountTb.getText());
    		if (transactionType.equals("Expense")) {
    			amount = -Math.abs(amount);
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
    			tsv.updateTransaction(transactionId, amount, date, description, transactionType, categoryId);
    		} else {
    			tsv.saveTransaction(amount, date, description, transactionType, categoryId); 
    		}
    		
    		handleCancel();
    	} catch (Exception e) {
    		System.out.println("Invalid Input" + e.getMessage());
    	}
    }
    
    @FXML
    public void handleCancel() {
    	Stage stage = (Stage) cancelBtn.getScene().getWindow();
    	stage.close();
    }

	public void setTransaction(Transactions t) {
		descriptionTb.setText(t.getDescription());
		amountTb.setText(String.valueOf(Math.abs(t.getAmount())));
		dateDp.setValue(LocalDate.parse(t.getDate()));
		
		this.transactionId=t.getTransacId();
		this.isEditing=true;
		
		
	}
}
