package com.graphics.rendering;

import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import com.graphics.rendering.objreader.ObjReaderException;
import com.graphics.rendering.objreader.ObjectReader;
import com.graphics.rendering.render_engine.Camera;
import com.graphics.rendering.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class GuiController {

    final private float TRANSLATION = 0.4F;
    private static final double FPS = 60;
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private HashMap<String, Model> meshes = new HashMap<>();
    private ContextMenu contextMenu;


    @FXML
    private ListView<String> fileName;
    private final ObservableList<String> tempFileName = FXCollections.observableArrayList();

    private Camera camera = new Camera(
            new Vector3D(0, 0, 35),
            new Vector3D(0, 0, 0),
            1.0F, 1, 0.01F, 80);

    @FXML
    private void initialize() {
        contextMenu = new ContextMenu();
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(1000 / FPS), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));
            handleCameraUpAndDownOnScroll(canvas);

            if (meshes != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, meshes, (int) width, (int) height);
            }
        });

        fileName.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.getItems().clear();
                removeModelFromTheScene(event);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        if (isFileNull(file)) {
            showAlertWindow(Alert.AlertType.ERROR, "File is null", ButtonType.CLOSE);
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            meshes.put(file.getName(), ObjectReader.read(fileContent));
            this.tempFileName.add(file.getName());
            this.fileName.setItems(tempFileName);
        } catch (IOException exception) {
            showAlertWindow(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.CLOSE);
        } catch (ObjReaderException exception) {
            showAlertWindow(Alert.AlertType.WARNING, exception.getMessage(), ButtonType.CLOSE);
        }
    }

    @FXML
    public void clearScene() {
        meshes = new HashMap<>();
        fileName.setItems(null);
        tempFileName.clear();
    }

    @FXML
    public void chooseModelOnClick() {
        //todo сделать выбор модели по клику
    }

    @FXML
    public void handleCameraForward() {
        camera.movePosition(new Vector3D(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward() {
        camera.movePosition(new Vector3D(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft() {
        camera.movePosition(new Vector3D(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight() {
        camera.movePosition(new Vector3D(-TRANSLATION, 0, 0));
    }

    @FXML
    public void backToZeroCoordinates() {
        camera = new Camera(
                new Vector3D(0, 0, 35),
                new Vector3D(0, 0, 0),
                1.0F, 1, 0.01F, 80);
    }

    private void handleCameraUpAndDownOnScroll(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                camera.movePosition(new Vector3D(0, -TRANSLATION, 0));
            } else if (deltaY < 0) {
                camera.movePosition(new Vector3D(0, TRANSLATION, 0));
            }
        });
    }

    private void removeModelFromTheScene(MouseEvent event) {
        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", fileName.getSelectionModel().selectedItemProperty()));

        deleteItem.setOnAction(deleteEvent -> {
            String selectedItem = fileName.getSelectionModel().getSelectedItem();
            fileName.getItems().remove(selectedItem);
            meshes.remove(selectedItem);
        });

        contextMenu.getItems().add(deleteItem);
        double yOffset = 10.5; //для смещения элемента контекстного меню вниз
        contextMenu.show(fileName, event.getScreenX(), event.getScreenY() + yOffset);
    }

    public static void showAlertWindow(Alert.AlertType alertType, String message, ButtonType buttonType){
        Alert alert = new Alert(alertType, message, buttonType);
        alert.showAndWait();
    }

    public static boolean isFileNull(File file){
        return file == null;
    }

    public ListView<String> getFileName() {
        return fileName;
    }

    public void setFileName(ListView<String> fileName) {
        this.fileName = fileName;
    }
}
