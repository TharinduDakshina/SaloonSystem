package controller;

import db.DbConnection;
import modle.AppointmentDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierServiceController {

    public static ArrayList<AppointmentDetails> geAppointmentData(String apoNo) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM appointment WHERE apo_No=?");
        stm.setString(1,apoNo);
        ResultSet rst = stm.executeQuery();
        ArrayList<AppointmentDetails> temp=new ArrayList();
        if (rst.next()){
            temp.add(new AppointmentDetails(
                    rst.getString(1), rst.getString(2), rst.getString(3),
                     rst.getString(4),rst.getDouble(5), rst.getDouble(6), rst.getDate(7)
            ));
        }
        return temp;
    }

    public static boolean deleteAppointment(String custNIC, String apoNo) throws SQLException, ClassNotFoundException {

        CustomerController.updateCustomer(custNIC,"A");

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM appointment WHERE apo_No=?");
        stm.setString(1,apoNo);
        return stm.executeUpdate()>0;

    }

    public static boolean payAmount(String apoNo, String serviceId) throws SQLException, ClassNotFoundException {
       if (serviceId==null){
           serviceId="None";
       }
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO service_details VALUES(?,?)");
        stm.setString(1,serviceId);
        stm.setString(2,apoNo);
        return stm.executeUpdate()>0;
    }

    public static ArrayList<String> loadAppointmentNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT apo_No FROM appointment").executeQuery();
        ArrayList<String> apoNo=new ArrayList();
        while (rst.next()){
            apoNo.add(rst.getString(1));
        }
        return apoNo;
    }

    public static boolean checkAppointmentPayed(Object apoNo) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * from service_details WHERE apo_No=?");
        stm.setObject(1,apoNo);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return true;
        }
        return false;
    }

    public static String getServiceName(String serviceId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT service_Name FROM service WHERE service_Id=?");
        stm.setString(1,serviceId);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return rst.getString(1);
        }
        return "None";
    }

}
