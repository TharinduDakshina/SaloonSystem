package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modle.Item;
import modle.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.Tm.CartTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class CashierAddItemFormController {


    public Label lblTotal;
    public Label lblQtyOnHand;
    public JFXTextField txtCustomerNIC;
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTime;
    public ComboBox cmbItemCode;
    public Label lblItemName;
    public TextField txtQty;
    public Label lblUnitePrice;
    public ComboBox cmbAppointmentNo;
    public Label lblAppointmentAmount;
    public TableView<CartTm> tblCart;
    public TableColumn colItemCode;
    public TableColumn colItemName;
    public TableColumn colUnitePrice;
    public TableColumn colQTY;
    public TableColumn colTotal;
    public TableColumn colEditField;
    public AnchorPane buyItemContext;
    static double total=0;
    public JFXButton btnAddCart;
    public JFXButton btnDelete;
    public JFXTextField txtReceivedCash;
    public Label cahsRecevedName;
    public Label balance;
    public Label balanceName;
    public Label lblBalance;
    public JFXButton btnPay;
    public Label lblServiceId;

    int cartRemoveRow=-1;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern customerIdPattern = Pattern.compile("^[1-9][0-9]{8,10}([0-9]|V|v)");
    Pattern qtyPattern = Pattern.compile("^([1-9]|[1-9][0-9]{1,2})$");

    private void storeValidations() {
        map.put(txtCustomerNIC, customerIdPattern);
        map.put(txtQty, qtyPattern);

    }

    public void initialize(){
        storeValidations();
        btnAddCart.setDisable(true);
        btnPay.setDisable(true);
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitePrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        try {
            ArrayList<String> appointment=ItemController.loadApoId();
            cmbAppointmentNo.getItems().addAll(appointment);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            ArrayList<String> itemCode=ItemController.loadItems();
            cmbItemCode.getItems().addAll(itemCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {

            lblOrderId.setText(ItemController.setOrderId());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateTime();

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartRemoveRow=(int)newValue;
        });
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

    public void searchItemOnAction(ActionEvent actionEvent) {
        try {
            ArrayList<Item> itemData=ItemController.getItem((String) cmbItemCode.getValue());
            setItemData(itemData);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemData(ArrayList<Item> itemData) {
        for (Item temp: itemData
             ) {
            lblItemName.setText(temp.getItemName());
            lblUnitePrice.setText(String.valueOf(temp.getUnitePrice()));
            lblQtyOnHand.setText(String.valueOf(temp.getQtyOnHand()));
        }
    }

    public void payOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {
        Date date=new Date();
        ArrayList<Item> item=new ArrayList();
        double total=0;

        if (CashierServiceController.checkAppointmentPayed(cmbAppointmentNo.getValue())){
            new Alert(Alert.AlertType.WARNING,"This Appointment already pay.").showAndWait();
            return;
        }

        if (CashierServiceController.payAmount((String) cmbAppointmentNo.getValue(),lblServiceId.getText())) {

        }else new Alert(Alert.AlertType.WARNING,"Service Details table is not updated").showAndWait();


        for (CartTm temp:obList
             ) {
            total+= temp.getTotal();
            item.add(new Item(
                    temp.getItemCode(), temp.getItemName(), temp.getUnitePrice(), temp.getQty(), temp.getTotal()
            ));
        }

        Order order=new Order(
                lblOrderId.getText(),txtCustomerNIC.getText(),(String) cmbAppointmentNo.getValue(),total,date
        );

        if (ItemController.placeOrder(item,order)){
            new Alert(Alert.AlertType.CONFIRMATION,"Order Successful.").showAndWait();
            printBill();
            clearOnAction();
            lblBalance.setVisible(false);
            balanceName.setVisible(false);
            cahsRecevedName.setVisible(false);
            txtReceivedCash.setVisible(false);
            lblOrderId.setText(ItemController.setOrderId());
        }else {
            new Alert(Alert.AlertType.ERROR,"Something is Wrong Please try again").show();
        }
    }

    private void printBill() throws JRException {
        String finalTotal=lblTotal.getText();
        String cashReceived=txtReceivedCash.getText();
        String balance=lblBalance.getText();
        String apoNo=(String) cmbAppointmentNo.getValue();
        String oNo=lblOrderId.getText();

        HashMap map=new HashMap();
        map.put("finalTotal",finalTotal);
        map.put("recevidCash",cashReceived);
        map.put("balance",balance);
        map.put("appointmentNo",apoNo);
        map.put("orderNo",oNo);

        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/BuyItemBill.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);
        ObservableList<CartTm> items = tblCart.getItems();
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(items.toArray()));
        JasperViewer.viewReport(jasperPrint,false);
    }

    private void clearOnAction() {
        txtCustomerNIC.clear();
        lblItemName.setText("-");
        lblUnitePrice.setText("-");
        lblQtyOnHand.setText("-");
        txtQty.clear();
        lblAppointmentAmount.setText("-");
        tblCart.getItems().clear();
        lblTotal.setText("00.00/=");
        txtReceivedCash.clear();
        lblBalance.setText("-");
    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage window=(Stage) buyItemContext.getScene().getWindow();
        window.setScene(scene);
    }

    public void searchApointmentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String[][] apoData= ItemController.getApoAmount((String) cmbAppointmentNo.getValue());
        lblAppointmentAmount.setText(apoData[0][0]);
        lblServiceId.setText(apoData[0][1]);
        lblTotal.setText(total+Double.valueOf(lblAppointmentAmount.getText())+"0 /=");
        total+=Double.valueOf(lblAppointmentAmount.getText());
    }




    ObservableList<CartTm> obList= FXCollections.observableArrayList();
    public void addCartOnAction(ActionEvent actionEvent) {
        cmbAppointmentNo.setDisable(false);
        String itemName = lblItemName.getText();
        int qtyOnHand = Integer.valueOf(lblQtyOnHand.getText());
        double unitePrice = Double.parseDouble(lblUnitePrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = qty * unitePrice;
        cahsRecevedName.setVisible(true);
        txtReceivedCash.setVisible(true);

        if (qty > qtyOnHand) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        CartTm tm = new CartTm(
                (String) cmbItemCode.getValue(), itemName, unitePrice, qty, total
        );

        int rowNumber = isExtists(tm);

        if (rowNumber == -1) {
            obList.add(tm);
        } else {
            //update
            CartTm temp = obList.get(rowNumber);
            CartTm newTm = new CartTm(
                    temp.getItemCode(),temp.getItemName(),temp.getUnitePrice(), temp.getQty()+qty, temp.getTotal()+total
            );
            obList.remove(rowNumber);
            obList.add(newTm);
        }
        txtQty.clear();
        tblCart.setItems(obList);
        calculateCost();
    }
        private void calculateCost() {
            double finalTotal=0;
            for (CartTm temp:obList
            ) {

                finalTotal +=temp.getTotal();
            }
            total=finalTotal;
            lblTotal.setText((finalTotal)+"0 /=");
        }

        private int isExtists(CartTm tm) {
            for (int i = 0; i <obList.size() ; i++) {
                if (tm.getItemCode().equals(obList.get(i).getItemCode())){
                    return i;
                }
            }
            return -1;
        }


    public void validation(KeyEvent keyEvent) {
        Object response= ValidationUtil.validation(map,btnAddCart);

        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof TextField){
                TextField errorText=(TextField) response;
                errorText.requestFocus();
            }else if (response instanceof Boolean){

            }
        }
    }

    public void deleteOnAction(ActionEvent event) {
        if (cartRemoveRow==-1){
            new Alert(Alert.AlertType.WARNING,"Please Select Row").show();
        }else {
            obList.remove(cartRemoveRow);
            calculateCost();
            tblCart.refresh();
        }
    }

    public void setBalance(ActionEvent event) {
        lblBalance.setVisible(true);
        balanceName.setVisible(true);
        btnPay.setDisable(false);
        lblBalance.setText(String.valueOf(Double.valueOf(txtReceivedCash.getText())-total)+"0/=");
    }
}
