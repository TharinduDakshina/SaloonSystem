package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
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

public class AddServiceFormController {
    public TextField txtServiceId;
    public TextField txtServiceName;
    public TextField txtServicePrice;
    public JFXButton btnAdd;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[S][E][-][0-9]{1,3}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern itemPricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");




    public void initialize(){
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtServiceId, idPattern);
        map.put(txtServiceName, namePattern);
        map.put(txtServicePrice, itemPricePattern);

    }

    public void addServiceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Service> servicesData=new ArrayList<>();
        servicesData.add(new Service(
                txtServiceId.getText(),txtServiceName.getText(),Double.valueOf(txtServicePrice.getText())
        ));
        if (ServiceController.addService(servicesData,txtServiceId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Service added to system").show();
            clearData();
        }else new Alert(Alert.AlertType.WARNING,"Try Again").show();

    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearData();
    }

    private void clearData() {
        txtServiceId.clear();
        txtServiceName.clear();
        txtServicePrice.clear();
    }

    public void txt_Field_KeyRelease(KeyEvent keyEvent) {
        Object response = ValidationUtil.validation(map,btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }
}
