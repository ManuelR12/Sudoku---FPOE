module com.example.proyecto2fpoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.proyecto2fpoe to javafx.fxml;
    opens com.example.proyecto2fpoe.Controller to javafx.fxml;
    exports com.example.proyecto2fpoe;
}