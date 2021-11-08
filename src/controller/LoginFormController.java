package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modle.Employee;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class LoginFormController {
    public AnchorPane loginContext;
    public Label lblTitle;
    public JFXPasswordField pasPassword;
    public JFXButton btnLogin;
    public FontAwesomeIconView iconUser;
    public FontAwesomeIconView iconPassword;
    public JFXButton lblCustomer;
    public JFXTextField txtUserId;
    public RadioButton radioAttendance;
    public ToggleGroup user;
    public RadioButton radioCashier;

    public void characterOnAction(ActionEvent actionEvent) {
        txtUserId.setDisable(false);
        pasPassword.setDisable(false);
        btnLogin.setDisable(false);
        lblCustomer.setDisable(true);
    }


    public void loginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (txtUserId.getText().equalsIgnoreCase("admin") && pasPassword.getText().equals("123")){
            URL resource = getClass().getResource("../view/AdminForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene=new Scene(load);
           // scene.getStylesheets().add("style/AdminStyle.css");
            Stage window=(Stage) loginContext.getScene().getWindow();
            window.setScene(scene);

        }else if(radioAttendance.isSelected() && getEmployeePassword(txtUserId.getText(),pasPassword.getText())==1){
            URL resource = getClass().getResource("../view/BarberForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene=new Scene(load);
            Stage window=(Stage) loginContext.getScene().getWindow();
            window.setScene(scene);


        }else if (getEmployeePassword(txtUserId.getText(),pasPassword.getText())==1 ){
            URL resource = getClass().getResource("../view/CashierForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene=new Scene(load);
            Stage window=(Stage) loginContext.getScene().getWindow();
            window.setScene(scene);
        }else if (getEmployeePassword(txtUserId.getText(),pasPassword.getText())==2){
            System.out.println("=========================================");
            URL resource = getClass().getResource("../view/BarberForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene=new Scene(load);
            Stage window=(Stage) loginContext.getScene().getWindow();
            window.setScene(scene);
        }else {
            new Alert(Alert.AlertType.WARNING,"Invalid User Id or Password").show();
        }
    }

    private int getEmployeePassword(String userId,String password) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("select * from employee where emp_Id=? and emp_Password=?");
        stm.setObject(1,userId);
        stm.setObject(2,password);
        ResultSet rst = stm.executeQuery();

        if (rst.next()){
            if (rst.getString(3).equalsIgnoreCase("Cashier")){
                return 1;
            }else if (rst.getString(3).equalsIgnoreCase("Barber")){return 2;}
        }
        return 0;
    }

    public void customerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBordForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage window=(Stage) loginContext.getScene().getWindow();
        window.setScene(scene);
    }


}
