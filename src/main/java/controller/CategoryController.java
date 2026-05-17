package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Category;
import service.CategoryService;

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
    
    
    public void initialize() {
    	// setup columns, load categories
    }

    @FXML
    public void handleAdd(ActionEvent event) {

    }

    @FXML
    public void handleBack(ActionEvent event) {

    }

    @FXML
    public void handleDelete(ActionEvent event) {

    }

    @FXML
    public void handleEdit(ActionEvent event) {

    }

}
