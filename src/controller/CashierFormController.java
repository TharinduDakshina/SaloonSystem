package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modle.AppointmentDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CashierFormController {

    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtCustomerId;
    public JFXTextField txtServiceId;
    public JFXTextField txtDate;
    public JFXTextField txtPrice;
    public JFXTextField txtDiscount;
    public AnchorPane cashierContext;
    public JFXButton btnDelete;
    public JFXButton btnPayAmount;
    public ComboBox cmbApoNo;
    public Label lblCashName;
    public JFXTextField txtCash;
    public Label lblBalance;
    public Label lblBalanceName;


    public void initialize(){
        loadDateTime();

        try {
            ArrayList<String> apoNo=CashierServiceController.loadAppointmentNo();
            cmbApoNo.getItems().addAll(apoNo);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateTime() {
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("YYYY-MM-dd ");
        lblDate.setText(f.format(date));

        Timeline time=new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime= LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+" : "+currentTime.getMinute()+
                            " : "+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }



    public void addItemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierAddItemForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage window=(Stage) cashierContext.getScene().getWindow();
        window.setScene(scene);
    }

    public void searchAppointmentOnAction(ActionEvent actionEvent) {
        try {

           ArrayList<AppointmentDetails> AppointmentData=CashierServiceController.geAppointmentData((String) cmbApoNo.getValue());
           setData(AppointmentData);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setData(ArrayList<AppointmentDetails> appointmentData) {
        DateFormat dateFormat=new SimpleDateFormat("YYYY-MM-DD ");
        if (appointmentData.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Invalid Appointment No").show();
        }else {
            for (AppointmentDetails temp:appointmentData
            ) {
                txtCustomerId.setText(temp.getCustomer_Nic());
                if (temp.getService_Id()==null){
                    txtServiceId.setText("None");
                }else {
                    txtServiceId.setText(temp.getService_Id());
                }
                txtPrice.setText(String.valueOf(temp.getPrice()));
                txtDiscount.setText(String.valueOf(temp.getDiscount()));
                txtDate.setText(dateFormat.format(temp.getDate()));
            }
            txtCustomerId.setDisable(false);
            txtServiceId.setDisable(false);
            txtDate.setDisable(false);
            txtPrice.setDisable(false);
            txtDiscount.setDisable(false);
            btnDelete.setDisable(false);
            lblCashName.setVisible(true);
            txtCash.setVisible(true);

        }
    }

    public void appoimentDealeteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (CashierServiceController.deleteAppointment(txtCustomerId.getText(),(String) cmbApoNo.getValue())){
            new Alert(Alert.AlertType.CONFIRMATION,"This Appointment Deleted.").show();
            clearTextFields();
        }else {
            new Alert(Alert.AlertType.WARNING,"This Appointment not Delete").show();
        }

    }

    public void payAmountOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {
        if (CashierServiceController.checkAppointmentPayed(cmbApoNo.getValue())){
            new Alert(Alert.AlertType.WARNING,"This Appointment already pay.").showAndWait();
            clearTextFields();
            return;
        }
        if (CashierServiceController.payAmount((String) cmbApoNo.getValue(),txtServiceId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION,"Successful.").showAndWait();
            printBill();
            clearTextFields();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again.").show();
        }
    }

    private void printBill() throws SQLException, ClassNotFoundException, JRException {
     String serviceName=CashierServiceController.getServiceName(txtServiceId.getText());
        String apoNo=(String) cmbApoNo.getValue();
        String nic=txtCustomerId.getText();
        String serviceId=serviceName;
        String price=txtPrice.getText();
        String discount=txtDiscount.getText();
        String cashReceived=txtCash.getText();
        String balance=lblBalance.getText();

        System.out.println(serviceId);
        System.out.println(balance);

        HashMap map=new HashMap();
        map.put("appointmentNo",apoNo);
        map.put("customerNIC",nic);
        map.put("serviceName",serviceId);
        map.put("descount",discount);
        map.put("price",price);
        map.put("cashRecived",cashReceived);
        map.put("balance",balance);

        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/print bill by service.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
        JasperViewer.viewReport(jasperPrint,false);
    }

    private void clearTextFields() {
        txtCustomerId.clear();
        txtCash.clear();
        txtPrice.clear();
        txtDiscount.clear();
        txtDate.clear();
        txtServiceId.clear();
        btnPayAmount.setDisable(true);
        lblBalance.setText("00.00");
        lblCashName.setVisible(false);
        txtCash.setVisible(false);
        lblBalanceName.setVisible(false);
        lblBalance.setVisible(false);
    }



    public void refreshOnAction(ActionEvent actionEvent) {
        cmbApoNo.getItems().clear();
        try {
            ArrayList<String> apoNo = CashierServiceController.loadAppointmentNo();
            cmbApoNo.getItems().addAll(apoNo);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void logOut(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        scene.getStylesheets().add("style/MyStyle.css");
        Stage window=(Stage) cashierContext.getScene().getWindow();
        window.setScene(scene);
    }

    public void setBalance(KeyEvent keyEvent) {
        btnPayAmount.setDisable(false);
       double amount=0;
        if (keyEvent.getCode()== KeyCode.ENTER){
           lblBalanceName.setVisible(true);
           lblBalance.setVisible(true);
           amount=Double.valueOf(txtCash.getText())-Double.valueOf(txtPrice.getText());
       }
        lblBalance.setText(amount+"0");

    }
}
