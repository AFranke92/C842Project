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

/**
 * FXML controller class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class MainScreen implements Initializable {

    Stage stage;
    Parent scene;
    Product foundProducts;
    Part foundParts;

    @FXML
    private TableColumn<Part, String> mainPartsNameCol;

    @FXML
    private TableColumn<Part, Double> mainPartsCostCol;

    @FXML
    private TableColumn<Part, Integer> mainPartsIdCol;

    @FXML
    private TableColumn<Part, Integer> mainPartsInvCol;

    @FXML
    private TextField mainPartsSearchTxt;

    @FXML
    private TableView<Part> mainPartsTableView;

    @FXML
    private TableColumn<Product, Double> mainProductsCostCol;

    @FXML
    private TableColumn<Product, Integer> mainProductsIdCol;

    @FXML
    private TableColumn<Product, Integer> mainProductsInvCol;

    @FXML
    private TableColumn<Product, String> mainProductsNameCol;

    @FXML
    private TextField mainProductsSearchTxt;

    @FXML
    private TableView<Product> mainProductsTableView;

    /**
     * Opens add product scene
     * @param event add product button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionMainAddProduct(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks if a product is selected. If product is selected, checks if
     * product has part(s) associated to it.
     * If part(s) are associated, populates error until all associated part(s) are removed.
     * Checks and confirms product delete.
     * @param event delete product button selection
     */
    @FXML
    void onActionMainDeleteProduct(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected Product. Would you like to continue?");
        alert.setTitle("Confirm Deletion");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Product selectedProduct = mainProductsTableView.getSelectionModel().getSelectedItem();

            if (selectedProduct == null) {

                Alert alert2 = new Alert(Alert.AlertType.ERROR, "No product selected. Please select a product.");
                alert2.setTitle("Deletion Error");
                alert2.showAndWait();
            } else {

                try {

                    if (selectedProduct.getAssociatedParts().isEmpty()) {
                        Inventory.deleteProduct(selectedProduct);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR, "Unable to delete a product with any parts associated with it.\nPlease remove all associated parts before deleting product.");
                        alert.showAndWait();
                    }
                } catch (UnsupportedOperationException e) {
                    alert.setTitle("Product Delete Error");
                    alert.setHeaderText("Product NOT Deleted");
                    alert.setContentText("No product selected");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Opens add part scene
     * @param event add part button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionMainAddPart(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks if a part is selected.
     * If part is selected, checks and confirms part delete.
     * @param event delete part button selection
     */
    @FXML
    void onActionMainDeletePart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part. Would you like to continue?");
        alert.setTitle("Confirm Deletion");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedPart = mainPartsTableView.getSelectionModel().getSelectedItem();

            if (selectedPart == null) {

                Alert alert2 = new Alert(Alert.AlertType.ERROR, "No part selected. Please select a part.");
                alert2.setTitle("Deletion Error");
                alert2.showAndWait();
            }
            else {

                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Closes the application
     * @param event exit button selection
     */
    @FXML
    void onActionMainExit(ActionEvent event) {

        System.exit(0);
    }

    /**
     * Takes selected part and sends part information to
     * modify part scene.
     * @param event modify part button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionMainModifyPart(ActionEvent event) throws IOException {

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader1.load();

        ModifyPart MPart = loader1.getController();
        MPart.sendPart(mainPartsTableView.getSelectionModel().getSelectedItem());


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader1.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Takes selected product and sends product information to
     * modify product scene.
     * @param event modify product button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionMainModifyProduct(ActionEvent event) throws IOException {

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader2.load();

        ModifyProduct MProduct = loader2.getController();
        MProduct.sendProduct(mainProductsTableView.getSelectionModel().getSelectedItem());


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader2.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Checks search field for an integer or string
     * Searches the parts tableview for matching parameters
     * @param event enter key pressed
     */
    @FXML
    void onActionMainPartsSearch(ActionEvent event) {
        String searchedParts = mainPartsSearchTxt.getText();

        ObservableList<Part> parts = filterParts(searchedParts);

        if (searchedParts.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Search Warning");
            alert.setHeaderText("There were no parts found.");
            alert.setContentText("No part Id or name was entered.");
            alert.showAndWait();
            mainPartsTableView.setItems(getAllParts());
        }
        else {
            boolean search = false;
            try {
                foundParts = lookupPart(Integer.parseInt(searchedParts));
                if (foundParts != null) {
                    ObservableList<Part> part = FXCollections.observableArrayList();
                    part.add(foundParts);
                    mainPartsTableView.setItems(part);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found.");
                    alert.setHeaderText("Search term entered does not match any part Id.");
                    alert.showAndWait();
                    mainPartsTableView.setItems(getAllParts());
                }
            }
            catch (NumberFormatException e) {
                ObservableList<Part> allParts = getAllParts();
                if (allParts.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found.");
                    alert.setContentText("There are no parts in part list.\nPlease add parts to list first.");
                    alert.showAndWait();
                    mainPartsTableView.setItems(getAllParts());
                }
                else {
                    for (Part p : allParts) {
                        if (p.getName().contains(searchedParts)) {
                            search = true;
                            parts = lookUpPart(searchedParts);
                            mainPartsTableView.setItems(parts);
                        }
                    }
                    if (!search) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Part Search Warning");
                        alert.setHeaderText("There were no parts found.");
                        alert.setContentText("The searched term does not match any part name.");
                        alert.showAndWait();
                        mainPartsTableView.setItems(getAllParts());
                    }
                }
            }
        }
        mainPartsSearchTxt.setText("");
    }

    /**
     * Checks search field for an integer or string
     * Searches the products tableview for matching parameters
     * @param event enter key pressed
     */
    @FXML
    void onActionMainProductsSearch(ActionEvent event) {

        String searchedProducts = mainProductsSearchTxt.getText();

        ObservableList<Product> product = filterProducts(searchedProducts);

        if (searchedProducts.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product Search Warning");
            alert.setHeaderText("There were no products found.");
            alert.setContentText("No product Id or name was entered.");
            alert.showAndWait();
            mainProductsTableView.setItems(product);
        }
        else {
            boolean search = false;
            try {
                foundProducts = lookupProduct(Integer.parseInt(searchedProducts));
                if (foundProducts != null) {
                    ObservableList<Product> products = FXCollections.observableArrayList();
                    products.add(foundProducts);
                    mainProductsTableView.setItems(products);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found.");
                    alert.setContentText("Search term entered does not match any product Id.");
                    alert.showAndWait();
                    mainProductsTableView.setItems(getAllProducts());
                }
            }
            catch (NumberFormatException e) {
                ObservableList<Product> allProducts = getAllProducts();
                if (allProducts.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found.");
                    alert.setContentText("There are no products in product list.\n Please add products to list first.");
                    alert.showAndWait();
                    mainProductsTableView.setItems(getAllProducts());
                }
                else {
                    for (Product p : allProducts) {
                        if (p.getName().contains(searchedProducts)) {
                            search = true;
                            product = lookupProduct(searchedProducts);
                            mainProductsTableView.setItems(product);
                        }
                    }
                    if (!search) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Product Search Warning");
                        alert.setHeaderText("There were no products found.");
                        alert.setContentText("The searched term does not match any product name.");
                        alert.showAndWait();
                        mainProductsTableView.setItems(getAllProducts());
                    }
                }
            }
        }
        mainProductsSearchTxt.setText("");
    }

    /**
     * Used as an ObservableList in parts search
     * @param name of searched part
     * @return the part(s) with matching name(s)
     */
    public ObservableList<Part> filterParts(String name) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part part : allParts) {
            if (part.getName().contains(name)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    /**
     * Used as an ObservableList in products search
     * @param name of searched product
     * @return product(s) with matching name(s)
     */
    public ObservableList<Product> filterProducts(String name) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product product : allProducts) {
            if (product.getName().contains(name)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    /**
     * Initializes controller class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainPartsTableView.setItems(Inventory.getAllParts());

        mainPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartsCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        mainProductsTableView.setItems(Inventory.getAllProducts());

        mainProductsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProductsCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainProductsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProductsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }

}
