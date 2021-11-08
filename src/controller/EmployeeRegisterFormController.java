package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modle.Employee;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeRegisterFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public PasswordField pswdPassword;
    public TextField txtCategory;
    public JFXButton btnSave;
    public TextField txtAddress;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[E][-][0-9]{1,5}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern categoryPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern contactPattern = Pattern.compile("^[0][0-9]{9}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");


    public void initialize(){
        btnSave.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtCategory, categoryPattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContact, contactPattern);
        map.put(txtSalary, salaryPattern);
    }

    public void employeeRegisterOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees=new ArrayList();
        employees.add(new Employee(
                txtId.getText(),txtName.getText(),txtCategory.getText(),txtAddress.getText(),txtContact.getText(),Double.valueOf(txtSalary.getText()),pswdPassword.getText()
        ));

        if (EmployeeController.addEmployee(employees,txtId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION,"Successful").show();
        }else new Alert(Alert.AlertType.WARNING,"Already used employee id.").show();
    }

    public void clearOnActon(ActionEvent actionEvent) {
        txtId.clear();
        txtCategory.clear();
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
        txtSalary.clear();
        pswdPassword.clear();
    }

    public void txtFields_Key_Release(KeyEvent keyEvent) {
            Object response= ValidationUtil.validation(map,btnSave);

            if (keyEvent.getCode()== KeyCode.ENTER){
                if (response instanceof TextField){
                    TextField errorText=(TextField) response;
                    errorText.requestFocus();
                }else if (response instanceof Boolean){
                   pswdPassword.requestFocus();
                }
            }
    }
}
