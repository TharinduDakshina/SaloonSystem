package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.Tm.CustomerTm;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminCustomersController {
    public TableView tblCustomer;
    public TableColumn colNIC;
    public TableColumn colName;
    public TableColumn colAge;
    public TableColumn colHairStyle;
    public TableColumn colCount;



    public void initialize(){
        colNIC.setCellValueFactory(new PropertyValueFactory<>("customerNIC"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colHairStyle.setCellValueFactory(new PropertyValueFactory<>("hairStyle"));
        colCount.setCellValueFactory(new PropertyValueFactory<>("count"));

        try {

            ObservableList<CustomerTm> customers=ServiceController.loadCustomerTable();
            tblCustomer.getItems().addAll(customers);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
