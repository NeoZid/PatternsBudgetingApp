package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CategoryService;

public class AddCategoryController {
	private CategoryService csv = new CategoryService();
	private int categoryId;
	private boolean isEditing = false;
    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private TextField nameTf;

    @FXML
    private ComboBox<String> typeCb;
    
    public void setCategory(model.Category c) {
    	nameTf.setText(c.getName());
    	typeCb.setValue(c.getType());
    	this.categoryId=c.getCategoryId();
    	this.isEditing=true;
    }
    
    @FXML
    public void initialize() {
    	typeCb.getItems().addAll("Income" , "Expense");
    }

    @FXML
    public void handleCancel(ActionEvent event) {
    	Stage stage = (Stage) cancelBtn.getScene().getWindow();
    	stage.close();
    }

    @FXML
    public void handleConfirm(ActionEvent event) {
    	String name = nameTf.getText();
    	String type = typeCb.getValue();
    	
    	if (isEditing ) {
    		csv.updateCategory(categoryId, name, type);
    	} else {
    		csv.saveCategory(name, type);
    	}
    	handleCancel(event);
    }

}
