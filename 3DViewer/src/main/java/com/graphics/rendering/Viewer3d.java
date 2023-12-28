package com.graphics.rendering;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Viewer3d extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml")));
        Scene scene = new Scene(viewport);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setMinWidth(bounds.getWidth());
        stage.setMinHeight(bounds.getHeight());

        switch (Objects.requireNonNull(getOperatingSystem())) {
            case WINDOWS:
                javafx.scene.image.Image windowsImage = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-mesh-96.png")).toString());
                stage.getIcons().add(windowsImage);
                break;
            case MAC:
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                java.awt.Image image = defaultToolkit.getImage(getClass().getResource("/photo/icons8-mesh-96.png"));
                final Taskbar taskbar = Taskbar.getTaskbar();

                taskbar.setIconImage(image);
                break;
        }

        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle("3DViewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static OS getOperatingSystem() {
        // detecting the operating system using `os.name` System property
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return OS.WINDOWS;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return OS.LINUX;
        } else if (os.contains("mac")) {
            return OS.MAC;
        }

        return null;
    }
}

enum OS {
    WINDOWS, LINUX, MAC, SOLARIS
}