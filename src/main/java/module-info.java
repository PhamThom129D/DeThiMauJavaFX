module com.example.dethimau {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dethimau to javafx.fxml;
    exports com.example.dethimau;
}