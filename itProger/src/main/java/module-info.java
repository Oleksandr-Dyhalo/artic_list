module com.itproger.itproger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires javafx.graphics;


    opens com.itproger.itproger to javafx.fxml;
    exports com.itproger.itproger;
    exports com.itproger.itproger.controllers;
    opens com.itproger.itproger.controllers to javafx.fxml;
    exports com.itproger.itproger.models;
    opens com.itproger.itproger.models to javafx.fxml;
}