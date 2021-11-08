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
import view.Tm.ItemTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ManageItemFormController {


    public AnchorPane changeFinalItemContext;
    public TableView tblItem;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colPrice;
    public TableColumn colQtyOnHand;

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        try {
            ObservableList<ItemTm> items= ItemAdminController.loadItems();
            tblItem.getItems().addAll(items);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ItemUpdateForm.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalItemContext.getChildren().clear();
        changeFinalItemContext.getChildren().add(load);
    }

    public void registerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RegisterItemForm.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalItemContext.getChildren().clear();
        changeFinalItemContext.getChildren().add(load);
    }

    public void removeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DeleteItemForm.fxml");
        Parent load = FXMLLoader.load(resource);
        changeFinalItemContext.getChildren().clear();
        changeFinalItemContext.getChildren().add(load);
    }
}
