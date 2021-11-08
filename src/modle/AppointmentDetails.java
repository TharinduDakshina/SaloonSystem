package modle;

import java.util.Date;

public class AppointmentDetails {
    private String apo_NO;
    private String customer_Nic;
    private String service_Id;
    private String employee_Id;
    private double price;
    private double discount;
    private Date date;

    public AppointmentDetails(double price, Date date) {
        this.price = price;
        this.date = date;
    }

    public AppointmentDetails(String apo_NO, String customer_Nic, String service_Id, String employee_Id, double price, double discount, Date date) {
        this.apo_NO = apo_NO;
        this.customer_Nic = customer_Nic;
        this.service_Id = service_Id;
        this.employee_Id = employee_Id;
        this.price = price;
        this.discount = discount;
        this.date = date;
    }

    public AppointmentDetails() {
    }

    public String getApo_NO() {
        return apo_NO;
    }

    public void setApo_NO(String apo_NO) {
        this.apo_NO = apo_NO;
    }

    public String getCustomer_Nic() {
        return customer_Nic;
    }

    public void setCustomer_Nic(String customer_Nic) {
        this.customer_Nic = customer_Nic;
    }

    public String getService_Id() {
        return service_Id;
    }

    public void setService_Id(String service_Id) {
        this.service_Id = service_Id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmployee_Id() {
        return employee_Id;
    }

    public void setEmployee_Id(String employee_Id) {
        this.employee_Id = employee_Id;
    }

    @Override
    public String toString() {
        return "AppointmentDetails{" +
                "apo_NO='" + apo_NO + '\'' +
                ", customer_Nic='" + customer_Nic + '\'' +
                ", service_Id='" + service_Id + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", date=" + date +
                ", employee_Id='" + employee_Id + '\'' +
                '}';
    }
}
