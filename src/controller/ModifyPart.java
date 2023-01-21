package controller;

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
public class ModifyPart implements Initializable {

    public Label modifyPartLastLabel;
    Stage stage;
    Parent scene;
    Part partToModify;

    @FXML
    private TextField modifyPartCostTxt;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private RadioButton modifyPartInHouseRBtn;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartLastTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private RadioButton modifyPartOutsourcedRBtn;

    @FXML
    private ToggleGroup sourceTg;

    /**
     * Confirms and cancels changes made to part being modified
     * @param event cancel button selection
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionModifyPartCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
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
    void onActionModifyPartInHouseRBtn(ActionEvent event) {

        modifyPartLastLabel.setText("Machine ID");
    }

    /**
     * Changes last labels text to Company Name
     * @param event Outsourced radio button selection
     */
    @FXML
    void onActionModifyPartOutsourcedRBtn(ActionEvent event) {

        modifyPartLastLabel.setText("Company Name");
    }

    /**
     * Verifies all input values are present. If the values are correct, it saves the part.
     * If values are missing, an error message populates.
     *
     * Logical Error: No matter what Error I tried to force the system to throw it would
     * only throw the Warning. It would skip all Errors and just run the catch statement.
     * I fixed this by embedding the Errors inside if statements before part creation.
     *
     * @param event the event to save a part
     * @throws IOException throws I/O exception
     */
    @FXML
    void onActionModifyPartSave(ActionEvent event) throws IOException {

        try {

            int id = Integer.parseInt(modifyPartIdTxt.getText());
            double price = Double.parseDouble(modifyPartCostTxt.getText());
            int stock = Integer.parseInt(modifyPartInvTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            String name = modifyPartNameTxt.getText();
            String companyName = modifyPartLastTxt.getText();


            if (modifyPartInHouseRBtn.isSelected()) {

                if (Integer.parseInt(modifyPartMinTxt.getText()) > Integer.parseInt(modifyPartMaxTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum value cannot be greater than maximum value.");
                    alert.showAndWait();
                }
                else if (Integer.parseInt(modifyPartInvTxt.getText()) > Integer.parseInt(modifyPartMaxTxt.getText()) || Integer.parseInt(modifyPartInvTxt.getText()) < Integer.parseInt(modifyPartMinTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between minimum and maximum values.");
                    alert.showAndWait();
                }
                else if (modifyPartNameTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a name.");
                    alert.showAndWait();
                }
                else if (modifyPartMinTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a minimum inventory value.");
                    alert.showAndWait();
                }
                else if (modifyPartMaxTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a maximum inventory value.");
                    alert.showAndWait();
                }
                else if (modifyPartCostTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a price value.");
                    alert.showAndWait();
                }
                else if (modifyPartInvTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory field must not be empty.");
                    alert.showAndWait();
                }
                else if (modifyPartLastTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Machine ID or Company Name must not be empty.");
                    alert.showAndWait();
                }
                else if (!modifyPartNameTxt.getText().isEmpty() && !modifyPartMaxTxt.getText().isEmpty() && !modifyPartMinTxt.getText().isEmpty() && !modifyPartCostTxt.getText().isEmpty() && !modifyPartInvTxt.getText().isEmpty()) {
                    int machineId = Integer.parseInt(modifyPartLastTxt.getText());
                    Part inHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                    ((InHouse) inHousePart).setMachineId(Integer.parseInt(modifyPartLastTxt.getText()));
                    Inventory.updatePart(Integer.parseInt(modifyPartIdTxt.getText()), inHousePart);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }

            if (modifyPartOutsourcedRBtn.isSelected()) {

                if (Integer.parseInt(modifyPartMinTxt.getText()) > Integer.parseInt(modifyPartMaxTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum value cannot be greater than maximum value.");
                    alert.showAndWait();
                }
                else if (Integer.parseInt(modifyPartInvTxt.getText()) > Integer.parseInt(modifyPartMaxTxt.getText()) || Integer.parseInt(modifyPartInvTxt.getText()) < Integer.parseInt(modifyPartMinTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between minimum and maximum values.");
                    alert.showAndWait();
                }
                else if (modifyPartNameTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a name.");
                    alert.showAndWait();
                }
                else if (modifyPartMinTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a minimum inventory value.");
                    alert.showAndWait();
                }
                else if (modifyPartMaxTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a maximum inventory value.");
                    alert.showAndWait();
                }
                else if (modifyPartCostTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Part must have a price value.");
                    alert.showAndWait();
                }
                else if (modifyPartInvTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory field must not be empty.");
                    alert.showAndWait();
                }
                else if (modifyPartLastTxt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Machine ID or Company Name must not be empty.");
                    alert.showAndWait();
                }
                else if (!modifyPartNameTxt.getText().isEmpty() && !modifyPartMaxTxt.getText().isEmpty() && !modifyPartMinTxt.getText().isEmpty() && !modifyPartCostTxt.getText().isEmpty() && !modifyPartInvTxt.getText().isEmpty()) {
                    Part outsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    ((Outsourced) outsourcedPart).setCompanyName(modifyPartLastTxt.getText());
                    Inventory.updatePart(Integer.parseInt(modifyPartIdTxt.getText()), outsourcedPart);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please enter valid values in each text field.");
            alert.showAndWait();
        }
    }

    /**
     * Populates selected part values in correlating text boxes to be edited
     * @param part the selected part to modify
     */
    public void sendPart (Part part) {
        partToModify = part;
        modifyPartCostTxt.setText(String.valueOf(partToModify.getPrice()));
        modifyPartIdTxt.setText(String.valueOf(partToModify.getId()));
        modifyPartInvTxt.setText(String.valueOf(partToModify.getStock()));
        modifyPartNameTxt.setText(partToModify.getName());
        modifyPartMaxTxt.setText(String.valueOf(partToModify.getMax()));
        modifyPartMinTxt.setText(String.valueOf(partToModify.getMin()));


        if (partToModify instanceof InHouse) {

            modifyPartLastTxt.setText(String.valueOf(((InHouse) partToModify).getMachineId()));
            modifyPartInHouseRBtn.setSelected(true);
        }
        if (partToModify instanceof Outsourced) {

            modifyPartLastTxt.setText(((Outsourced) partToModify).getCompanyName());
            modifyPartOutsourcedRBtn.setSelected(true);
            modifyPartLastLabel.setText("Company Name");
        }
    }

    /**
     * Initializes controller class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}