package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CategoryService;
import util.SessionManager;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        typeCb.getItems().addAll(bundle.getString("app.income.type"), bundle.getString("app.expense.type"));
    }

    @FXML
    public void handleCancel(ActionEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Category.fxml"), bundle);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleConfirm(ActionEvent event) throws IOException {
        String name = nameTf.getText();
        String type = toEnglishType(typeCb.getValue());
    	
    	if (isEditing ) {
    		csv.updateCategory(categoryId, name, type);
    	} else {
    		csv.saveCategory(name, type);
    	}
    	handleCancel(event);
    }

    private String toEnglishType(String type) {
        ResourceBundle enBundle = ResourceBundle.getBundle("i18n/messages", Locale.ENGLISH);
        if (type.equals(enBundle.getString("app.income.type"))) return "Income";
        return "Expense";
    }
    
    private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }

}
