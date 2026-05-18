package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Category;
import model.Transactions;
import service.CategoryService;
import util.SessionManager;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryController {
	private CategoryService csv = new CategoryService();

	@FXML private TableColumn<Category, String> nameCol;
	@FXML private TableColumn<Category, String> typeCol;
    @FXML
    private Button addBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Category> categoryTv;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;
    
    @FXML
    public void initialize() {
    	// setup columns, load categories
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
        List<Category> categories = csv.getCategoriesByUser(userId);

        categoryTv.setItems(FXCollections.observableArrayList(categories));

    }

    @FXML
    public void handleAdd(ActionEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCategory.fxml"), bundle);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"), bundle);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleDelete(ActionEvent event) {
    	Category selected = categoryTv.getSelectionModel().getSelectedItem();
    	if (selected == null) {
    		showAlert("Please select a category first!");
    		return;
    	}
        csv.deleteCategory(categoryTv.getSelectionModel().getSelectedItem().getCategoryId());
        int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
        categoryTv.setItems(FXCollections.observableArrayList(csv.getCategoriesByUser(userId)));
    }

    @FXML
    public void handleEdit(ActionEvent event) {
        Category selected = categoryTv.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("No category selected");
            return;
        }

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCategory.fxml"), bundle);
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.setScene(scene);
            AddCategoryController addCategoryController = loader.getController();
            addCategoryController.setCategory(selected);
            stage.showAndWait();
            int userId = SessionManager.getInstance().getUserLoggedIn().getUserId();
            categoryTv.setItems(FXCollections.observableArrayList(csv.getCategoriesByUser(userId)));


        } catch (IOException e) {

        }
    }
    
    private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }

}
