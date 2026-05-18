package main;

import database.DBInitialize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.SessionManager;

import java.util.ResourceBundle;

/**
 * Main entry point for the Budgeting Application.
 * Initializes the database and launches the JavaFX UI.
 */

public class MainApp extends Application {
    /**
     * Starts the JavaFX application, loads the Login view with the current locale.
     * @param stage the primary stage for the application
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginRegister.fxml"), bundle);
        Scene scene = new Scene(loader.load(), 600, 400);
        stage.setTitle("Budgeting App");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Main method, initializes the database and launches the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        DBInitialize.initialize(); // initialize DB before launching UI
        launch(args);
    }
}