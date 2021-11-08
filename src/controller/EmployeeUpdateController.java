package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modle.Employee;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeUpdateController {


    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public PasswordField pswdPassword;
    public ComboBox cmbEmpId;
    public TextField txtCategory;
    public JFXButton btnUpdate;
    public JFXButton btcClear;
    public TextField txtAddress;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern categoryPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern contactPattern = Pattern.compile("^[0][0-9]{9}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");

    public void initialize(){
        try {
            ArrayList<String> employeeIds=EmployeeController.loadEmployeeId();
            cmbEmpId.getItems().addAll(employeeIds);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtCategory, categoryPattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContact, contactPattern);
        map.put(txtSalary, salaryPattern);
    }

    public void employeeUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employeeDetails=new ArrayList<>();
        employeeDetails.add(new Employee(
                (String) cmbEmpId.getValue(),txtName.getText(),txtCategory.getText(),txtAddress.getText(),txtContact.getText(),Double.valueOf(txtSalary.getText()),pswdPassword.getText()
        ));

        if (EmployeeController.updateEmployee(employeeDetails)) {
            new Alert(Alert.AlertType.CONFIRMATION,"Successful").show();
        }else new Alert(Alert.AlertType.WARNING,"Some thing is wrong.Please try again.").show();
        cleartextFeels();
        disableFalse();
    }



    public void clearOnActon(ActionEvent actionEvent) {
        cleartextFeels();
    }

    private void cleartextFeels() {
        txtName.clear();
        txtSalary.clear();
        txtCategory.clear();
        txtContact.clear();
        txtAddress.clear();
        pswdPassword.clear();
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employee= EmployeeController.searchEmployee(cmbEmpId.getValue());
        for (Employee temp:employee
             ) {
            txtName.setText(temp.getEmp_Name());
            txtCategory.setText(temp.getEmp_Category());
            txtAddress.setText(temp.getEmp_Address());
            txtContact.setText(temp.getEmp_Contact());
            txtSalary.setText(String.valueOf(temp.getEmp_Basic_Salary()));
            pswdPassword.setText(temp.getEmp_Password());
        }
        disableTrue();
    }

    private void disableFalse() {
        txtName.setDisable(true);
        txtCategory.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtSalary.setDisable(true);
        pswdPassword.setDisable(true);
        btnUpdate.setDisable(true);
        btcClear.setDisable(true);
    }
    private void disableTrue() {
        txtName.setDisable(false);
        txtCategory.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtSalary.setDisable(false);
        pswdPassword.setDisable(false);
        btnUpdate.setDisable(false);
        btcClear.setDisable(false);
    }

    public void txt_Field_KeyRelease(KeyEvent keyEvent) {
        Object response= ValidationUtil.validation(map,btnUpdate);

        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof TextField){
                TextField errorText=(TextField) response;
                errorText.requestFocus();
            }
        }
    }
}
