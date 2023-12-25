package com.graphics.rendering.gui_functionality;

import com.graphics.rendering.GuiController;
import com.graphics.rendering.objreader.ObjReaderException;
import com.graphics.rendering.objreader.ObjectReader;
import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Style {
    GuiController guiController;

    public Style(GuiController guiController) {
        this.guiController = guiController;
    }

    public void setDarkMode() {
        guiController.getAnchorPane().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("/styles/lightMode.css")).toString());
        guiController.getAnchorPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/darkMode.css")).toString());
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-sun-90.png")).toString());
        Image imageMove = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-move-white-50.png")).toString());
        Image imageRotate = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-3d-rotate-white-50.png")).toString());
        Image imageScale = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-stretch-tool-white-96.png")).toString());
        guiController.getImage().setImage(image);
        guiController.getButtonMoveImage().setImage(imageMove);
        guiController.getButtonRotateImage().setImage(imageRotate);
        guiController.getButtonScaleImage().setImage(imageScale);
        guiController.getFileMenu().getStyleClass().clear();
        guiController.getFileMenu().getStyleClass().add("menu");
    }

    public void setLightMode() {
        guiController.getAnchorPane().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("/styles/darkMode.css")).toString());
        guiController.getAnchorPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/lightMode.css")).toString());
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-moon-96.png")).toString());
        Image imageMove = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-move-50.png")).toString());
        Image imageRotate = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-3d-rotate-50.png")).toString());
        Image imageScale = new Image(Objects.requireNonNull(getClass().getResource("/photo/icons8-stretch-tool-96.png")).toString());
        guiController.getImage().setImage(image);
        guiController.getButtonMoveImage().setImage(imageMove);
        guiController.getButtonRotateImage().setImage(imageRotate);
        guiController.getButtonScaleImage().setImage(imageScale);
        guiController.getFileMenu().getStyleClass().clear();
        guiController.getFileMenu().getStyleClass().add("menu");
    }

    public void setDefaultScene() {
        try {
            for (int i = 1; i <= 2; i++) {
                Path fileName = Path.of("3DViewer/src/main/resources/default_model/" + i + ".obj");
                String fileContent = Files.readString(fileName);
                if (i == 2) {
                    guiController.getMeshes().put("PcMonitor.001", ObjectReader.read(fileContent));
                    guiController.getFileNames().add("PcMonitor.001");
                    guiController.getModelNameView().setItems(guiController.getFileNames());
                } else {
                    guiController.getMeshes().put("" + i, ObjectReader.read(fileContent));
                }
            }
        } catch (IOException e) {
            throw new ObjReaderException("Error", -1);
        }
    }

    public void activeMode(String resultItem) {
        if (!guiController.getActiveModels().contains(resultItem)) {
            guiController.getActiveModels().add(resultItem);
            int index = guiController.getModelNameView().getSelectionModel().getSelectedIndex();
            String selectedItemActive = resultItem + "    ACTIVE NOW";
            guiController.getModelNameView().getItems().remove(guiController.getModelNameView().getSelectionModel().getSelectedItem());
            guiController.getModelNameView().getItems().add(index, selectedItemActive);
        } else {
            int index = guiController.getModelNameView().getSelectionModel().getSelectedIndex();
            guiController.getActiveModels().remove(resultItem);
            guiController.getModelNameView().getItems().remove(guiController.getModelNameView().getSelectionModel().getSelectedItem());
            guiController.getModelNameView().getItems().add(index, resultItem);
        }
    }

    public void setRotateButtonStyle() {
        if (guiController.isRotateModeEnabled()) {
            guiController.getButtonRotate().getStyleClass().remove("button-move");
            guiController.getButtonRotate().getStyleClass().add("button-move-pressed");
        } else {
            guiController.getButtonRotate().getStyleClass().remove("button-move-pressed");
            guiController.getButtonRotate().getStyleClass().add("button-move");
        }
    }

    public void setMoveButtonStyle() {
        if (guiController.isMoveModeEnabled()) {
            guiController.getButtonMove().getStyleClass().remove("button-move");
            guiController.getButtonMove().getStyleClass().add("button-move-pressed");
        } else {
            guiController.getButtonMove().getStyleClass().remove("button-move-pressed");
            guiController.getButtonMove().getStyleClass().add("button-move");
        }
    }

    public void setScalingButtonStyle(){
        if (guiController.isScalingModeEnabled()) {
            guiController.getButtonScale().getStyleClass().remove("button-move");
            guiController.getButtonScale().getStyleClass().add("button-move-pressed");
    } else {
            guiController.getButtonScale().getStyleClass().remove("button-move-pressed");
            guiController.getButtonScale().getStyleClass().add("button-move");
        }
    }
}
