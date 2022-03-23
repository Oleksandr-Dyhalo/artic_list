package com.itproger.itproger.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.itproger.itproger.DB;
import com.itproger.itproger.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReadArticleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label article_title;

    @FXML
    private VBox panel_vbox;

    @FXML
    private TextArea text_area;

    @FXML
    private Button to_articles_menu_btn;

    public static int articleIdToRead = 0;

    @FXML
    void initialize() throws SQLException {
        DB db = new DB();
        ResultSet res = db.getArticleToRead(String.valueOf(articleIdToRead));

        while (res.next()) {
            article_title.setText(res.getString("title"));
            text_area.setText(res.getString("text"));
        }

        to_articles_menu_btn.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                HelloApplication.setScene("articles-panel.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
