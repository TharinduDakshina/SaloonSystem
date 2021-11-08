package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modle.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteItemFormController {
    public TextField txtItemId;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtQtyOnHand;
    public JFXButton btnDelete;
    public ComboBox cmbItemId;

    public void initialize(){
        try {
            loadComboBox();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadComboBox() throws SQLException, ClassNotFoundException {
        ArrayList<String> itemId=ItemAdminController.loadItemCode();
        cmbItemId.getItems().addAll(itemId);
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items=ItemAdminController.searchItem(cmbItemId.getValue());
        for (Item tm:items
             ) {
            txtName.setText(tm.getItemName());
            txtPrice.setText(String.valueOf(tm.getUnitePrice()));
            txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
        }
        disableTrue();
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (ItemAdminController.deleteItem(cmbItemId.getValue())) {
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted Item").show();
            cmbItemId.getItems().clear();
            loadComboBox();
            disableFalse();
            clearFields();
        }else new Alert(Alert.AlertType.WARNING,"Try Again").show();

    }

    private void clearFields() {
        txtName.clear();
        txtPrice.clear();
        txtQtyOnHand.clear();
    }

    private void disableFalse() {

        txtName.setDisable(true);
        txtPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
        btnDelete.setDisable(true);

    }
    private void disableTrue() {

        txtName.setDisable(false);
        txtPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);
        btnDelete.setDisable(false);

    }
}
