package controller;

import db.DbConnection;
import modle.AppointmentDetails;
import modle.Order;
import modle.Report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportController {

    public static ArrayList<AppointmentDetails> getDaliIncomeReportData() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("select date,sum(price) as price from appointment group by date order by price desc").executeQuery();
        ArrayList<AppointmentDetails> temp=new ArrayList();
        while (rst.next()){
            temp.add(new AppointmentDetails(
                    rst.getDouble(2), rst.getDate(1)
            ));
        }
        return temp;
    }

    public static ArrayList<Order> getDalyItemIncome() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT date,sum(total) as total from `order` group by date order by total desc ").executeQuery();
        ArrayList<Order> temp=new ArrayList();
        while (rst.next()) {
            temp.add(new Order(rst.getDouble(2), rst.getDate(1)));
        }
        return temp;
    }

    public static ArrayList<Report> getMonthlyIncomeInService() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT extract(MONTH FROM (date)),sum(total) FROM `order` GROUP BY extract(MONTH FROM (date)) ").executeQuery();
       ArrayList<Report> temp=new ArrayList();
        while (rst.next()){
            int monthNumber=rst.getInt(1);
            Double sumOfTotal= rst.getDouble(2);
            String month=selectMonth(monthNumber);
            temp.add(new Report(
                    month,sumOfTotal
            ));
        }
        return temp;
    }

    private static String selectMonth(int monthNumber) {
        if (monthNumber==1){
            return "January";
        }else if (monthNumber==2){
            return "February";
        }else if (monthNumber==3){
            return "March";
        }else if (monthNumber==4){
            return "April";
        }else if (monthNumber==5){
            return "May";
        }else if (monthNumber==6){
            return "June";
        }else if (monthNumber==7){
            return "July";
        }else if (monthNumber==8){
            return "August";
        }else if (monthNumber==9){
            return "September";
        }else if (monthNumber==10){
            return "October";
        }else if (monthNumber==11){
            return "November";
        }else {
            return "December";
        }


    }

    public static ArrayList<Report> getMonthlyItemIncome() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT extract(MONTH FROM (date)),sum(price) FROM appointment GROUP BY extract(MONTH FROM (date))").executeQuery();
        ArrayList<Report> temp=new ArrayList();
        while (rst.next()) {
            int monthNumber=rst.getInt(1);
            Double sumOfTotal= rst.getDouble(2);
            String month=selectMonth(monthNumber);
            temp.add(new Report(
                    month,sumOfTotal
            ));
        }
        return temp;
    }
}

    /*SELECT extract(MONTH FROM (OrderDate)) ,sum(`order details`.OrderQty),count(`order`.OrderId),sum(`order details`.total)  FROM `order` INNER JOIN\n" +
        "    `order details` ON `order`.OrderId = `order details`.OrderId GROUP BY  extract(MONTH FROM (OrderDate))*/
