package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modle.Customer;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerRegisterFormController {

    public AnchorPane registerContext;
    public JFXTextField txtNIC;
    public JFXTextField txtName;
    public JFXTextField txtAge;
    public JFXButton btnConfirm;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern customerIdPattern = Pattern.compile("^[1-9][0-9]{8,10}([0-9]|V|v)");
    Pattern customerNamePattern = Pattern.compile("^[A-z]{3,30}$");
    Pattern agePattern = Pattern.compile("^^([1-9][0-9]|[1-9])$");

    private void storeValidations() {
        map.put(txtNIC, customerIdPattern);
        map.put(txtName, customerNamePattern);
        map.put(txtAge, agePattern);
    }

    public void initialize(){

        storeValidations();
    }
    public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (txtNIC.getText().isEmpty() || txtName.getText().isEmpty() || txtAge.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Fill Form Correct").showAndWait();
            return;
        }
        Customer customer=new Customer(
                txtNIC.getText(),txtName.getText(),txtAge.getText(),"None","0"
        );


        if (new CustomerController().addCustomer(customer)){
            new Alert(Alert.AlertType.CONFIRMATION,"Welcome VIMASHI SALOON...").showAndWait();
            Stage window=(Stage)registerContext.getScene().getWindow();
            window.close();

        }else {
            new Alert(Alert.AlertType.WARNING,"Already exits. \nPLEASE TRY AGAIN").show();

        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        txtNIC.clear();
        txtName.clear();
        txtAge.clear();
    }

    public void validation(KeyEvent keyEvent) {

        Object response= ValidationUtil.validationCustomerRegister(map,btnConfirm);

        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof JFXTextField){
                JFXTextField errorText=(JFXTextField) response;
                errorText.requestFocus();
            }else if (response instanceof Boolean){

            }
        }

    }



}
