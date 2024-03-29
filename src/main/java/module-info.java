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
    requires java.desktop;
    requires com.google.gson;

    opens com.othelloai.aiapi to javafx.fxml, spring.core;
    opens com.othelloai.aiapi.repository to spring.beans;
    opens com.othelloai.aiapi.controller to spring.beans, spring.context, spring.web;
    opens com.othelloai.aiapi.model to com.google.gson;

    exports com.othelloai.aiapi;
}
