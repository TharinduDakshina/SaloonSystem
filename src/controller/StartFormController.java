package controller;


import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartFormController implements Initializable {


    public Label none;
 
    public AnchorPane apnStartContext;
    public JFXProgressBar progressBarStart;
    public ProgressIndicator circleProgress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new ShowSplashScreen().start();
    }


    class ShowSplashScreen extends Thread{
        public void run(){
            circleProgress.setStyle("-fx-bottom: black;");
            for (int i = 0; i <=10; i++) {
                double x=i *(0.1);
                progressBarStart.setProgress(x);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater(() -> {
                Stage stage=new Stage();
                Parent root=null;
                try {
                    root= FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
                } catch (IOException e) {
                    Logger.getLogger(DashBordFormController.class.getName()).log(Level.SEVERE,null,e);
                }
                Scene scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
                apnStartContext.getScene().getWindow().hide();
            });
        }

    }





}

