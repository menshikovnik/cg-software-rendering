package com.graphics.rendering;

import com.graphics.rendering.model.Model;
import com.graphics.rendering.objreader.ObjectReader;
import com.graphics.rendering.render_engine.Camera;
import com.graphics.rendering.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.vecmath.Vector3f;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class GuiController {

    final private float TRANSLATION = 0.75F;
    private Vector3f vector3f = new Vector3f(0, 0, 35);
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private List<Model> meshes = new LinkedList<>();

    private final Camera camera = new Camera(
            vector3f,
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 80);

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (meshes != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, meshes, (int) width, (int) height);
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

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            meshes.add(ObjectReader.read(fileContent));
        } catch (IOException exception) {
            // todo: доделать окно ошибки
        }
    }

    @FXML
    public void clearScene() {
        meshes = new LinkedList<>();
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
    public void handleCameraUp() {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown() {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }
}