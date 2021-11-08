package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modle.Service;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateServiceFormController {

    public TextField txtServiceName;
    public TextField txtServicePrice;
    public ComboBox cmbServiceId;
    public JFXButton btnUpdate;
    public JFXButton btnClear;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern itemPricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    private void storeValidations() {

        map.put(txtServiceName, namePattern);
        map.put(txtServicePrice, itemPricePattern);

    }

    public void initialize(){
        try {
            ArrayList<String> serviceId= ServiceController.loadServiceIds();
            cmbServiceId.getItems().addAll(serviceId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storeValidations();
    }
    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Service> serviceDetails=ServiceController.searchService(cmbServiceId.getValue());
        for (Service tm:serviceDetails
             ) {
            txtServiceName.setText(tm.getServiceName());
            txtServicePrice.setText(String.valueOf(tm.getServicePrice()));
            disableTrue();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Service> serviceData=new ArrayList<>();
        serviceData.add(new Service(
                (String) cmbServiceId.getValue(),txtServiceName.getText(),Double.valueOf(txtServicePrice.getText())
        ));
        if (ServiceController.updateService(serviceData)) {
            new Alert(Alert.AlertType.CONFIRMATION,"Updated Service").show();
            clearData();
            disableFalse();
        }else new Alert(Alert.AlertType.WARNING,"Something is wrong").show();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearData();
    }

    private void clearData() {
        txtServiceName.clear();
        txtServicePrice.clear();
    }

    private void disableFalse() {
        txtServiceName.setDisable(true);
        txtServicePrice.setDisable(true);
        btnClear.setDisable(true);
        btnUpdate.setDisable(true);
    }
    private void disableTrue() {
        txtServiceName.setDisable(false);
        txtServicePrice.setDisable(false);
        btnUpdate.setDisable(false);
        btnClear.setDisable(false);
    }

    public void checkedValidation(KeyEvent keyEvent) {
        Object response = ValidationUtil.validation(map,btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }

    }
}
