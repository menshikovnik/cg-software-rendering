module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;
    requires org.junit.jupiter.api;


    opens com.graphics.rendering to javafx.fxml;
    exports com.graphics.rendering;
}