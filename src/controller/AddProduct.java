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
import static model.Inventory.getAllParts;

/**
 * FXML controller class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class AddProduct implements Initializable {

    Stage stage;
    Parent scene;
    Product newProduct = new Product(0, null, 0, 0, 0, 0);
    ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Double> addProductAddCostCol;

    @FXML
    private TableColumn<Part, Integer> addProductAddInvCol;

    @FXML
    private TableColumn<Part, Integer> addProductAddPartIdCol;

    @FXML
    private TableColumn<Part, String> addProductAddPartNameCol;

    @FXML
    private TableView<Part> addProductAddTableView;

    @FXML
    private TextField addProductCostTxt;

    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TableColumn<?, Double> addProductRemoveCostCol;

    @FXML
    private TableColumn<?, Integer> addProductRemoveInvCol;

    @FXML
    private TableColumn<?, Integer> addProductRemovePartIdCol;

    @FXML
    private TableColumn<?, String> addProductRemovePartNameCol;

    @FXML
    private TableView<Part> addProductRemoveTableView;

    @FXML
    private TextField addProductSearchTxt;

    /**
     * Adds selected associated part to a product
     * @param event add button selection
     */
    @FXML
    void onActionAddProductAdd(ActionEvent event) {

        Part sPart = addProductAddTableView.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedPart(sPart);
        addProductRemoveTableView.setItems(newProduct.getAssociatedParts());
    }

    /**
     * Confirms and cancels changes made to product being added
     * @param event cancel button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionAddProductCancel(ActionEvent event) throws IOException {

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
    void onActionAddProductRemove(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part. Do you wish to continue?");
        alert.setTitle("Confirm Delete");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part sPart = addProductRemoveTableView.getSelectionModel().getSelectedItem();
            if (newProduct.deleteAssociatedPart(sPart)) {

                newProduct.getAssociatedParts().remove(addProductRemoveTableView.getSelectionModel().getSelectedItem());
            }
            associatedPartList = newProduct.getAssociatedParts();
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
     * Future Enhancement: Implementing a database that allows users to save products
     * even after closing the application.
     *
     * @param event the event to save a part
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionAddProductSave(ActionEvent event) throws IOException {

        try {
            newProduct.setId(Integer.parseInt(addProductIdTxt.getText()));
            newProduct.setName(addProductNameTxt.getText());
            newProduct.setStock(Integer.parseInt(addProductInvTxt.getText()));
            newProduct.setMin(Integer.parseInt(addProductMinTxt.getText()));
            newProduct.setMax(Integer.parseInt(addProductMaxTxt.getText()));
            newProduct.setPrice(Double.parseDouble(addProductCostTxt.getText()));

            if (newProduct.getMin() > newProduct.getMax()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum value cannot be greater than maximum value.");
                alert.showAndWait();
            }
            else if (newProduct.getStock() > newProduct.getMax() || newProduct.getStock() < newProduct.getMin()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between minimum and maximum values.");
                alert.showAndWait();
            }
            else if (addProductNameTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product must have a name.");
                alert.showAndWait();
            }
            else if (addProductInvTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory field must not be empty.");
                alert.showAndWait();
            }
            else if (addProductMaxTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product must have a maximum inventory value.");
                alert.showAndWait();
            }
            else if (addProductMinTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product must have a minimum inventory value.");
                alert.showAndWait();
            }
            else if (addProductCostTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product must have a price value.");
                alert.showAndWait();
            }
            else if (!addProductNameTxt.getText().isEmpty() && !addProductInvTxt.getText().isEmpty() && !addProductMinTxt.getText().isEmpty() && !addProductMaxTxt.getText().isEmpty() && !addProductCostTxt.getText().isEmpty()) {
                Inventory.addProduct(newProduct);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please enter valid values in each text field.");
            alert.showAndWait();
        }
    }

    /**
     * Checks search field for an integer or string
     * Searches the parts tableview for matching parameters
     * @param event enter key pressed
     */
    @FXML
    void onActionAddProductSearch(ActionEvent event) {

        String searchParts = addProductSearchTxt.getText();

        if (searchParts.equals("")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Search Warning");
            alert.setHeaderText("There were no parts found.");
            alert.setContentText("No part Id or name was entered.");
            alert.showAndWait();
            addProductAddTableView.setItems(getAllParts());
        }
        else {
            boolean search = false;
            try {

                Part searchedPart = lookupPart(Integer.parseInt(searchParts));

                if (searchedPart != null) {

                    ObservableList<Part> parts = FXCollections.observableArrayList();
                    parts.add(searchedPart);
                    addProductAddTableView.setItems(parts);
                }
                else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found.");
                    alert.setContentText("Search term entered does not match any part Id.");
                    alert.showAndWait();
                    updatePartsTable();
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
                    updatePartsTable();
                }
                else {
                    for (Part p : allParts) {
                        if (p.getName().contains(searchParts)) {
                            search = true;
                            ObservableList parts = lookUpPart(searchParts);
                            addProductAddTableView.setItems(parts);
                        }
                    }
                    if (!search) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Part Search Warning");
                        alert.setHeaderText("There were no parts found.");
                        alert.setContentText("The searched term does not match any part name.");
                        alert.showAndWait();
                        updatePartsTable();
                    }
                }
            }
        }
    }

    /**
     * Updates parts table with all parts in view
     */
    public void updatePartsTable() {

        addProductAddTableView.setItems(Inventory.getAllParts());
    }

    /**
     * Initializer controller class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addProductAddTableView.setItems(Inventory.getAllParts());
        addProductIdTxt.setText(String.valueOf(Inventory.getAllProducts().size()+1));

        addProductAddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAddCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductAddInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAddPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        associatedPartList = newProduct.getAssociatedParts();

        addProductRemovePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductRemovePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductRemoveInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductRemoveCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}