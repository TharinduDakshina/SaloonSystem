package controller;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import modle.AppointmentDetails;
import modle.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class DalyIncomeReportController {


    public   LineChart<?,?> dalyIncome;

    public void initialize(){
        XYChart.Series series=new XYChart.Series();
        XYChart.Series series2=new XYChart.Series();
        series.setName("Service");
        series2.setName("By Item");

        try {

            loadData(series);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            loadItemIncomeData(series2);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        dalyIncome.getData().addAll(series,series2);
    }

    private void loadItemIncomeData(XYChart.Series series2) throws SQLException, ClassNotFoundException {
        ArrayList<Order> dalyItemIncomeData = ReportController.getDalyItemIncome();
        System.out.println(dalyItemIncomeData);
        for (Order temp:dalyItemIncomeData
        ) {
            series2.getData().add(new XYChart.Data(temp.getDate().toString(),temp.getTotal()));
        }
    }

    private void loadData(XYChart.Series series) throws SQLException, ClassNotFoundException {
        ArrayList<AppointmentDetails> dalyIncomeData = ReportController.getDaliIncomeReportData();
        System.out.println(dalyIncomeData);
        for (AppointmentDetails temp:dalyIncomeData
             ) {
            series.getData().add(new XYChart.Data(temp.getDate().toString(),temp.getPrice()));
        }
    }
}
