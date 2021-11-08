package view.Tm;

import java.sql.Date;

public class AppointmentTm {
    private String apoNo;
    private String customerNIC;
    private String empId;
    private double total;
    private double discount;
    private Date date;

    public AppointmentTm(String apoNo, String customerNIC, String empId, double total, double discount, Date date) {
        this.setApoNo(apoNo);
        this.setCustomerNIC(customerNIC);
        this.setEmpId(empId);
        this.setTotal(total);
        this.setDiscount(discount);
        this.setDate(date);
    }

    public AppointmentTm() {
    }


    public String getApoNo() {
        return apoNo;
    }

    public void setApoNo(String apoNo) {
        this.apoNo = apoNo;
    }

    public String getCustomerNIC() {
        return customerNIC;
    }

    public void setCustomerNIC(String customerNIC) {
        this.customerNIC = customerNIC;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    @Override
    public String toString() {
        return "AppointmentTm{" +
                "apoNo='" + apoNo + '\'' +
                ", customerNIC='" + customerNIC + '\'' +
                ", empId='" + empId + '\'' +
                ", total=" + total +
                ", discount=" + discount +
                ", date=" + date +
                '}';
    }
}
