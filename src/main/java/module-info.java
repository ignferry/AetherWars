module com.aetherwars.aetherwars {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.aetherwars.aetherwars to javafx.fxml;
    exports com.aetherwars.aetherwars;
}