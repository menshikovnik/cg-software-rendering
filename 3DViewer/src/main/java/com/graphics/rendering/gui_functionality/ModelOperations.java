package com.graphics.rendering.gui_functionality;

import com.graphics.rendering.GuiController;
import com.graphics.rendering.model.Model;
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

    public ModelOperations(GuiController guiController) {
        this.guiController = guiController;
    }

    /**
     * @param selectedItem выбранный элемент
     */
    protected void saveAs(String selectedItem) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save model as");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        File file = fileChooser.showSaveDialog(guiController.getCanvas().getScene().getWindow());
        ObjectWriter.write(file.getAbsolutePath(), guiController.getMeshes().get(selectedItem));
    }

    public void saveInCurrentFile(MouseEvent event) {
        MenuItem save = new MenuItem();
        String menuItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
        save.textProperty().bind(Bindings.format("Save \"%s\"", menuItem.replaceAll("\\s.*", "")));

        save.setOnAction(saveEvent -> {
            String selectedItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
            selectedItem = selectedItem.replaceAll("\\s.*", "");
            String filePath = guiController.getMeshes().get(selectedItem).getFilePath();
            if (filePath != null) {
                ObjectWriter.write(filePath, guiController.getMeshes().get(selectedItem));
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
        String menuItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
        saveItemAs.textProperty().bind(Bindings.format("Save As.. \"%s\"", menuItem.replaceAll("\\s.*", "")));

        saveItemAs.setOnAction(saveAsEvent -> {
            String selectedItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
            selectedItem = selectedItem.replaceAll("\\s.*", "");
            saveAs(selectedItem);
            showErrorSaveAlertWindow(selectedItem);
        });
        if (!guiController.isLightMode()) {
            saveItemAs.setStyle("-fx-text-fill: white;");
        }

        guiController.getContextMenu().getItems().add(saveItemAs);
        guiController.getContextMenu().show(guiController.getModelNameView(), event.getScreenX(), event.getScreenY());
    }

    public void removeModelFromTheScene(MouseEvent event) {
        MenuItem deleteItem = new MenuItem();
        String menuItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", menuItem.replaceAll("\\s.*", "")));

        deleteItem.setOnAction(deleteEvent -> {
            String selectedItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
            String selectedItemWithoutParse = selectedItem;
            selectedItem = selectedItem.replaceAll("\\s.*", "");
            if (selectedItem.equals("PcMonitor")) {
                guiController.getMeshes().remove(selectedItem);
                guiController.getModelNameView().getItems().remove(selectedItemWithoutParse);
            } else {
                guiController.getMeshes().remove(selectedItem);
                guiController.getFileNames().remove(selectedItem);
                guiController.getModelNameView().getItems().remove(selectedItemWithoutParse);
            }
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
        String menuItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
        copy.textProperty().bind(Bindings.format("Copy \"%s\"", menuItem.replaceAll("\\s.*", "")));

        copy.setOnAction(copyEvent -> {
            String selectedItem = guiController.getModelNameView().getSelectionModel().getSelectedItem();
            selectedItem = selectedItem.replaceAll("\\s.*", "");

            Model model = new Model(guiController.getMeshes().get(selectedItem));
            String finalName = setName(selectedItem);
            model.setNameOfModel(finalName);
            guiController.getMeshes().put(finalName, model);
            setNameInListView(finalName);
        });

        if (!guiController.isLightMode()) {
            copy.setStyle("-fx-text-fill: white;");
        }

        guiController.getContextMenu().getItems().add(copy);
        guiController.getContextMenu().show(guiController.getModelNameView(), event.getScreenX(), event.getScreenY());
    }

    /**
     * Метод принимает на вход путь к файлу, с помощью регулярного выражение парсит и
     * мы получаем из name.obj имя вида: name
     * @param filePath Путь к файлу, который надо распарсить
     * @return Возвращает имя файла без расширения
     */
    private String parseNameOfModel(String filePath) {
        File file = new File(filePath);
        String fileNameWithExtension = file.getName(); // имя файла с расширением
        return fileNameWithExtension.replaceFirst("[.][^.]+$", ""); // имя файла без расширения
    }

    /**
     * Метод принимает на вход файл, затем он парсит имя файла и мы получаем имя вида:
     * model. Затем вызывается метод setName, чтобы присвоить имя модели вида: model.00n.
     *
     * @param file Файл модели
     * @throws IOException Обработка исключения - файл не найден
     */

    public void readFile(File file) throws IOException {
        Path fileName = Path.of(file.getAbsolutePath());
        String fileContent = Files.readString(fileName);
        String nameOfModel = parseNameOfModel(file.getAbsolutePath());


        String resultNameOfModel = setName(nameOfModel);
        Model mesh = ObjectReader.read(fileContent);

        guiController.getMeshes().put(resultNameOfModel, mesh);

        guiController.getMeshes().get(resultNameOfModel).setNameOfModelAndFilePath(resultNameOfModel,  file.getAbsolutePath());

        setNameInListView(mesh.getNameOfModel());
    }

    public void showErrorSaveAlertWindow(String selectedItem) {
        Stage mainStage = (Stage) guiController.getAnchorPane().getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error writing OBJ file: No such file exists, do you want to save the file somewhere else?", ButtonType.CANCEL, ButtonType.OK);
        alert.initOwner(mainStage);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                saveAs(selectedItem);
            }
        });
    }

    public void showSuccessfullySaveWindow(AnchorPane anchorPane) {
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

    private void setNameInListView(String item){
        guiController.getFileNames().add(item);
        guiController.getModelNameView().setItems(guiController.getFileNames());
    }

    private String setName(String selectedItem) {
        String item = selectedItem.replaceAll("[\\d.]", "");
        String result;
        for (int i = guiController.getFileNames().size() - 1;i >=0;i--){
            if (item.equals(guiController.getFileNames().get(i).replaceAll("[\\d.]", ""))){
                result = item + ".00" + (extractDigits(guiController.getFileNames().get(i)) + 1);
                return result;
            }
        }
        result = item + ".001";
        return result;
    }

    /**
     * Метод выделения числа из строки
     * @param input На вход идет строка
     * @return Возвращается число найденное в этой строке
     */
    private static int extractDigits(String input) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(c);
            }
        }

        return Integer.parseInt(result.toString());
    }
}
