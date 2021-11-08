package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.lowagie.text.html.simpleparser.ALink;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modle.Customer;
import util.ValidationUtil;
import view.Tm.CustomerTm;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.net.URI;

public class AdminFormController {

    public AnchorPane adminContext;
    public TextField txtCustomerNic;
    public TextField txtCustomerName;
    public TextField txtCustomerAge;
    public AnchorPane manageContext;
    public JFXButton logOutButton;
    public TableView tblCustomer;
    public TableColumn colNIC;
    public TableColumn colName;
    public TableColumn colAge;
    public TableColumn colHairStyle;
    public TableColumn colCount;
    public JFXButton btnAddCustomer;
    public Hyperlink help;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern customerIdPattern = Pattern.compile("^[1-9][0-9]{8,10}([0-9]|V|v)");
    Pattern customerNamePattern = Pattern.compile("^[A-z]{3,30}$");
    Pattern agePattern = Pattern.compile("^^([1-9][0-9]|[1-9])$");

    private void storeValidations() {
        map.put(txtCustomerNic, customerIdPattern);
        map.put(txtCustomerName, customerNamePattern);
        map.put(txtCustomerAge, agePattern);
    }


    public void initialize(){
        storeValidations();
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



    public void manageEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageEmployee.fxml");
        Parent load = FXMLLoader.load(resource);
        manageContext.getChildren().clear();
        manageContext.getChildren().add(load);

    }

    public void manageItemContext(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItemForm.fxml");
        Parent load = FXMLLoader.load(resource);
        manageContext.getChildren().clear();
        manageContext.getChildren().add(load);
    }

    public void manageServiceOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageServiceForm.fxml");
        Parent load = FXMLLoader.load(resource);
        manageContext.getChildren().clear();
        manageContext.getChildren().add(load);
    }

    public void reportsOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageReports.fxml");
        Parent load = FXMLLoader.load(resource);
        manageContext.getChildren().clear();
        manageContext.getChildren().add(load);
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        scene.getStylesheets().add("style/MyStyle.css");
        Stage window=(Stage) adminContext.getScene().getWindow();
        window.setScene(scene);
    }



    private void clearFields() {
        txtCustomerNic.clear();
        txtCustomerAge.clear();
        txtCustomerName.clear();
    }

    public void customerONAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../view/AdminCustomers.fxml");
        Parent load = FXMLLoader.load(resource);
        manageContext.getChildren().clear();
        manageContext.getChildren().add(load);
    }

    public void addCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtCustomerName.getText().isEmpty() || txtCustomerAge.getText().isEmpty() || txtCustomerNic.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please fill fields needs").showAndWait();
            return;
        }

        Customer customer=new Customer(
                txtCustomerNic.getText(),txtCustomerName.getText(),txtCustomerAge.getText(),"None","0"
        );
        if (new CustomerController().addCustomer(customer)){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer added to System.").showAndWait();
            clearFields();
            tblCustomer.getItems().clear();
            ObservableList<CustomerTm> customers=ServiceController.loadCustomerTable();
            tblCustomer.getItems().addAll(customers);
        }else {
            new Alert(Alert.AlertType.WARNING,"Already exits. \nPLEASE TRY AGAIN").show();
        }
    }

    public void validation(KeyEvent keyEvent) {
        Object response= ValidationUtil.validation(map,btnAddCustomer);

        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof JFXTextField){
                JFXTextField errorText=(JFXTextField) response;
                errorText.requestFocus();
            }else if (response instanceof Boolean){

            }
        }
    }


    public void goToGoogleHelp(MouseEvent mouseEvent) {
       Desktop d=Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.ijse.lk"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
