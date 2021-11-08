package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modle.Employee;
import view.Tm.EmployeeTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeController {
    public static ObservableList<EmployeeTm> loadEmployees() throws SQLException, ClassNotFoundException {
        ObservableList<EmployeeTm> obList= FXCollections.observableArrayList();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employee").executeQuery();
        while (rst.next()){
            obList.add(new EmployeeTm(
                    rst.getString(1), rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5), rst.getDouble(6)
            ));
        }
        return obList;
    }

    public static boolean addEmployee(ArrayList<Employee> employees, String empId) throws SQLException, ClassNotFoundException {
        if (!checkEmployee(empId)) {
            return false;
        }
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO employee VALUES (?,?,?,?,?,?,?)");
        for (Employee temp:employees
             ) {
            stm.setString(1,temp.getEmp_Id());
            stm.setString(2,temp.getEmp_Name());
            stm.setString(3,temp.getEmp_Category());
            stm.setString(4,temp.getEmp_Address());
            stm.setString(5,temp.getEmp_Contact());
            stm.setDouble(6,temp.getEmp_Basic_Salary());
            stm.setString(7,temp.getEmp_Password());
        }
       return stm.executeUpdate()>0;
    }

    public static ArrayList<Employee> searchEmployee(Object empId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employee WHERE emp_Id=?");
        stm.setObject(1,empId);
        ResultSet rst = stm.executeQuery();
        ArrayList<Employee> temp=new ArrayList();
        if (rst.next()) {
            temp.add(new Employee(
                    rst.getString(1), rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5), rst.getDouble(6), rst.getString(7)
            ));
        }
        return temp;
    }

    public static ArrayList<String> loadEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Id FROM employee").executeQuery();
        ArrayList<String> temp=new ArrayList<>();
        while (rst.next()) {
            temp.add(rst.getString(1));
        }
        return temp;
    }

    public static boolean updateEmployee(ArrayList<Employee> employeeDetails) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE employee SET emp_Name=?,emp_Category=?,emp_Address=?,emp_Contact=?,emp_Basic_Salary=?,emp_Password=? WHERE emp_Id=?");
        for (Employee temp:employeeDetails
             ) {
            stm.setString(1,temp.getEmp_Name());
            stm.setString(2,temp.getEmp_Category());
            stm.setString(3,temp.getEmp_Address());
            stm.setString(4,temp.getEmp_Contact());
            stm.setDouble(5,temp.getEmp_Basic_Salary());
            stm.setString(6,temp.getEmp_Password());
            stm.setString(7,temp.getEmp_Id());
            return stm.executeUpdate()>0;
        }
        return false;
    }

    public static boolean deleteEmployee(Object empId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM employee WHERE emp_Id=?");
        stm.setObject(1,empId);
       return stm.executeUpdate()>0;
    }

    private static boolean checkEmployee(String empId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employee WHERE emp_Id=?");
        stm.setString(1,empId);
        if (stm.executeQuery().next()) {
            return false;
        }
        return true;
    }
}
