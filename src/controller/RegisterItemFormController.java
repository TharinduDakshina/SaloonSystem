package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modle.Item;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RegisterItemFormController {

    public TextField txtItemId;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtQtyOnHand;
    public JFXButton btnAdd;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[I][-][0-9]{1,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern itemPricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]{1,2}$");



    public void initialize(){
        btnAdd.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtItemId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtPrice, itemPricePattern);
        map.put(txtQtyOnHand, qtyPattern);
    }

        public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemArrayList = new ArrayList();
        itemArrayList.add(new Item(
                txtItemId.getText(),txtName.getText(),Double.valueOf(txtPrice.getText()),Integer.parseInt(txtQtyOnHand.getText())
        ));

        if (ItemAdminController.addItem(itemArrayList,txtItemId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION,"Successful").show();
            clearFields();
        }else new Alert(Alert.AlertType.WARNING,"Already used this item code.").show();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();

    }

    private void clearFields() {
        txtItemId.clear();
        txtName.clear();
        txtPrice.clear();
        txtQtyOnHand.clear();
    }

    public void txtFieldKeyRelease(KeyEvent keyEvent) {
        Object response= ValidationUtil.validation(map,btnAdd);

        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof TextField){
                TextField errorText=(TextField) response;
                errorText.requestFocus();
            }else if (response instanceof Boolean){

            }
        }
    }
}
