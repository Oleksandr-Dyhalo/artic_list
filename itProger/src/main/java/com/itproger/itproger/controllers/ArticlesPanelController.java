package com.itproger.itproger.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.itproger.itproger.DB;
import com.itproger.itproger.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArticlesPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit_btn, add_article_btn;

    @FXML
    private VBox panel_vbox;

    @FXML
    void initialize() throws SQLException, IOException {

        DB db = new DB();
        ResultSet resultSet = db.getArticles();

        while (resultSet.next()) {

            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("article.fxml")));

            Label id = (Label) node.lookup("#id");
            id.setText(resultSet.getString("id"));

            Label title = (Label) node.lookup("#title");
            title.setText(resultSet.getString("title"));

            Label intro = (Label) node.lookup("#intro");
            intro.setText(resultSet.getString("intro"));

            node.setOnMouseEntered(event -> node.setStyle("-fx-background-color: #707173"));
            node.setOnMouseExited(event -> node.setStyle("-fx-background-color: #343434"));

            panel_vbox.getChildren().add(node);
            panel_vbox.setSpacing(10);
        }

        exit_btn.setOnAction(event -> {
            try {
                exitUser(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        add_article_btn.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                HelloApplication.setScene("add_article.fxml", stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void exitUser(ActionEvent event) throws IOException {

        File file = new File("user.settings");
        file.delete();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HelloApplication.setScene("main.fxml", stage);
    }

}
