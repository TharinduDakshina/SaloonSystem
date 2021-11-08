package modle;

import java.util.Date;

public class Order {
    private String orderId;
    private String customerId;
    private String apoNo;
    private double total;
    private Date date;

    public Order(double total, Date date) {
        this.total = total;
        this.date = date;
    }

    public Order(String orderId, String customerId, String apoNo, double total, Date date) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setApoNo(apoNo);
        this.setTotal(total);
        this.setDate(date);
    }

    public Order() {
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getApoNo() {
        return apoNo;
    }

    public void setApoNo(String apoNo) {
        this.apoNo = apoNo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", apoNo='" + apoNo + '\'' +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}
