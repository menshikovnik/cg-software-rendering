package com.graphics.rendering;

import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import com.graphics.rendering.objreader.ObjReaderException;
import com.graphics.rendering.objreader.ObjectReader;
import com.graphics.rendering.objwriter.ObjectWriter;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
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
    private ListView<String> modelNameView;

    private final float TRANSLATION = 0.4F;

    private static final double FPS = 60;

    private HashMap<String, Model> meshes = new HashMap<>();

    private HashMap<String, String> filePaths = new HashMap<>();

    private HashMap<String, Integer> countNameOfModels = new HashMap<>();

    private ContextMenu contextMenu;

    private final ObservableList<String> fileNames = FXCollections.observableArrayList();

    private boolean isLightMode = false;

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
                removeModelFromTheScene(event);
                saveModelAs(event);
                saveInCurrentFile(event);
                copyModel(event);
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

        try {

            readFile(file);

        } catch (IOException exception) {
            showAlertWindow(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.CLOSE);
        } catch (ObjReaderException exception) {
            showAlertWindow(Alert.AlertType.WARNING, exception.getMessage(), ButtonType.CLOSE);
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
    private void chooseModelOnClick() {
        //todo сделать выбор модели по клику
    }

    @FXML
    private void handleCameraForward() {
        camera.movePosition(new Vector3D(0, 0, -TRANSLATION));
    }

    @FXML
    private void handleCameraBackward() {
        camera.movePosition(new Vector3D(0, 0, TRANSLATION));
    }

    @FXML
    private void handleCameraLeft() {
        camera.movePosition(new Vector3D(TRANSLATION, 0, 0));
    }

    @FXML
    private void handleCameraRight() {
        camera.movePosition(new Vector3D(-TRANSLATION, 0, 0));
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

    private void removeModelFromTheScene(MouseEvent event) {
        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", modelNameView.getSelectionModel().selectedItemProperty()));

        deleteItem.setOnAction(deleteEvent -> {
            String selectedItem = modelNameView.getSelectionModel().getSelectedItem();
            String parseItem = parseNameOfModel(selectedItem);
            countNameOfModels.put(parseItem, countNameOfModels.get(parseItem) - 1);
            meshes.remove(selectedItem);
            filePaths.remove(selectedItem);
            fileNames.remove(selectedItem);
            modelNameView.getItems().remove(selectedItem);
        });

        if (!isLightMode) {
            deleteItem.setStyle("-fx-text-fill: white;");
        }

        contextMenu.getItems().add(deleteItem);
        double yOffset = 10.5; //для смещения элемента контекстного меню вниз
        contextMenu.show(modelNameView, event.getScreenX(), event.getScreenY() + yOffset);
    }

    private void saveModelAs(MouseEvent event) {
        MenuItem saveItemAs = new MenuItem();
        saveItemAs.textProperty().bind(Bindings.format("Save As.. \"%s\"", modelNameView.getSelectionModel().selectedItemProperty()));

        saveItemAs.setOnAction(saveAsEvent -> {
            String selectedItem = modelNameView.getSelectionModel().getSelectedItem();
            saveAs(selectedItem);
        });
        if (!isLightMode) {
            saveItemAs.setStyle("-fx-text-fill: white;");
        }

        contextMenu.getItems().add(saveItemAs);
        contextMenu.show(modelNameView, event.getScreenX(), event.getScreenY());
    }

    private void saveInCurrentFile(MouseEvent event) {
        MenuItem save = new MenuItem();
        save.textProperty().bind(Bindings.format("Save \"%s\"", modelNameView.getSelectionModel().selectedItemProperty()));

        save.setOnAction(saveEvent -> {
            String selectedItem = modelNameView.getSelectionModel().getSelectedItem();
            if (filePaths.containsKey(selectedItem)) {
                ObjectWriter.write(filePaths.get(selectedItem), meshes.get(selectedItem));
                showAlertWindow(Alert.AlertType.INFORMATION, "Model updated successfully!", ButtonType.OK);
            } else
                showAlertWindow(Alert.AlertType.ERROR, "Error writing OBJ file: No such file exists, do you want to save the file somewhere else?", ButtonType.CANCEL, ButtonType.OK, selectedItem);
        });

        if (!isLightMode) {
            save.setStyle("-fx-text-fill: white;");
        }

        contextMenu.getItems().add(save);
        contextMenu.show(modelNameView, event.getScreenX(), event.getScreenY());
    }

    private void copyModel(MouseEvent event) {
        MenuItem copy = new MenuItem();
        copy.textProperty().bind(Bindings.format("Copy \"%s\"", modelNameView.getSelectionModel().selectedItemProperty()));

        copy.setOnAction(copyEvent -> {
            String selectedItem = modelNameView.getSelectionModel().getSelectedItem();
            String item = parseNameOfModel(selectedItem);
            addInCountNameOfModels(item);
            meshes.put(setNameOfModel(item), meshes.get(selectedItem));
            fileNames.add(setNameOfModel(item));
            modelNameView.setItems(fileNames);
        });

        if (!isLightMode) {
            copy.setStyle("-fx-text-fill: white;");
        }

        contextMenu.getItems().add(copy);
        contextMenu.show(modelNameView, event.getScreenX(), event.getScreenY());
    }

    private void showAlertWindow(Alert.AlertType alertType, String message, ButtonType buttonType) {
        Stage mainStage = (Stage) anchorPane.getScene().getWindow();
        Alert alert = new Alert(alertType, message, buttonType);
        alert.initOwner(mainStage);
        alert.showAndWait();
    }

    private void showAlertWindow(Alert.AlertType alertType, String message, ButtonType buttonType, ButtonType buttonType1, String selectedItem) {
        Stage mainStage = (Stage) anchorPane.getScene().getWindow();
        Alert alert = new Alert(alertType, message, buttonType, buttonType1);
        alert.initOwner(mainStage);
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonType1) {
                saveAs(selectedItem);
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

    private void setLightMode() {
        anchorPane.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("/styles/darkMode.css")).toString());
        anchorPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/lightMode.css")).toString());
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-moon-96.png")).toString());
        this.image.setImage(image);
        fileMenu.getStyleClass().clear();
        fileMenu.getStyleClass().add("menu");
    }

    private void setDarkMode() {
        anchorPane.getStylesheets().remove(Objects.requireNonNull(getClass().getResource("/styles/lightMode.css")).toString());
        anchorPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/darkMode.css")).toString());
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-sun-90.png")).toString());
        this.image.setImage(image);
        fileMenu.getStyleClass().clear();
        fileMenu.getStyleClass().add("menu");
    }

    private void saveAs(String selectedItem) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save model as");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        ObjectWriter.write(file.getAbsolutePath(), meshes.get(selectedItem));
    }

    private String parseNameOfModel(String filePath) {
        File file = new File(filePath);

        // Получаем имя файла (с расширением)
        String fileNameWithExtension = file.getName();

        // Получаем имя файла без расширения
        return fileNameWithExtension.replaceFirst("[.][^.]+$", "");
    }

    private String setNameOfModel(String nameOfModel){
        return nameOfModel + ".00" + countNameOfModels.get(nameOfModel);
    }

    private void readFile(File file) throws IOException {
        Path fileName = Path.of(file.getAbsolutePath());
        String fileContent = Files.readString(fileName);
        String nameOfModel = parseNameOfModel(file.getAbsolutePath());

        addInCountNameOfModels(nameOfModel);

        if (!meshes.containsKey(nameOfModel)) {
            meshes.put(setNameOfModel(nameOfModel), ObjectReader.read(fileContent));
            filePaths.put(setNameOfModel(nameOfModel), file.getAbsolutePath());
            this.fileNames.add(setNameOfModel(nameOfModel));
            this.modelNameView.setItems(fileNames);
        } else {
            meshes.put(setNameOfModel(nameOfModel), ObjectReader.read(fileContent));
            filePaths.put(setNameOfModel(nameOfModel), file.getAbsolutePath());
            this.fileNames.add(setNameOfModel(nameOfModel));
            this.modelNameView.setItems(fileNames);
        }
    }

    private void addInCountNameOfModels(String item){
        if (!countNameOfModels.containsKey(item)){
            countNameOfModels.put(item, 1);
        } else countNameOfModels.put(item, countNameOfModels.get(item) + 1);
    }
}
