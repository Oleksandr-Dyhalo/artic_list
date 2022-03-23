package com.itproger.itproger.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.itproger.itproger.DB;
import com.itproger.itproger.HelloApplication;
import com.itproger.itproger.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegController {

    private DB db = new DB();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button auth_btn;

    @FXML
    private TextField auth_login;

    @FXML
    private PasswordField auth_pass;

    @FXML
    private Button reg_btn;

    @FXML
    private TextField reg_email;

    @FXML
    private TextField reg_login;

    @FXML
    private PasswordField reg_password;

    @FXML
    private CheckBox reg_rights;

    @FXML
    private Button to_reset_window_btn;

    @FXML
    void initialize() {

        reg_btn.setOnAction(event -> registrationUser());
        auth_btn.setOnAction(event -> {
            try {
                authUser(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        to_reset_window_btn.setOnAction(event -> go_to_reset_data_window());
    }

    private void go_to_reset_data_window() {

        Stage stage = (Stage) to_reset_window_btn.getScene().getWindow();
        try {
            HelloApplication.setScene("resetData.fxml", stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authUser(ActionEvent event) throws IOException {

        String login = auth_login.getCharacters().toString();
        String pass = auth_pass.getCharacters().toString();

        auth_login.setStyle("-fx-border-color: #fafafa");
        auth_pass.setStyle("-fx-border-color: #fafafa");

        if (login.length() <= 1) {
            auth_login.setStyle("-fx-border-color: #e06349");
        } else if (pass.length() <= 3) {
            auth_pass.setStyle("-fx-border-color: #e06349");
        } else if (!db.userExists(login, md5String(pass))) {
            auth_btn.setText("Пользователя нет");
            auth_login.setText("");
            auth_pass.setText("");
        } else {
            auth_login.setText("");
            auth_pass.setText("");
            auth_btn.setText("Авторизация успешна:)");

            FileOutputStream fos = new FileOutputStream("user.settings");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new User(login));
            oos.close();

            Stage stage = (Stage) to_reset_window_btn.getScene().getWindow();
            try {
                HelloApplication.setScene("articles-panel.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registrationUser() {
        String login = reg_login.getCharacters().toString();
        String email = reg_email.getCharacters().toString();
        String pass = reg_password.getCharacters().toString();

        reg_login.setStyle("-fx-border-color: #fafafa");
        reg_email.setStyle("-fx-border-color: #fafafa");
        reg_password.setStyle("-fx-border-color: #fafafa");

        if (login.length() <= 1) {
            reg_login.setStyle("-fx-border-color: #e06349");
        } else if (email.length() < 5 || !email.contains("@") || !email.contains(".")) {
            reg_email.setStyle("-fx-border-color: #e06349");
        } else if (pass.length() <= 3) {
            reg_password.setStyle("-fx-border-color: #e06349");
        } else if (!reg_rights.isSelected()) {
            reg_btn.setText("Поставьте галочку");
        } else if (db.isExistsUser(login)) {
            reg_btn.setText("Введите другой логин");
        } else {
            db.regUser(login, email, md5String(pass));
            reg_login.setText("");
            reg_email.setText("");
            reg_password.setText("");
            reg_btn.setText("Регистрация успешна:)");
        }
    }

    public static String md5String(String pass) {

        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}
