package controller;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import modle.Report;

import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyIncomeReportsController {

    public LineChart<?,?> lineChartMonthly;

    public void initialize(){
        XYChart.Series series=new XYChart.Series();
        XYChart.Series series2=new XYChart.Series();
        series2.setName("Service");
        series.setName("Item");

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
        lineChartMonthly.getData().addAll(series,series2);
    }

    private void loadItemIncomeData(XYChart.Series series2) throws SQLException, ClassNotFoundException {
        ArrayList<Report> monthlyItemIncome= ReportController.getMonthlyItemIncome();
        for (Report temp:monthlyItemIncome
        ) {
            series2.getData().add(new XYChart.Data(temp.getMonth(),temp.getSumOfIncome()));
        }
    }

    private void loadData(XYChart.Series series) throws SQLException, ClassNotFoundException {
        ArrayList<Report> monthlyServiceIncome= ReportController.getMonthlyIncomeInService();
        for (Report temp:monthlyServiceIncome
             ) {
            series.getData().add(new XYChart.Data(temp.getMonth(),temp.getSumOfIncome()));
        }
    }
}
