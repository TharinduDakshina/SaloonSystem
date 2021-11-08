package modle;

public class Item {
    private String itemId;
    private String itemName;
    private double unitePrice;
    private int qtyOnHand;
    private double total;

    public Item(String itemId, String itemName, double unitePrice, int qtyOnHand, double total) {
        this.setItemId(itemId);
        this.setItemName(itemName);
        this.setUnitePrice(unitePrice);
        this.setQtyOnHand(qtyOnHand);
        this.setTotal(total);
    }

    public Item(String itemId, String itemName, double unitePrice, int qtyOnHand) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitePrice = unitePrice;
        this.qtyOnHand = qtyOnHand;
    }

    public Item() {
    }



    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + getItemId() + '\'' +
                ", itemName='" + getItemName() + '\'' +
                ", unitePrice=" + getUnitePrice() +
                ", qtyOnHand=" + getQtyOnHand() +
                '}';
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
