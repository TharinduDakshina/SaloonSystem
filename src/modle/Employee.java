package modle;

import javafx.scene.image.ImageView;

public class Employee {
        private String emp_Id;
        private String emp_Name;
        private String emp_Category;
        private String emp_Address;
        private String emp_Contact;
        private double emp_Basic_Salary;
        private String emp_Password;
        private String employeeImageView;

        public Employee(String emp_Id, String emp_Name, String employeeImageView) {
                this.emp_Id = emp_Id;
                this.emp_Name = emp_Name;
                this.employeeImageView = employeeImageView;
        }

        public Employee(String emp_Id, String emp_Name, String emp_Category, String emp_Address, String emp_Contact, double emp_Basic_Salary, String emp_Password) {
                this.setEmp_Id(emp_Id);
                this.setEmp_Name(emp_Name);
                this.setEmp_Category(emp_Category);
                this.setEmp_Address(emp_Address);
                this.setEmp_Contact(emp_Contact);
                this.setEmp_Basic_Salary(emp_Basic_Salary);
                this.setEmp_Password(emp_Password);
        }

        public Employee(String emp_Category, String emp_Password) {
                this.setEmp_Category(emp_Category);
                this.setEmp_Password(emp_Password);
        }

        public Employee() {
        }

        public String getEmp_Id() {
                return emp_Id;
        }

        public void setEmp_Id(String emp_Id) {
                this.emp_Id = emp_Id;
        }

        public String getEmp_Name() {
                return emp_Name;
        }

        public void setEmp_Name(String emp_Name) {
                this.emp_Name = emp_Name;
        }

        public String getEmp_Category() {
                return emp_Category;
        }

        public void setEmp_Category(String emp_Category) {
                this.emp_Category = emp_Category;
        }

        public String getEmp_Address() {
                return emp_Address;
        }

        public void setEmp_Address(String emp_Address) {
                this.emp_Address = emp_Address;
        }

        public String getEmp_Contact() {
                return emp_Contact;
        }

        public void setEmp_Contact(String emp_Contact) {
                this.emp_Contact = emp_Contact;
        }

        public double getEmp_Basic_Salary() {
                return emp_Basic_Salary;
        }

        public void setEmp_Basic_Salary(double emp_Basic_Salary) {
                this.emp_Basic_Salary = emp_Basic_Salary;
        }

        public String getEmp_Password() {
                return emp_Password;
        }

        public void setEmp_Password(String emp_Password) {
                this.emp_Password = emp_Password;
        }

        @Override
        public String toString() {
                return "Employee{" +
                        "emp_Id='" + emp_Id + '\'' +
                        ", emp_Name='" + emp_Name + '\'' +
                        ", emp_Category='" + emp_Category + '\'' +
                        ", emp_Address='" + emp_Address + '\'' +
                        ", emp_Contact='" + emp_Contact + '\'' +
                        ", emp_Basic_Salary=" + emp_Basic_Salary +
                        ", emp_Password='" + emp_Password + '\'' +
                        ", employeeImageView=" + employeeImageView +
                        '}';
        }

        public String getEmployeeImageView() {
                return employeeImageView;
        }

        public void setEmployeeImageView(String employeeImageView) {
                this.employeeImageView = employeeImageView;
        }
}
