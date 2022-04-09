module com.aetherwars.aetherwars {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.aetherwars to javafx.fxml;
    exports com.aetherwars;
}