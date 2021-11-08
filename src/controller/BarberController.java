package controller;

import db.DbConnection;
import modle.Employee;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class BarberController {



    public static ArrayList<String> loadEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Id FROM employee").executeQuery();
        ArrayList<String> temp=new ArrayList();
        while (rst.next()){
            temp.add(rst.getString(1));
        }
        return temp;
    }

    public static String getEmpName(Object empId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Name FROM employee WHERE emp_Id=?");
        stm.setObject(1,empId);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public static boolean markAttendances(String cust_Id, Date date, String present) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection()
                .prepareStatement("INSERT INTO attendants(emp_Id, date,mark) VALUES (?,?,?)");
        stm.setString(1,cust_Id);
        stm.setObject(2,date);
        stm.setString(3,present);
        return stm.executeUpdate()>0;
    }

}
