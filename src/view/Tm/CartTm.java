package view.Tm;

import javafx.scene.control.Button;

public class CartTm {
    private String itemCode;
    private String itemName;
    private double unitePrice;
    private int qty;
    private double total;


    public CartTm(String itemCode, String itemName, double unitePrice, int qty, double total) {
        this.setItemCode(itemCode);
        this.setItemName(itemName);
        this.setUnitePrice(unitePrice);
        this.setQty(qty);
        this.setTotal(total);
    }

    public CartTm() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getUnitePrice() {
        return unitePrice;
    }

    public void setUnitePrice(double unitePrice) {
        this.unitePrice = unitePrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }



    @Override
    public String toString() {
        return "CartTm{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", unitePrice=" + unitePrice +
                ", qty=" + qty +
                ", total=" + total +
                '}';
    }
}
