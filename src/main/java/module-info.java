module com.othelloai.aiapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot.autoconfigure;
    requires spring.boot;


    opens com.othelloai.aiapi to javafx.fxml;
    exports com.othelloai.aiapi;
}