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

        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        java.awt.Image image = defaultToolkit.getImage(getClass().getResource("/photo/icons8-mesh-96.png"));
        final Taskbar taskbar = Taskbar.getTaskbar();

        taskbar.setIconImage(image);

        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle("3DViewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}