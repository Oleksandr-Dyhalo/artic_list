package com.itproger.itproger;

import com.itproger.itproger.models.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {

        File file = new File("user.settings");

        if (file.exists()) {

            DB db = new DB();

            FileInputStream fis = new FileInputStream("user.settings");
            ObjectInputStream ois = new ObjectInputStream(fis);
            User user = (User) ois.readObject();
            ois.close();

            if (db.isExistsUser(user.getLogin())) {
                setScene("articles-panel.fxml", stage);
            } else {
                setScene("main.fxml", stage);
            }
        } else {
            setScene("main.fxml", stage);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setScene(String sceneName, Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("itProger Program!");
        stage.setScene(scene);
        stage.show();
    }
}