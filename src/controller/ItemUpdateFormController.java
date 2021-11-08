package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modle.Item;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemUpdateFormController {
    public TextField txtItemId;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtQtyOnHand;
    public ComboBox cmbSelectItem;
    public JFXButton btnUpdate;
    public JFXButton btnClear;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern itemPricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]{1,2}$");

    private void storeValidations() {

        map.put(txtName, namePattern);
        map.put(txtPrice, itemPricePattern);
        map.put(txtQtyOnHand, qtyPattern);
    }

    public void initialize(){
        try {

            ArrayList<String> itemId= ItemAdminController.loadItemCode();
            cmbSelectItem.getItems().addAll(itemId);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storeValidations();
    }
    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemData=new ArrayList();
        itemData.add(new Item(
                (String) cmbSelectItem.getValue(),txtName.getText(),Double.valueOf(txtPrice.getText()),Integer.parseInt(txtQtyOnHand.getText())
        ));

        if (ItemAdminController.updateItem(itemData)) {
            new Alert(Alert.AlertType.CONFIRMATION,"Update item").show();
            clearFields();
        }else new Alert(Alert.AlertType.WARNING,"Something is wrong").show();
        disableFalse();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {

        txtName.clear();
        txtPrice.clear();
        txtQtyOnHand.clear();
    }
    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemDetails = ItemAdminController.searchItem(cmbSelectItem.getValue());
        setData(itemDetails);
    }

    private void setData(ArrayList<Item> itemDetails) {
        for (Item temp:itemDetails
             ) {
            txtName.setText(temp.getItemName());
            txtPrice.setText(String.valueOf(temp.getUnitePrice()));
            txtQtyOnHand.setText(String.valueOf(temp.getQtyOnHand()));
        }
        disableTrue();
    }

    private void disableFalse() {
        txtName.setDisable(true);
        txtPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
        btnUpdate.setDisable(true);
        btnClear.setDisable(true);
    }
    private void disableTrue() {
        txtName.setDisable(false);
        txtPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);
        btnClear.setDisable(false);
        btnUpdate.setDisable(false);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response= ValidationUtil.validation(map,btnUpdate);

        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof TextField){
                TextField errorText=(TextField) response;
                errorText.requestFocus();
            }
        }
    }
}
