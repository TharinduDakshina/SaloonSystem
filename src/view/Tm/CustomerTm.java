package view.Tm;

public class CustomerTm {
    private String customerNIC;
    private String customerName;
    private int age;
    private String hairStyle;
    private int count;

    public CustomerTm(String customerNIC, String customerName, int age, String hairStyle, int count) {
        this.setCustomerNIC(customerNIC);
        this.setCustomerName(customerName);
        this.setAge(age);
        this.setHairStyle(hairStyle);
        this.setCount(count);
    }

    public CustomerTm() {
    }

    public String getCustomerNIC() {
        return customerNIC;
    }

    public void setCustomerNIC(String customerNIC) {
        this.customerNIC = customerNIC;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHairStyle() {
        return hairStyle;
    }

    public void setHairStyle(String hairStyle) {
        this.hairStyle = hairStyle;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CustomerTm{" +
                "customerNIC='" + customerNIC + '\'' +
                ", customerName='" + customerName + '\'' +
                ", age=" + age +
                ", hairStyle='" + hairStyle + '\'' +
                ", count=" + count +
                '}';
    }
}
