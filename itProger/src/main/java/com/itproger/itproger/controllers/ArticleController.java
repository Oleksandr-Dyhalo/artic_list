package com.itproger.itproger.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.itproger.itproger.DB;
import com.itproger.itproger.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ArticleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label intro;

    @FXML
    private Button read_article_btn;

    @FXML
    private Label title;

    @FXML
    private Label id;

    @FXML
    void initialize() {

        read_article_btn.setOnAction(event -> {
            try {
                readArticle();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void readArticle() throws SQLException, IOException {

        DB db = new DB();
        ResultSet res = db.getArticleToRead(id.getText());

        while (res.next()) {

            ReadArticleController.articleIdToRead = Integer.parseInt(res.getString("id"));
        }

        Stage stage = (Stage) read_article_btn.getScene().getWindow();

        try {
            HelloApplication.setScene("read_article.fxml", stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
