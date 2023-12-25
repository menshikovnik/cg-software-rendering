package com.graphics.rendering;

import com.graphics.rendering.gui_functionality.ModelOperations;
import com.graphics.rendering.gui_functionality.Style;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
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
    private ImageView buttonScaleImage;

    @FXML
    private ImageView buttonRotateImage;
    @FXML
    private ListView<String> modelNameView;

    @FXML
    private Button buttonMove;
    @FXML
    private Button buttonRotate;
    @FXML
    private Button buttonScale;

    private final float TRANSLATION = 0.3F;

    private static final double FPS = 60;

    private HashMap<String, Model> meshes = new HashMap<>();

    private ContextMenu contextMenu;

    private final ObservableList<String> fileNames = FXCollections.observableArrayList();
    private final ModelOperations modelOperations = new ModelOperations(this);
    private final Style style = new Style(this);
    private boolean isLightMode = false;
    private boolean isMoveModeEnabled = false;
    private boolean isRotateModeEnabled = false;
    private boolean isScalingModeEnabled = false;
    private int ANGLEForXZ = 45;
    private int ANGLEForY = 45;
    private float RADIUS = 13;
    private double mouseX, mouseY;

    private LinkedList<String> activeModels = new LinkedList<>();
    private Camera camera = new Camera(
            new Vector3D((float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY))),
                    (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY))),
                    (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)))),
            new Vector3D(0, 0, 0),
            1.0F, 1, 0.01F, 80);

    @FXML
    private void initialize() {
        style.setDefaultScene();
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
            handleCameraRotationOnMouseDrag(canvas);

            if (meshes != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, meshes, (int) width, (int) height);
            }
        });

        modelNameView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                modelOperations.contextMenu(event);
            } else if (event.getButton() == MouseButton.PRIMARY) {
                String selectedItem = modelNameView.getSelectionModel().getSelectedItem();
                String resultItem = selectedItem.replaceAll("\\s.*", "");
                style.activeMode(resultItem);
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
        activeModels = new LinkedList<>();
    }

    @FXML
    private void handleActionForward() {
        if (!isMoveModeEnabled && !isRotateModeEnabled && !isScalingModeEnabled) {
            RADIUS += TRANSLATION;
            float newX = (float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getX());
            float newY = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY)) - camera.getPosition().getY());
            float newZ = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getZ());

            camera.movePosition(new Vector3D(newX, newY, newZ));
        } else if (isRotateModeEnabled) {
            for (String activeModel : activeModels) {
                Model.rotateModelOnZClockwise(meshes.get(activeModel));
            }
        } else if (isMoveModeEnabled) {
            for (String activeModel : activeModels) {
                Model.moveModelForward(meshes.get(activeModel));
            }
        } else {
            for (String activeModel : activeModels) {
                Model.scaleModelOnXForward(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleActionBackward() {
        if (!isMoveModeEnabled && !isRotateModeEnabled && !isScalingModeEnabled) {
            RADIUS -= TRANSLATION;
            float newX = (float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getX());
            float newY = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY)) - camera.getPosition().getY());
            float newZ = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getZ());

            camera.movePosition(new Vector3D(newX, newY, newZ));
        } else if (isRotateModeEnabled) {
            for (String activeModel : activeModels) {
                Model.rotateModelOnZNotClockwise(meshes.get(activeModel));
            }
        } else if (isMoveModeEnabled) {
            for (String activeModel : activeModels) {
                Model.moveModelBackward(meshes.get(activeModel));
            }
        } else {
            for (String activeModel : activeModels) {
                Model.scaleModelOnXBackward(meshes.get(activeModel));
            }
        }
    }

    private void handleCameraRotationOnMouseDrag(Node node) {
        node.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        node.setOnMouseDragged(event -> {
            double deltaX = (event.getSceneX() - mouseX);
            double deltaY = (event.getSceneY() - mouseY);

            ANGLEForXZ += deltaX % 360;
            ANGLEForY += deltaY % 360;

            float newX = (float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getX());
            float newY = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY)) - camera.getPosition().getY());
            float newZ = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getZ());

            camera.movePosition(new Vector3D(newX, newY, newZ));

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
    }


    @FXML
    private void handleActionLeft() {
        if (!isMoveModeEnabled && !isRotateModeEnabled && !isScalingModeEnabled) {
            if (ANGLEForXZ < -360) {
                ANGLEForXZ = 0;
            }
            ANGLEForXZ -= 1;

            float newX = (float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getX());
            float newY = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY)) - camera.getPosition().getY());
            float newZ = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getZ());

            camera.movePosition(new Vector3D(newX, newY, newZ));

        } else if (isRotateModeEnabled) {
            for (String activeModel : activeModels) {
                Model.rotateModelOnXNotClockwise(meshes.get(activeModel));
            }
        } else if (isMoveModeEnabled) {
            for (String activeModel : activeModels) {
                Model.moveModelLeft(meshes.get(activeModel));
            }
        } else {
            for (String activeModel : activeModels) {
                Model.scaleModelOnZLeft(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleActionRight() {
        if (!isMoveModeEnabled && !isRotateModeEnabled && !isScalingModeEnabled) {
            if (ANGLEForXZ > 360) {
                ANGLEForXZ = 0;
            }
            ANGLEForXZ += 1;

            float newX = (float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getX());
            float newY = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY)) - camera.getPosition().getY());
            float newZ = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getZ());

            camera.movePosition(new Vector3D(newX, newY, newZ));

        } else if (isRotateModeEnabled) {
            for (String activeModel : activeModels) {
                Model.rotateModelOnXClockwise(meshes.get(activeModel));
            }
        } else if (isMoveModeEnabled) {
            for (String activeModel : activeModels) {
                Model.moveModelRight(meshes.get(activeModel));
            }
        } else {
            for (String activeModel : activeModels) {
                Model.scaleModelOnZRight(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleActionOnYUp() {
        if (isMoveModeEnabled) {
            for (String activeModel : activeModels) {
                Model.moveModelOnYUp(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleActionOnYDown() {
        if (isMoveModeEnabled) {
            for (String activeModel : activeModels) {
                Model.moveModelOnYDown(meshes.get(activeModel));
            }
        } else if (isRotateModeEnabled) {
            for (String activeModel : activeModels) {
                Model.rotateModelOnYDown(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleRotateRight() {
        if (isRotateModeEnabled) {
            for (String activeModel : activeModels) {
                Model.rotateModelOnYUp(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleRotateLeft() {
        if (isRotateModeEnabled) {
            for (String activeModel : activeModels) {
                Model.rotateModelOnYDown(meshes.get(activeModel));
            }
        }
    }

    private void handleCameraUpAndDownOnScroll(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                RADIUS += TRANSLATION;
                float newX = (float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getX());
                float newY = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY)) - camera.getPosition().getY());
                float newZ = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getZ());

                camera.movePosition(new Vector3D(newX, newY, newZ));
            } else if (deltaY < 0) {
                RADIUS -= TRANSLATION;
                float newX = (float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getX());
                float newY = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY)) - camera.getPosition().getY());
                float newZ = (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)) - camera.getPosition().getZ());

                camera.movePosition(new Vector3D(newX, newY, newZ));
            }
        });
    }

    @FXML
    private void backToZeroCoordinates() {
        ANGLEForXZ = 45;
        ANGLEForY = 45;
        RADIUS = 10;
        camera = new Camera(
                new Vector3D((float) (RADIUS * Math.cos(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY))),
                        (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForY))),
                        (float) (RADIUS * Math.sin(Math.toRadians(ANGLEForXZ)) * Math.cos(Math.toRadians(ANGLEForY)))),
                new Vector3D(0, 0, 0),
                1.0F, 1, 0.01F, 80);
    }

    @FXML
    private void handleScaleUp(){
        if (isScalingModeEnabled){
            for (String activeModel : activeModels) {
                Model.scaleModelOnYUp(meshes.get(activeModel));
            }
        }
    }

    @FXML
    private void handleScaleDown(){
        if (isScalingModeEnabled){
            for (String activeModel : activeModels) {
                Model.scaleModelOnYDown(meshes.get(activeModel));
            }
        }
    }
    public static boolean isFileNull(File file) {
        return file == null;
    }

    @FXML
    private void changeTheme() {
        isLightMode = !isLightMode;

        if (isLightMode) {
            style.setLightMode();
        } else {
            style.setDarkMode();
        }
    }

    @FXML
    private void toggleMoveMode() {
        isMoveModeEnabled = !isMoveModeEnabled;
        style.setMoveButtonStyle();
    }

    @FXML
    private void toggleRotateMode() {
        isRotateModeEnabled = !isRotateModeEnabled;
        style.setRotateButtonStyle();
    }

    @FXML
    private void toggleScalingMode() {
        isScalingModeEnabled = !isScalingModeEnabled;
        style.setScalingButtonStyle();
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

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public ObservableList<String> getFileNames() {
        return fileNames;
    }

    public boolean isLightMode() {
        return isLightMode;
    }

    public LinkedList<String> getActiveModels() {
        return activeModels;
    }

    public ImageView getImage() {
        return image;
    }

    public ImageView getButtonMoveImage() {
        return buttonMoveImage;
    }

    public ImageView getButtonRotateImage() {
        return buttonRotateImage;
    }

    public Button getButtonMove() {
        return buttonMove;
    }

    public Button getButtonRotate() {
        return buttonRotate;
    }

    public Menu getFileMenu() {
        return fileMenu;
    }

    public boolean isMoveModeEnabled() {
        return isMoveModeEnabled;
    }

    public boolean isRotateModeEnabled() {
        return isRotateModeEnabled;
    }

    public ImageView getButtonScaleImage() {
        return buttonScaleImage;
    }

    public boolean isScalingModeEnabled() {
        return isScalingModeEnabled;
    }

    public Button getButtonScale() {
        return buttonScale;
    }


}
