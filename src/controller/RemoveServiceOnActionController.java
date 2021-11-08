package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modle.Service;

import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveServiceOnActionController {
    
    public TextField txtServiceName;
    public TextField txtServicePrice;
    public ComboBox cmbServiceId;
    public JFXButton btnDelete;


    public void initialize() throws SQLException, ClassNotFoundException {
        loadComboBox();

    }

    private void loadComboBox() throws SQLException, ClassNotFoundException {
        ArrayList<String> serviceIds=ServiceController.loadServiceIds();
        cmbServiceId.getItems().addAll(serviceIds);
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

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            if (ServiceController.deleteService(cmbServiceId.getValue())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted Service.").show();
                cmbServiceId.getItems().clear();
                clearData();
                disableFalse();
                loadComboBox();
            }else new Alert(Alert.AlertType.WARNING,"Something is Wrong").show();
    }

    private void disableFalse() {
        txtServiceName.setDisable(true);
        txtServicePrice.setDisable(true);
        btnDelete.setDisable(true);

    }
    private void disableTrue() {
        txtServiceName.setDisable(false);
        txtServicePrice.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void clearData() {
        txtServiceName.clear();
        txtServicePrice.clear();
    }
}
