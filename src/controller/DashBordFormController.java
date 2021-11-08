package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modle.Customer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class DashBordFormController {

    public AnchorPane dashBordContext;
    public Label lblDate;
    public Label lblAppointment;
    public TextField txtNIC;
    public TextField txtName;
    public TextField txtAge;
    public TextField txtHairCut;
    public ComboBox cmbsSelectBAr;
    public ComboBox cmbOtherService;
    public Label lblTotal;
    public  ComboBox cmbHairStyle;
    public Label lblTime;
    public JFXButton btnBack;
    public JFXButton btnConfirm;
    public JFXButton btnRegister;
    double[] totalAndDiscount=new double[2];

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[1-9][0-9]{8,10}([0-9]|V|v)");

    private void storeValidations() {
        map.put(txtNIC, idPattern);
    }

    public void initialize(){
       btnConfirm.setDisable(true);
        loadTimeDate();
       storeValidations();

        try {

            lblAppointment.setText(CustomerController.setApoNo());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            loadBarber();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            loadStyles();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            loadOtherService();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadTimeDate() {
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

    private void loadStyles() throws SQLException, ClassNotFoundException {
        ArrayList<String> styles=new CustomerController().getStyles();
        cmbHairStyle.getItems().addAll(styles);
    }

    private void loadOtherService() throws SQLException, ClassNotFoundException {
        ArrayList<String> services= new CustomerController().getServices();
        cmbOtherService.getItems().addAll(services);
    }

    private void loadBarber() throws SQLException, ClassNotFoundException {
        ArrayList<String> barber= new CustomerController().getBarber(lblDate.getText());
        cmbsSelectBAr.getItems().addAll(barber);
    }

    public void customerRegisterOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerRegisterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }



    public void searchCustomerDataOnAction(ActionEvent actionEvent) {
        if (txtNIC.getText().equalsIgnoreCase("-1")){
            btnBack.setDisable(false);
            return;
        }
        try {
            Customer customer=new CustomerController().searchCustomer(txtNIC.getText());
            if(customer==null){
                new Alert(Alert.AlertType.WARNING,"Please Register.").show();
            }else {setData(customer);}

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setData(Customer customer) {
        txtName.setDisable(false);
        txtAge.setDisable(false);
        txtHairCut.setDisable(false);
        cmbOtherService.setDisable(false);
        cmbsSelectBAr.setDisable(false);
        cmbHairStyle.setDisable(false);

        txtName.setText(customer.getCustomerName());
        txtAge.setText(customer.getCustomerAge());
        txtHairCut.setText(customer.getHairStyle());

    }

    public void styleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        txtHairCut.setText((String) cmbHairStyle.getValue());
        totalAndDiscount=CustomerController.setTotal(txtNIC.getText(),cmbHairStyle.getValue());
        lblTotal.setText(totalAndDiscount[0]+"0 /=");
    }

    public void otherServiceOnActiuon(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnConfirm.setDisable(false);
        double[] otherPrice=CustomerController.setFinalTotal(cmbOtherService.getValue(),txtNIC.getText());
        lblTotal.setText((totalAndDiscount[0]+otherPrice[0])+"0 /=");
        totalAndDiscount[0]+=otherPrice[0];
        totalAndDiscount[1]+=otherPrice[1];
    }

    public void confirmOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {
        String serviceId=CustomerController.getServiceId((String)cmbOtherService.getValue());
        Date date=new Date();
        if (CustomerController.createAppointment(txtNIC.getText(),totalAndDiscount[0],totalAndDiscount[1], date,
                lblAppointment.getText(), serviceId,(String)cmbsSelectBAr.getValue())){
            new Alert(Alert.AlertType.CONFIRMATION,"Thank You sri.\n Welcome to Vimashi Saloon...!").showAndWait();
            printAppointment();
        }else {
            new Alert(Alert.AlertType.WARNING,"Data Not Added please Check").show();
        }
        CustomerController.updateCustomer(txtNIC.getText(),txtHairCut.getText());
        resetData();
        lblAppointment.setText(CustomerController.setApoNo());
    }

    private void printAppointment() throws JRException {
        String apoNo=lblAppointment.getText();
        String cstNIC=txtNIC.getText();
        String cstName=txtName.getText();
        String cstAge=txtAge.getText();
        String cstHairStyle=txtHairCut.getText();
        String barber=(String) cmbsSelectBAr.getValue();
        String otherService=(String) cmbOtherService.getValue();
        String total=lblTotal.getText();

        HashMap map=new HashMap();
        map.put("appointmentNo",apoNo);
        map.put("NIC",cstNIC);
        map.put("name",cstName);
        map.put("style",cstHairStyle);
        map.put("age",cstAge);
        map.put("barber",barber);
        map.put("otherService",otherService);
        map.put("total",total);

        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/Appointment.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
        JasperViewer.viewReport(jasperPrint,false);
    }

    private void resetData() {
        txtNIC.clear();
        txtHairCut.clear();
        txtAge.clear();
        txtName.clear();

        txtName.setDisable(true);
        txtAge.setDisable(true);
        txtHairCut.setDisable(true);
        cmbHairStyle.setDisable(true);
        cmbsSelectBAr.setDisable(true);
        cmbOtherService.setDisable(true);
        btnConfirm.setDisable(true);
    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        scene.getStylesheets().add("style/MyStyle.css");
        Stage window=(Stage) dashBordContext.getScene().getWindow();
        window.setScene(scene);
    }

    public void validation(KeyEvent keyEvent) {
        Object response= ValidationUtil.validationCustomer(map,btnConfirm);
        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof TextField){
                TextField errorText=(TextField) response;
                errorText.requestFocus();
            }else if (response instanceof Boolean){

            }
        }
    }
}
