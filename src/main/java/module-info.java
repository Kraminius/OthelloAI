module com.othelloai.aiapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.web;
    requires java.sql;

    opens com.othelloai.aiapi to javafx.fxml, spring.core;
    opens com.othelloai.aiapi.repository to spring.beans;
    exports com.othelloai.aiapi;
}