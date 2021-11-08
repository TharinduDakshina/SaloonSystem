package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import modle.Service;
import view.Tm.ServiceTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageServiceFormController {

    public AnchorPane changeFinalItemContext;
    public TableView tblService;
    public TableColumn colServiceId;
    public TableColumn colServiceName;
    public TableColumn colServicePrice;

    public void initialize(){
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colServicePrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        try {
            ObservableList<ServiceTm> serviceDetails= ServiceController.loadServices();
            tblService.setItems(serviceDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void addOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddServiceForm.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalItemContext.getChildren().clear();
        changeFinalItemContext.getChildren().add(load);
    }

    public void removeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RemoveServiceOnAction.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalItemContext.getChildren().clear();
        changeFinalItemContext.getChildren().add(load);
    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/UpdateServiceForm.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalItemContext.getChildren().clear();
        changeFinalItemContext.getChildren().add(load);
    }
}
