package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import modle.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDeleteOnActionController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public PasswordField pswdPassword;
    public TextArea txtAddress;
    public ComboBox cmbEmployee;
    public TextField txtCategory;
    public JFXButton btnDelete;

    public void initialize() throws SQLException, ClassNotFoundException {
       loadCmb();
    }

    private void loadCmb() throws SQLException, ClassNotFoundException {
        ArrayList<String> empId=EmployeeController.loadEmployeeId();
        cmbEmployee.getItems().addAll(empId);
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employee= EmployeeController.searchEmployee(cmbEmployee.getValue());
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
        btnDelete.setDisable(true);

    }
    private void disableTrue() {
        txtName.setDisable(false);
        txtCategory.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtSalary.setDisable(false);
        pswdPassword.setDisable(false);
        btnDelete.setDisable(false);

    }

    private void cleartextFeels() {
        txtName.clear();
        txtSalary.clear();
        txtCategory.clear();
        txtContact.clear();
        txtAddress.clear();
        pswdPassword.clear();
    }

    public void employeeDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (EmployeeController.deleteEmployee(cmbEmployee.getValue())) {
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted employee.").show();
            cmbEmployee.getItems().clear();
            loadCmb();
            cleartextFeels();
            disableFalse();
        }else new Alert(Alert.AlertType.WARNING,"Something is wrong.").show();


    }
}
