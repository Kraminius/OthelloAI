module com.othelloai.aiapi {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.othelloai.aiapi to javafx.fxml;
    exports com.othelloai.aiapi;
}