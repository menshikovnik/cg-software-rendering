package com.graphics.rendering;

import com.graphics.rendering.model.Model;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.vecmath.Vector3f;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class GuiController {

    final private float TRANSLATION = 0.4F;
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
            new Vector3f(0, 0, 35),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 80);

    @FXML
    private void initialize() {
        contextMenu = new ContextMenu();
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
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

        if (file == null) {
            return;
        } //todo сделать окно предупреждения

        this.tempFileName.add(file.getName());
        this.fileName.setItems(tempFileName);

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            meshes.put(file.getName(), ObjectReader.read(fileContent));
        } catch (IOException exception) {
            // todo: доделать окно ошибки
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
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward() {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft() {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight() {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void backToZeroCoordinates() {
        camera = new Camera(
                new Vector3f(0, 0, 35),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 80);
    }

    private void handleCameraUpAndDownOnScroll(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
            } else if (deltaY < 0) {
                camera.movePosition(new Vector3f(0, TRANSLATION, 0));
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

    public ListView<String> getFileName() {
        return fileName;
    }

    public void setFileName(ListView<String> fileName) {
        this.fileName = fileName;
    }
}
