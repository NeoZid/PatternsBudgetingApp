package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CategoryService;

import java.io.IOException;

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
    public void handleCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Category.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleConfirm(ActionEvent event) throws IOException {
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
