package com.graphics.rendering;

import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import com.graphics.rendering.models_operations.ModelOperations;
import com.graphics.rendering.objreader.ObjReaderException;
import com.graphics.rendering.render_engine.Camera;
import com.graphics.rendering.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class GuiController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Menu fileMenu;

    @FXML
    private ImageView image;

    @FXML
    private ImageView buttonMoveImage;

    @FXML
    private ListView<String> modelNameView;

    @FXML
    private Button buttonMove;

    private final float TRANSLATION = 0.4F;

    private static final double FPS = 60;

    private HashMap<String, Model> meshes = new HashMap<>();

    private HashMap<String, String> filePaths = new HashMap<>();

    private HashMap<String, Integer> countNameOfModels = new HashMap<>();

    private ContextMenu contextMenu;

    private final ObservableList<String> fileNames = FXCollections.observableArrayList();
    ModelOperations modelOperations = new ModelOperations(this);
    private boolean isLightMode = false;
    private boolean isMoveModeEnabled = false;

    private final LinkedList<String> activeModels = new LinkedList<>();

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

        modelNameView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.getItems().clear();
                modelOperations.removeModelFromTheScene(event);
                modelOperations.saveModelAs(event);
                modelOperations.saveInCurrentFile(event);
                modelOperations.copyModel(event);
            } else if (event.getButton() == MouseButton.PRIMARY) {
                modelNameView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                if (!activeModels.contains(modelNameView.getSelectionModel().getSelectedItem())) {
                    activeModels.add(modelNameView.getSelectionModel().getSelectedItem());
                    modelNameView.getStyleClass().add("selected-item-now");
                    modelNameView.getStyleClass().remove("cancel-choose-selected-item");
                    System.out.println(modelNameView.getStyleClass());
                } else {
                    activeModels.remove(modelNameView.getSelectionModel().getSelectedItem());
                    modelNameView.getStyleClass().add("cancel-choose-selected-item");
                    modelNameView.getStyleClass().remove("selected-item-now");
                    System.out.println(modelNameView.getStyleClass());
                }
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
            modelOperations.showAlertWindow(anchorPane, Alert.AlertType.ERROR, "File is null", ButtonType.CLOSE);
        }

        try {

            modelOperations.readFile(file);

        } catch (IOException exception) {
            modelOperations.showAlertWindow(anchorPane, Alert.AlertType.ERROR, exception.getMessage(), ButtonType.CLOSE);
        } catch (ObjReaderException exception) {
            modelOperations.showAlertWindow(anchorPane, Alert.AlertType.WARNING, exception.getMessage(), ButtonType.CLOSE);
        }
    }

    @FXML
    private void clearScene() {
        meshes = new HashMap<>();
        modelNameView.setItems(null);
        fileNames.clear();
        filePaths = new HashMap<>();
        countNameOfModels = new HashMap<>();
    }

    @FXML
    private void handleCameraForward() {
        if (!isMoveModeEnabled) {
            camera.movePosition(new Vector3D(0, 0, -TRANSLATION));
        } else {
            for (String activeModel : activeModels) {
                Model.moveModelForward(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleCameraBackward() {
        if (!isMoveModeEnabled) {
            camera.movePosition(new Vector3D(0, 0, TRANSLATION));
        } else {
            for (String activeModel : activeModels) {
                Model.moveModelBackward(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleCameraLeft() {
        if (!isMoveModeEnabled) {
            camera.movePosition(new Vector3D(TRANSLATION, 0, 0));
        } else {
            for (String activeModel : activeModels) {
                Model.moveModelLeft(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleCameraRight() {
        if (!isMoveModeEnabled) {
            camera.movePosition(new Vector3D(-TRANSLATION, 0, 0));
        } else {
            for (String activeModel : activeModels) {
                Model.moveModelRight(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void backToZeroCoordinates() {
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

    public static boolean isFileNull(File file) {
        return file == null;
    }

    @FXML
    private void changeTheme() {
        isLightMode = !isLightMode;

        if (isLightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    @FXML
    private void toggleMode() {
        isMoveModeEnabled = !isMoveModeEnabled;

        if (isMoveModeEnabled) {
            buttonMove.getStyleClass().remove("button-move");
            buttonMove.getStyleClass().add("button-move-pressed");
        } else {
            buttonMove.getStyleClass().remove("button-move-pressed");
            buttonMove.getStyleClass().add("button-move");
        }
    }


    private void setLightMode() {
        anchorPane.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("/styles/darkMode.css")).toString());
        anchorPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/lightMode.css")).toString());
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-moon-96.png")).toString());
        Image imageMove = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-move-50.png")).toString());
        this.image.setImage(image);
        this.buttonMoveImage.setImage(imageMove);
        fileMenu.getStyleClass().clear();
        fileMenu.getStyleClass().add("menu");
    }

    private void setDarkMode() {
        anchorPane.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("/styles/lightMode.css")).toString());
        anchorPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/darkMode.css")).toString());
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-sun-90.png")).toString());
        Image imageMove = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-move-white-50.png")).toString());
        this.image.setImage(image);
        this.buttonMoveImage.setImage(imageMove);
        fileMenu.getStyleClass().clear();
        fileMenu.getStyleClass().add("menu");
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public HashMap<String, Model> getMeshes() {
        return meshes;
    }

    @FXML
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public ListView<String> getModelNameView() {
        return modelNameView;
    }

    public HashMap<String, String> getFilePaths() {
        return filePaths;
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public ObservableList<String> getFileNames() {
        return fileNames;
    }

    public boolean isLightMode() {
        return isLightMode;
    }

    public HashMap<String, Integer> getCountNameOfModels() {
        return countNameOfModels;
    }
}
