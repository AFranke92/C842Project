package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Part;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * FXML controller class
 * @author
 * Abigail Franke
 * afra480@wgu.edu
 * Student Id: 010025705
 */
public class AddPart implements Initializable {

    public Label addPartLastLabel;
    Stage stage;
    Parent scene;
    static int partIdCount = 1;


    @FXML
    private TextField addPartCostTxt;

    @FXML
    private TextField addPartIdTxt;

    @FXML
    private RadioButton addPartInHouseRBtn;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartMachineIdTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private RadioButton addPartOutsourcedRBtn;

    @FXML
    private ToggleGroup sourceTg;

    /**
     * Confirms and cancels changes made to part being added
     * @param event cancel button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionAddPartCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Changes last labels text to Machine ID
     * @param event InHouse radio button selection
     */
    @FXML
    void onActionAddPartInHouseRBtn(ActionEvent event) {

        addPartLastLabel.setText("Machine ID");
    }

    /**
     * Changes last labels text to Company Name
     * @param event Outsourced radio button selection
     */
    @FXML
    void onActionAddPartOutsourcedRBtn(ActionEvent event) {

        addPartLastLabel.setText("Company Name");
    }

    /**
     * Verifies all input values are present. If the values are correct, it saves the part.
     * If values are missing, an error message populates.
     *
     * Logical Error: No matter what Error I tried to force the system to throw it would
     * only throw the Warning. It would skip all Errors and just run the catch statement.
     * I fixed this by embedding the Errors inside if statements before part creation.
     *
     * Future Enhancement: Implementing a database that allows users to save parts
     * even after closing the application.
     *
     * @param event the event to save a part
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionAddPartSave(ActionEvent event) throws IOException {

        int id = partIdCount;
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part part : allParts) {
            if (part.getId() > partIdCount) {
                partIdCount = part.getId();
            }
        }

            try {

                double price = Double.parseDouble(addPartCostTxt.getText());
                int stock = Integer.parseInt(addPartInvTxt.getText());
                int max = Integer.parseInt(addPartMaxTxt.getText());
                int min = Integer.parseInt(addPartMinTxt.getText());
                String name = addPartNameTxt.getText();
                String companyName = String.valueOf(addPartMachineIdTxt.getText());


                if (addPartInHouseRBtn.isSelected()) {
                    if (Integer.parseInt(addPartMinTxt.getText()) > Integer.parseInt(addPartMaxTxt.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum value cannot be greater than maximum value.");
                        alert.showAndWait();
                    }
                    else if (Integer.parseInt(addPartInvTxt.getText()) > Integer.parseInt(addPartMaxTxt.getText()) || Integer.parseInt(addPartInvTxt.getText()) < Integer.parseInt(addPartMinTxt.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between minimum and maximum values.");
                        alert.showAndWait();
                    }
                    else if (addPartNameTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a name.");
                        alert.showAndWait();
                    }
                    else if (addPartMinTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a minimum inventory value.");
                        alert.showAndWait();
                    }
                    else if (addPartMaxTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a maximum inventory value.");
                        alert.showAndWait();
                    }
                    else if (addPartCostTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a price value.");
                        alert.showAndWait();
                    }
                    else if (addPartInvTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory field must not be empty.");
                        alert.showAndWait();
                    }
                    else if (addPartMachineIdTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Machine ID or Company Name must not be empty.");
                        alert.showAndWait();
                    }
                    else if (!addPartNameTxt.getText().isEmpty() && !addPartMaxTxt.getText().isEmpty() && !addPartMinTxt.getText().isEmpty() && !addPartCostTxt.getText().isEmpty() && !addPartMachineIdTxt.getText().isEmpty() && !addPartInvTxt.getText().isEmpty()) {
                        int machineId = Integer.parseInt(addPartMachineIdTxt.getText());
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

                        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();

                        partIdCount++;

                    }
                }

                if (addPartOutsourcedRBtn.isSelected()) {
                    if (Integer.parseInt(addPartMinTxt.getText()) > Integer.parseInt(addPartMaxTxt.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum value cannot be greater than maximum value.");
                        alert.showAndWait();
                    }
                    else if (Integer.parseInt(addPartInvTxt.getText()) > Integer.parseInt(addPartMaxTxt.getText()) || Integer.parseInt(addPartInvTxt.getText()) < Integer.parseInt(addPartMinTxt.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between minimum and maximum values.");
                        alert.showAndWait();
                    }
                    else if (addPartNameTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a name.");
                        alert.showAndWait();
                    }
                    else if (addPartMinTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a minimum inventory value.");
                        alert.showAndWait();
                    }
                    else if (addPartMaxTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a maximum inventory value.");
                        alert.showAndWait();
                    }
                    else if (addPartCostTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a price value.");
                        alert.showAndWait();
                    }
                    else if (addPartInvTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory field must not be empty.");
                        alert.showAndWait();
                    }
                    else if (addPartMachineIdTxt.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Machine ID or Company Name must not be empty.");
                        alert.showAndWait();
                    }
                    else if (!addPartNameTxt.getText().isEmpty() && !addPartMaxTxt.getText().isEmpty() && !addPartMinTxt.getText().isEmpty() && !addPartCostTxt.getText().isEmpty() && !addPartMachineIdTxt.getText().isEmpty() && !addPartInvTxt.getText().isEmpty()) {

                        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

                        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();

                        partIdCount++;

                    }
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
     * Initializes controller class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPartIdTxt.setText(String.valueOf(partIdCount));
    }
}
