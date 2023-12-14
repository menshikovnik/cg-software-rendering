package com.graphics.rendering.models_operations;

import com.graphics.rendering.GuiController;
import com.graphics.rendering.objreader.ObjectReader;
import com.graphics.rendering.objwriter.ObjectWriter;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModelOperations {

    GuiController guiController;
    public ModelOperations(GuiController guiController){
        this.guiController = guiController;
    }
    protected void saveAs(String selectedItem) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save model as");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        File file = fileChooser.showSaveDialog(guiController.getCanvas().getScene().getWindow());
        ObjectWriter.write(file.getAbsolutePath(), guiController.getMeshes().get(selectedItem));
    }

    public void saveInCurrentFile(MouseEvent event) {
        MenuItem save = new MenuItem();
        save.textProperty().bind(Bindings.format("Save \"%s\"", guiController.getModelNameView().getSelectionModel().selectedItemProperty()));

        save.setOnAction(saveEvent -> {
            String selectedItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
            if (guiController.getFilePaths().containsKey(selectedItem)) {
                ObjectWriter.write(guiController.getFilePaths().get(selectedItem), guiController.getMeshes().get(selectedItem));
                showSuccessfullySaveWindow(guiController.getAnchorPane());
            } else
                showErrorSaveAlertWindow(selectedItem);
        });

        if (!guiController.isLightMode()) {
            save.setStyle("-fx-text-fill: white;");
        }

        guiController.getContextMenu().getItems().add(save);
        guiController.getContextMenu().show(guiController.getModelNameView(), event.getScreenX(), event.getScreenY());
    }

    public void saveModelAs(MouseEvent event) {
        MenuItem saveItemAs = new MenuItem();
        saveItemAs.textProperty().bind(Bindings.format("Save As.. \"%s\"", guiController.getModelNameView().getSelectionModel().selectedItemProperty()));

        saveItemAs.setOnAction(saveAsEvent -> {
            String selectedItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
            saveAs(selectedItem);
        });
        if (!guiController.isLightMode()) {
            saveItemAs.setStyle("-fx-text-fill: white;");
        }

        guiController.getContextMenu().getItems().add(saveItemAs);
        guiController.getContextMenu().show(guiController.getModelNameView(), event.getScreenX(), event.getScreenY());
    }
    public void removeModelFromTheScene(MouseEvent event) {
        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", guiController.getModelNameView().getSelectionModel().selectedItemProperty()));

        deleteItem.setOnAction(deleteEvent -> {
            String selectedItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
            String parseItem = parseNameOfModel(selectedItem);
            guiController.getCountNameOfModels().put(parseItem, guiController.getCountNameOfModels().get(parseItem) - 1);
            guiController.getMeshes().remove(selectedItem);
            guiController.getFilePaths().remove(selectedItem);
            guiController.getFileNames().remove(selectedItem);
            guiController.getModelNameView().getItems().remove(selectedItem);
        });

        if (!guiController.isLightMode()) {
            deleteItem.setStyle("-fx-text-fill: white;");
        }

        guiController.getContextMenu().getItems().add(deleteItem);
        double yOffset = 10.5; //для смещения элемента контекстного меню вниз
        guiController.getContextMenu().show(guiController.getModelNameView(), event.getScreenX(), event.getScreenY() + yOffset);
    }

    public void copyModel(MouseEvent event) {
        MenuItem copy = new MenuItem();
        copy.textProperty().bind(Bindings.format("Copy \"%s\"", guiController.getModelNameView().getSelectionModel().selectedItemProperty()));

        copy.setOnAction(copyEvent -> {
            String selectedItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
            String item = parseNameOfModel(selectedItem);
            addInCountNameOfModels(item);
            guiController.getMeshes().put(setNameOfModel(item), guiController.getMeshes().get(selectedItem));
            guiController.getFileNames().add(setNameOfModel(item));
            guiController.getModelNameView().setItems(guiController.getFileNames());
        });

        if (!guiController.isLightMode()) {
            copy.setStyle("-fx-text-fill: white;");
        }

        guiController.getContextMenu().getItems().add(copy);
        guiController.getContextMenu().show(guiController.getModelNameView(), event.getScreenX(), event.getScreenY());
    }

    private String parseNameOfModel(String filePath) {
        File file = new File(filePath);

        // Получаем имя файла (с расширением)
        String fileNameWithExtension = file.getName();

        // Получаем имя файла без расширения
        return fileNameWithExtension.replaceFirst("[.][^.]+$", "");
    }

    private String setNameOfModel(String nameOfModel){
        return nameOfModel + ".00" + guiController.getCountNameOfModels().get(nameOfModel);
    }

    public void readFile(File file) throws IOException {
        Path fileName = Path.of(file.getAbsolutePath());
        String fileContent = Files.readString(fileName);
        String nameOfModel = parseNameOfModel(file.getAbsolutePath());

        addInCountNameOfModels(nameOfModel);

        guiController.getMeshes().put(setNameOfModel(nameOfModel), ObjectReader.read(fileContent));
        guiController.getFilePaths().put(setNameOfModel(nameOfModel), file.getAbsolutePath());
        guiController.getFileNames().add(setNameOfModel(nameOfModel));
        guiController.getModelNameView().setItems(guiController.getFileNames());
    }

    private void addInCountNameOfModels(String item){
        if (!guiController.getCountNameOfModels().containsKey(item)){
            guiController.getCountNameOfModels().put(item, 1);
        } else guiController.getCountNameOfModels().put(item, guiController.getCountNameOfModels().get(item) + 1);
    }

    public void showErrorSaveAlertWindow(String selectedItem){
        Stage mainStage = (Stage) guiController.getAnchorPane().getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.ERROR , "Error writing OBJ file: No such file exists, do you want to save the file somewhere else?", ButtonType.CANCEL, ButtonType.OK);
        alert.initOwner(mainStage);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                saveAs(selectedItem);
            }
        });
    }

    public void showSuccessfullySaveWindow(AnchorPane anchorPane){
        Stage mainStage = (Stage) anchorPane.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Model updated successfully!", ButtonType.OK);
        alert.initOwner(mainStage);
        alert.showAndWait();
    }

    public void showAlertWindow(AnchorPane anchorPane, Alert.AlertType alertType, String message, ButtonType buttonType) {
        Stage mainStage = (Stage) anchorPane.getScene().getWindow();
        Alert alert = new Alert(alertType, message, buttonType);
        alert.initOwner(mainStage);
        alert.showAndWait();
    }
}
