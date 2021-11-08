package view.Tm;

public class ItemTm {
    private String itemId;
    private String itemName;
    private double unitePrice;
    private int qtyOnHand;

    public ItemTm(String itemId, String itemName, double unitePrice, int qtyOnHand) {
        this.setItemId(itemId);
        this.setItemName(itemName);
        this.setUnitePrice(unitePrice);
        this.setQtyOnHand(qtyOnHand);
    }

    public ItemTm() {
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemTm{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", unitePrice=" + unitePrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
