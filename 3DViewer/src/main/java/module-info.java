module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;


    opens com.graphics.rendering to javafx.fxml;
    exports com.graphics.rendering;
}