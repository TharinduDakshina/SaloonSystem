package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.Tm.AppointmentTm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageReportsController {

    public AnchorPane changeReportsFinalContext;
    public TableView tblAppointment;
    public TableColumn colApoId;
    public TableColumn colCustomer;
    public TableColumn colEmp;
    public TableColumn colTotal;
    public TableColumn colDiscount;
    public TableColumn colDate;

    public void initialize(){
        colApoId.setCellValueFactory(new PropertyValueFactory<>("apoNo"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerNIC"));
        colEmp.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {

            ObservableList<AppointmentTm> appointmentData=loadData();
            tblAppointment.getItems().addAll(appointmentData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<AppointmentTm> loadData() throws SQLException, ClassNotFoundException {
        ObservableList<AppointmentTm> apoData= FXCollections.observableArrayList();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * from appointment").executeQuery();
        while (rst.next()){
            apoData.add(new AppointmentTm(
                    rst.getString(1), rst.getString(2), rst.getString(4), rst.getDouble(5), rst.getDouble(6),rst.getDate(7)
            ));
        }
        return apoData;
    }

    public void monthlyInComeReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MonthlyIncomeReports.fxml");
        Parent load = FXMLLoader.load(resource);
        changeReportsFinalContext.getChildren().clear();
        changeReportsFinalContext.getChildren().add(load);
    }

    public void dalyInComeReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DalyIncomeReport.fxml");
        Parent load = FXMLLoader.load(resource);
        changeReportsFinalContext.getChildren().clear();
        changeReportsFinalContext.getChildren().add(load);
    }
}
