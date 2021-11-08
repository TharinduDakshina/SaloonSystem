package controller;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modle.Employee;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class BarberFormController {

    public Label lblName;
    public AnchorPane barberContext;
    public ComboBox cmbEmployees;
    public Label lblDate;
    public Label lblTime;
    public ImageView employeeImageView;

    ArrayList<Employee> employeeImages=new ArrayList();
    public void initialize(){
        loadDateTime();
        try {

            ArrayList<String> employee=BarberController.loadEmployees();
            cmbEmployees.getItems().addAll(employee);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        employeeImages.add(new Employee("E-001","Samantha","../asrte/Samantha.jpeg"));
        employeeImages.add(new Employee("E-002","Jayantha","../asrte/Jayantha.jpg"));
        employeeImages.add(new Employee("E-003","Sunil","../asrte/Sunil.jpeg"));
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



    public void continueOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Date date=new Date();
       // System.out.println(date);

        if (BarberController.markAttendances((String)cmbEmployees.getValue(),date,"1")) {
            new Alert(Alert.AlertType.CONFIRMATION,"Your Attendance marked").showAndWait();
        }else new Alert(Alert.AlertType.WARNING,"Error").showAndWait();


        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        scene.getStylesheets().add("style/MyStyle.css");
        Stage window=(Stage) barberContext.getScene().getWindow();
        window.setScene(scene);
    }




    public void selectEmpNameOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String empId=BarberController.getEmpName(cmbEmployees.getValue());
        Image nextImage= setImage((String)cmbEmployees.getValue());
        employeeImageView.setImage(nextImage);
        if (empId!=null){
            lblName.setText(empId);
        }else {new Alert(Alert.AlertType.ERROR,"Error").show();}
    }

    private Image setImage(String empId) {
        for (Employee temp:employeeImages
             ) {
            if (empId.equals(temp.getEmp_Id())){
                return new Image(getClass().getResourceAsStream(temp.getEmployeeImageView()));
            }
        }

        return null;
    }
}
