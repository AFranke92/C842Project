package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Part;
import model.Inventory;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;
import static model.Inventory.lookUpPart;

/**
 * FXML controller class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class ModifyProduct implements Initializable {

    Stage stage;
    Parent scene;
    Product productToModify;
    int selectedIndex;

    @FXML
    private TableColumn<Part, Double> modifyProductAddCostCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductAddInvCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductAddPartIdCol;

    @FXML
    private TableColumn<Part, String> modifyProductAddPartNameCol;

    @FXML
    private TableView<Part> modifyProductAddTableView;

    @FXML
    private TextField modifyProductCostTxt;

    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TableColumn<?, ?> modifyProductRemoveCostCol;

    @FXML
    private TableColumn<?, ?> modifyProductRemoveInvCol;

    @FXML
    private TableColumn<?, ?> modifyProductRemovePartIdCol;

    @FXML
    private TableColumn<?, ?> modifyProductRemovePartNameCol;

    @FXML
    private TableView<Part> modifyProductRemoveTableView;

    @FXML
    private TextField modifyProductSearchTxt;

    /**
     * Adds selected associated part to a product
     * @param event add button selection
     */
    @FXML
    void onActionModifyProductAdd(ActionEvent event) {

        Part sPart = modifyProductAddTableView.getSelectionModel().getSelectedItem();
        productToModify.setAssociatedParts(sPart);
    }

    /**
     * Confirms and cancels changes made to product being modified
     * @param event cancel button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionModifyProductCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Confirms and removes selected associated part from product
     * @param event remove associated part button selection
     */
    @FXML
    void onActionModifyProductRemove(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part. Do you wish to continue?");
        alert.setTitle("Confirm Delete");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part sPart = modifyProductRemoveTableView.getSelectionModel().getSelectedItem();

            if (productToModify.deleteAssociatedPart(selectedPart(sPart.getId()))) {
                productToModify.getAssociatedParts().remove(sPart);
            }
        }
    }

    /**
     * Verifies all input values are present. If the values are correct, it saves the part.
     * If values are missing, an error message populates.
     *
     * Logical Error: No matter what Error I tried to force the system to throw it would
     * only throw the Warning. It would skip all Errors and just run the catch statement.
     * I fixed this by embedding the Errors inside if statements before product creation.
     *
     * @param event the event to save a part
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionModifyProductSave(ActionEvent event) throws IOException {

    try {

        productToModify.setName(modifyProductNameTxt.getText());
        productToModify.setStock(Integer.parseInt(modifyProductInvTxt.getText()));
        productToModify.setMin(Integer.parseInt(modifyProductMinTxt.getText()));
        productToModify.setMax(Integer.parseInt(modifyProductMaxTxt.getText()));
        productToModify.setPrice(Double.parseDouble(modifyProductCostTxt.getText()));

        if (productToModify.getMin() > productToModify.getMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum value cannot be greater than maximum value.");
            alert.showAndWait();
        }
        else if (productToModify.getStock() > productToModify.getMax() || productToModify.getStock() < productToModify.getMin()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between minimum and maximum values.");
            alert.showAndWait();
        }
        else if (modifyProductNameTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product must have a name.");
            alert.showAndWait();
        }
        else if (modifyProductInvTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory field must not be empty.");
            alert.showAndWait();
        }
        else if (modifyProductMaxTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product must have a maximum inventory value.");
            alert.showAndWait();
        }
        else if (modifyProductMinTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product must have a minimum inventory value.");
            alert.showAndWait();
        }
        else if (modifyProductCostTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product must have a price value.");
            alert.showAndWait();
        }
        else if (!modifyProductNameTxt.getText().isEmpty() && !modifyProductInvTxt.getText().isEmpty() && !modifyProductMinTxt.getText().isEmpty() && !modifyProductMaxTxt.getText().isEmpty() && !modifyProductCostTxt.getText().isEmpty()) {
            Inventory.deleteProduct(productToModify);

            Inventory.updateProduct(selectedIndex, productToModify);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please enter valid values in each text field.");
            alert.showAndWait();
        }
    }

    /**
     * Checks search field for an integer or string
     * Searches the parts tableview for matching parameters
     * @param event enter key pressed
     */
    @FXML
    void onActionModifyProductSearch(ActionEvent event) {

        String searchParts = modifyProductSearchTxt.getText();

        if (searchParts.equals("")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Search Warning");
            alert.setHeaderText("There were no parts found.");
            alert.setContentText("No part Id or name was entered.");
            alert.showAndWait();
            modifyProductAddTableView.setItems(getAllParts());
        }
        else {
            boolean search = false;

            try {

                Part searchedPart = lookupPart(Integer.parseInt(searchParts));

                if (searchedPart != null) {

                    ObservableList<Part> parts = FXCollections.observableArrayList();
                    parts.add(searchedPart);
                    modifyProductAddTableView.setItems(parts);
                }
                else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found.");
                    alert.setContentText("Search term entered does not match any part Id.");
                    alert.showAndWait();
                    modifyProductAddTableView.setItems(getAllParts());
                }
            }
            catch (NumberFormatException e) {

                ObservableList<Part> allParts = getAllParts();

                if (allParts.isEmpty()) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found.");
                    alert.setContentText("There are no parts in part list.\n Please add parts to list first.");
                    alert.showAndWait();
                    modifyProductAddTableView.setItems(getAllParts());
                }
                else {
                    for (Part p : allParts) {
                        if (p.getName().contains(searchParts)) {
                            search = true;
                            ObservableList parts = lookUpPart(searchParts);
                            modifyProductAddTableView.setItems(parts);
                        }
                    }
                    if (!search) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Part Search Warning");
                        alert.setHeaderText("There were no parts found.");
                        alert.setContentText("The searched term does not match any part name.");
                        alert.showAndWait();
                        modifyProductAddTableView.setItems(getAllParts());
                    }
                }
            }
        }
        modifyProductSearchTxt.setText("");
    }

    /**
     * Populates selected product values in correlating text boxes to be edited
     * @param product the selected product to modify
     */
    public void sendProduct (Product product) {

        productToModify = product;

        if (product != null) {

            this.modifyProductIdTxt.setText(String.valueOf(productToModify.getId()));
            this.modifyProductNameTxt.setText(productToModify.getName());
            this.modifyProductCostTxt.setText(String.valueOf(productToModify.getPrice()));
            this.modifyProductInvTxt.setText(String.valueOf(productToModify.getStock()));
            this.modifyProductMaxTxt.setText(String.valueOf(productToModify.getMax()));
            this.modifyProductMinTxt.setText(String.valueOf(productToModify.getMin()));

            modifyProductRemoveTableView.setItems(productToModify.getAssociatedParts());
        }
    }

    /**
     * Used to indicate part to delete from associated parts table
     * @param id the part id of selected part
     * @return part if true, null if false
     */
    public Part selectedPart (int id) {
        for (Part p : Inventory.getAllParts()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Initializes controller class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyProductAddTableView.setItems(Inventory.getAllParts());

        modifyProductAddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAddCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductAddInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAddPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        modifyProductRemovePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductRemoveCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductRemoveInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductRemovePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

}