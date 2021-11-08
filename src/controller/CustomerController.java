package controller;

import db.DbConnection;
import javafx.scene.control.Alert;
import modle.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CustomerController {
    public static String setApoNo() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT apo_No FROM appointment order by apo_No DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempId=Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "Apo-00"+tempId;
            }else if (tempId<=99){
                return "Apo-0"+tempId;
            }else {
                return "Apo-"+tempId;
            }
        }else {
            return "Apo-001";
        }
    }

    public static boolean createAppointment(String cusNic, double total, double discount, Date date, String apoNo, String serviceId,String barberName) throws SQLException, ClassNotFoundException {
        String emp_Id=getEmployeeId(barberName);
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO appointment VALUES (?,?,?,?,?,?,?)");
        stm.setString(1, apoNo);
        stm.setString(2, cusNic);
        stm.setString(3, serviceId);
        stm.setString(4, emp_Id);
        stm.setDouble(5, total);
        stm.setDouble(6, discount);
        stm.setObject(7, date);
        if (stm.executeUpdate()>0) {
            return true;
        }else {
            return false;
        }
    }

    public static void updateCustomer(String cusNIC, String hairCut) throws SQLException, ClassNotFoundException {
        Customer customer=searchCustomer(cusNIC);
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE customer set cust_Hair_Style=?,cust_Count=? WHERE cust_NIC=?");
        if (hairCut.equals("A")){
            System.out.println(customer.getHairStyle());
            stm.setString(1,customer.getHairStyle());
            stm.setInt(2,Integer.parseInt(customer.getCustomerCount())-1);
            stm.setString(3,cusNIC);
            stm.executeUpdate();
        }else {
            stm.setString(1,hairCut);
            stm.setInt(2,Integer.parseInt(customer.getCustomerCount())+1);
            stm.setString(3,cusNIC);
            stm.executeUpdate();
        }

    }

    private static String getEmployeeId(String barberName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Id FROM employee WHERE emp_Name=?");
        stm.setString(1,barberName);
        ResultSet rst = stm.executeQuery();
        String emp_Id=null;
        if (rst.next()) {
            emp_Id= rst.getString(1);
        }
        return emp_Id;
    }

    public static String getServiceId(String serviceName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT service_Id FROM service WHERE service_Name=?");
        stm.setString(1,serviceName);
        ResultSet rst = stm.executeQuery();
        String serviceId=null;
        if (rst.next()){
            serviceId= rst.getString(1);
        }
        return serviceId;
    }




    public boolean addCustomer(Customer customerDetail) throws SQLException, ClassNotFoundException {

        if (search(customerDetail)) {
            return false;
        }else {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?)");
            stm.setObject(1,customerDetail.getCustomerNic());
            stm.setObject(2,customerDetail.getCustomerName());
            stm.setObject(3,customerDetail.getCustomerAge());
            stm.setObject(4,customerDetail.getHairStyle());
            stm.setObject(5,Integer.parseInt(customerDetail.getCustomerCount()));
            return stm.executeUpdate()>0;

        }

    }

    private boolean search(Customer customerDetail) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customer WHERE cust_NIC=?");
        stm.setString(1,customerDetail.getCustomerNic());
        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return true;
        }
        return false;
    }


    public static Customer searchCustomer(String customerId) throws SQLException, ClassNotFoundException {
        Customer c1=null;
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("select * from customer WHERE cust_NIC=?");
            stm.setObject(1,customerId);
            ResultSet rst = stm.executeQuery();
            if (rst.next()){
                   c1=new Customer(
                          rst.getString(1), rst.getString(2),String.valueOf(rst.getString(3)) , rst.getString(4),String.valueOf(rst.getString(5))
                  );
                   return c1;
            }
            return c1;
    }

    public ArrayList<String> getBarber(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Id FROM attendants where date=?");
        statement.setString(1,date);
        ResultSet rst = statement.executeQuery();
        ArrayList<String> barbers=new ArrayList();
        ArrayList<String> barbersName=new ArrayList();
        while (rst.next()){
            barbers.add(rst.getString(1));
        }

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Name FROM employee WHERE emp_Category=? AND emp_Id=?");
        for (String temp:barbers
             ) {
            stm.setString(1,"Barber");
            stm.setString(2,temp.toUpperCase());
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                barbersName.add(resultSet.getString(1));
            }
        }
        return barbersName;
    }

    public ArrayList<String> getStyles() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT style_Name FROM hair_style").executeQuery();
        ArrayList<String> styles=new ArrayList();

        while (rst.next()){
            styles.add(rst.getString(1));
        }
        return styles;
    }

    public ArrayList<String> getServices() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT service_Name FROM service").executeQuery();
        ArrayList<String> service=new ArrayList();

        while (rst.next()){
                service.add(rst.getString(1));
        }
        return service;
    }


    public static double[] setTotal(String customerNIc, Object styleName) throws SQLException, ClassNotFoundException {
        double stylePrice=0;
        double[] tot={0,0};
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT style_Price FROM hair_style WHERE style_Name=?");
        stm.setObject(1,styleName);
        ResultSet rst = stm.executeQuery();

        if (rst.next()){
            stylePrice=rst.getDouble(1);
        }
        
        tot[0]=stylePrice;
        int[] ageCount=getFrequencyOfAttendanceAndAge(customerNIc);
        
        return isChecked(ageCount,tot);


    }

    public static double[] setFinalTotal(Object newValue, String customerNIC) throws SQLException, ClassNotFoundException {
        double[] totAndDiscount={0,0};

        
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT service_Unite_Price FROM service where service_Name=?");
        stm.setObject(1,newValue);
        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            totAndDiscount[0]= rst.getDouble(1);
        }
        int[] ageCount=getFrequencyOfAttendanceAndAge(customerNIC);
        return isChecked(ageCount,totAndDiscount);

    }


    public static double[] isChecked(int[] ageCount,double[] totAndDiscount) {
        if ((ageCount[0]<12) ||(ageCount[1]>20)){
            totAndDiscount[0]-=50;
            totAndDiscount[1]+=50;
        }
        return totAndDiscount;
    }

    public static int[] getFrequencyOfAttendanceAndAge(String customerNIC) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT cust_Age,cust_Count FROM customer WHERE cust_NIC=?");
        stm.setString(1,customerNIC);
        ResultSet rst = stm.executeQuery();
        int[] age_count=new int[2];
        if (rst.next()){
            age_count[0]=rst.getInt(1);
            age_count[1]=rst.getInt(2);
        }
        return age_count;
    }
}
