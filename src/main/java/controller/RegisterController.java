package controller;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.UserService;
import util.SessionManager;


public class RegisterController {
	UserService usv = new UserService();
	@FXML
	private TextField userReTf, emailReTf;
	@FXML
	private PasswordField passwordReTf;
	@FXML
	private ComboBox<String> currencyCb;
	@FXML
	private Button registerBtn, returnLoginButton;
	
	
	@FXML
	public void initialize() {
		currencyCb.getItems().addAll("CAD","USD","EUR","PHP");
	}
	
	public void handleRegister() throws IOException {
		
		
		String username = userReTf.getText();
		String email = emailReTf.getText();
		String password = passwordReTf.getText();
		String currency = currencyCb.getValue();
		
		// check empty 
		if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
			showAlert("Please fill in all fields!");
			return;
		}
		
		// check email format 
		if (!email.contains("@") || !email.contains(".")) {
			showAlert("Please enter a valid email address!");
			return;
		}
		
		// pass length 
		if (password.length() < 6) {
			showAlert("Password must be atleast 6 characters!");
			return;
		}
		
		// user name length
		if (username.length() < 3) {
			showAlert("Username must atleast be 3 characters");
			return;
		}
		 
		if (currency == null) {
			showAlert("Please select a currency!");
			return;
		}
		
		
		
		if (usv.registerUser(email, username, password,currency)) {
            ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginRegister.fxml"), bundle);
			Scene scene = new Scene(loader.load());
			Stage stage = (Stage) registerBtn.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} else {
			showAlert("Registration failed!");
			return;
		}
	}
	
	public void handleBackToLogin() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginRegister.fxml"), bundle);
		Scene scene = new Scene(loader.load());
		Stage stage = (Stage) registerBtn.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	// alert boxes helper method
    private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
}
