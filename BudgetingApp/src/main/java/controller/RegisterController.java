package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.UserService;


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
		if (usv.registerUser(username, email, password,currency)) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginRegister.fxml"));
			Scene scene = new Scene(loader.load());
			Stage stage = (Stage) registerBtn.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} else {
			System.out.println("fail");
		}
	}
	
	public void handleBackToLogin() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginRegister.fxml"));
		Scene scene = new Scene(loader.load());
		Stage stage = (Stage) registerBtn.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}
