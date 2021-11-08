package view.Tm;

public class EmployeeTm {
    private String empId;
    private String empName;
    private String category;
    private String address;
    private String contact;
    private double salary;

    public EmployeeTm(String empId, String empName, String category, String address, String contact, double salary) {
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setCategory(category);
        this.setAddress(address);
        this.setContact(contact);
        this.setSalary(salary);
    }

    public EmployeeTm() {
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeTm{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", salary=" + salary +
                '}';
    }
}
