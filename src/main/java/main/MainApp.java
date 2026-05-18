package main;

import database.DBInitialize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.SessionManager;

import java.util.ResourceBundle;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", SessionManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginRegister.fxml"), bundle);
        Scene scene = new Scene(loader.load(), 600, 400);
        stage.setTitle("Budgeting App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DBInitialize.initialize(); // initialize DB before launching UI
        launch(args);
    }
}