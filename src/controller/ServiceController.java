package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modle.Item;
import modle.Service;
import view.Tm.CustomerTm;
import view.Tm.ServiceTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceController {

    public static ObservableList<ServiceTm> loadServices() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM service").executeQuery();
        ObservableList<ServiceTm> temp= FXCollections.observableArrayList();
        while (rst.next()) {
            temp.add(new ServiceTm(
                    rst.getString(1), rst.getString(2), rst.getDouble(3)
            ));
        }
        return temp;
    }

    public static boolean addService(ArrayList<Service> servicesData, String serviceId) throws SQLException, ClassNotFoundException {
        if (!checkService(serviceId)) {
            return false;
        }
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO service VALUES (?,?,?)");
        for (Service temp:servicesData
             ) {
            stm.setString(1, temp.getServiceId());
            stm.setString(2, temp.getServiceName());
            stm.setDouble(3,temp.getServicePrice());
        }
        return stm.executeUpdate() > 0;
    }

    private static boolean checkService(String serviceId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM service WHERE service_Id=?");
        stm.setString(1,serviceId);
        if (stm.executeQuery().next()) {
            return false;
        }
        return true;
    }

    public static ArrayList<String> loadServiceIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT service_Id FROM service").executeQuery();
        ArrayList<String> temp=new ArrayList<>();
        while (rst.next()) {
            temp.add(rst.getString(1));
        }
        return temp;
    }

    public static ArrayList<Service> searchService(Object serviceId) throws SQLException, ClassNotFoundException {
        PreparedStatement srm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM service WHERE service_Id=?");
        srm.setObject(1,serviceId);
        ResultSet rst = srm.executeQuery();
        ArrayList<Service> temp=new ArrayList<>();
        while (rst.next()) {
            temp.add(new Service(
                    rst.getString(1), rst.getString(2), rst.getDouble(3)
            ));
        }
        return temp;
    }

    public static boolean updateService(ArrayList<Service> serviceData) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE service SET service_Id=?,service_Name=?,service_Unite_Price=? WHERE service_Id=?");
        for (Service temp:serviceData
        ) {
            stm.setString(1,temp.getServiceId());
            stm.setString(2,temp.getServiceName());
            stm.setDouble(3,temp.getServicePrice());
            stm.setString(4,temp.getServiceId());

            return stm.executeUpdate()>0;
        }
        return false;
    }

    public static boolean deleteService(Object serviceId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM service WHERE service_Id=?");
        stm.setObject(1,serviceId);
        return stm.executeUpdate()>0;
    }

    public static ObservableList<CustomerTm> loadCustomerTable() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> observableList= FXCollections.observableArrayList();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customer").executeQuery();
        while (rst.next()) {
            observableList.add(new CustomerTm(
                    rst.getString(1), rst.getString(2), rst.getInt(3), rst.getString(4), rst.getInt(5)
            ));
        }
        return observableList;
    }
}
