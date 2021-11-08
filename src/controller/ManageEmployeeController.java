package controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.Tm.EmployeeTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ManageEmployeeController {


    public AnchorPane changeFinalContext;
    public TableView tblEmployee;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colCategory;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colSalary;

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        try {

            ObservableList<EmployeeTm> employee=EmployeeController.loadEmployees();
            tblEmployee.getItems().addAll(employee);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void registerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/EmployeeRegisterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalContext.getChildren().clear();
        changeFinalContext.getChildren().add(load);
    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/EmployeeUpdate.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalContext.getChildren().clear();
        changeFinalContext.getChildren().add(load);
    }

    public void deleteOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/EmployeeDeleteOnAction.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalContext.getChildren().clear();
        changeFinalContext.getChildren().add(load);
    }
}
