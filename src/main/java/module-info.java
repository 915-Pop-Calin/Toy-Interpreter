module com.example.assignment_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires pcollections;


    opens com.example.assignment_gui to javafx.fxml;
    exports com.example.assignment_gui;
}