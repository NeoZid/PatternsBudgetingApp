package controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import util.SessionManager;

public class LoginController {
	private UserService usv = new UserService();
	@FXML
	private TextField emailTf;
	@FXML
	private PasswordField passwordTf;
	@FXML
	private Button loginBtn, registerBtn;
	
	
	@FXML
	public void handleLogin() throws IOException {
		
		// checks if email and password is empty
		if (emailTf.getText().isEmpty() || passwordTf.getText().isEmpty()) {
			showAlert("Please fill in all fields!");
			return;
		}
		String email = emailTf.getText();
		String password = passwordTf.getText();
		Optional<User> user = usv.userLogin(email, password);
		if (user.isPresent()) {
			SessionManager.getInstance().setUserLoggedIn(user.get());
            ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"), bundle);
			Scene scene = new Scene(loader.load());
			Stage stage = (Stage) loginBtn.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} else {
			showAlert("Invalid email or password!!");
			return;
		}
	}

    @FXML
    public void handleRegister() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Register.fxml"), bundle);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginRegister.fxml"), bundle);
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
