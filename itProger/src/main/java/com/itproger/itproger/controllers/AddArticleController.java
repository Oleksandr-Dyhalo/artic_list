package com.itproger.itproger.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.itproger.itproger.DB;
import com.itproger.itproger.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddArticleController {

    @FXML
    private Button back_btn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea full_text_field;

    @FXML
    private TextArea intro_field;

    @FXML
    private TextField title_field;

    @FXML
    void addArticle(ActionEvent event) {

        String title = title_field.getCharacters().toString();
        String intro = intro_field.getText();
        String full_text = full_text_field.getText();

        title_field.setStyle("-fx-border-color: #fafafa");
        intro_field.setStyle("-fx-border-color: #fafafa");
        full_text_field.setStyle("-fx-border-color: #fafafa");

        if (title.length() <= 5) {
            title_field.setStyle("-fx-border-color: #e06349");
        } else if (intro.length() <= 10) {
            intro_field.setStyle("-fx-border-color: #e06349");
        } else if (full_text.length() <= 15) {
            full_text_field.setStyle("-fx-border-color: #e06349");
        } else {
            DB db = new DB();
            db.addArticle(title, intro, full_text);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                HelloApplication.setScene("articles-panel.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void initialize() {

        back_btn.setOnAction(event -> go_to_articles_panel());
    }

    private void go_to_articles_panel() {

        Stage stage = (Stage) back_btn.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("articles-panel.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("itProger Program!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
