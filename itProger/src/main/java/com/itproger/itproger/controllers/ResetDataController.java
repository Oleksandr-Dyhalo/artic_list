package com.itproger.itproger.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.itproger.itproger.DB;
import com.itproger.itproger.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.itproger.itproger.controllers.RegController.md5String;

public class ResetDataController {

    private DB db = new DB();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_btn;

    @FXML
    private Button reset_data_btn;

    @FXML
    private TextField reset_email;

    @FXML
    private TextField reset_login;

    @FXML
    private PasswordField reset_password;

    @FXML
    private PasswordField old_password;

    @FXML
    public TextField old_login;

    @FXML
    void initialize() {

        reset_data_btn.setOnAction(event -> resetUserData());
        back_btn.setOnAction(event -> go_to_main_page());
    }

    private void go_to_main_page() {

        Stage stage = (Stage) back_btn.getScene().getWindow();

        try {
            HelloApplication.setScene("main.fxml", stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetUserData() {

        String oldLogin = old_login.getCharacters().toString();
        String oldPassword = old_password.getCharacters().toString();
        String login = reset_login.getCharacters().toString();
        String email = reset_email.getCharacters().toString();
        String pass = reset_password.getCharacters().toString();

        old_login.setStyle("-fx-border-color: #fafafa");
        old_password.setStyle("-fx-border-color: #fafafa");
        reset_login.setStyle("-fx-border-color: #fafafa");
        reset_email.setStyle("-fx-border-color: #fafafa");
        reset_password.setStyle("-fx-border-color: #fafafa");

        if (login.length() <= 1) {
            reset_login.setStyle("-fx-border-color: #e06349");
        } else if (oldLogin.length() <= 1) {
            old_login.setStyle("-fx-border-color: #e06349");
        } else if (email.length() < 5 && email.length() > 0 || email.length() > 0 && !email.contains("@") || email.length() > 0 && !email.contains(".")) {
            reset_email.setStyle("-fx-border-color: #e06349");
        } else if (pass.length() <= 3) {
            reset_password.setStyle("-fx-border-color: #e06349");
        } else if (oldPassword.length() <= 3) {
            old_password.setStyle("-fx-border-color: #e06349");
        } else if (db.isExistsUser(login)) {
            reset_login.setText("");
            reset_login.setPromptText("Введите другой логин");
            reset_login.setStyle("-fx-border-color: #e06349");
        } else if (!db.userExists(oldLogin, md5String(oldPassword))) {
            reset_data_btn.setText("Пользователя нет");
            old_login.setText("");
            old_password.setText("");
            reset_login.setText("");
            reset_email.setText("");
            reset_password.setText("");
        } else {
            db.resetData(login, email, md5String(pass), oldLogin);
            old_login.setText("");
            old_password.setText("");
            reset_login.setText("");
            reset_login.setPromptText("Новый логин");
            reset_email.setText("");
            reset_password.setText("");
            reset_data_btn.setText("Данные обновлены:)");
        }
    }
}
