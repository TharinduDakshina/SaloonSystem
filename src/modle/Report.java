package modle;

public class Report {
    private String month;
    private double sumOfIncome;

    public Report() {
    }

    public Report(String month, double sumOfIncome) {
        this.setMonth(month);
        this.setSumOfIncome(sumOfIncome);
    }

    public double getSumOfIncome() {
        return sumOfIncome;
    }

    public void setSumOfIncome(double sumOfIncome) {
        this.sumOfIncome = sumOfIncome;
    }

    @Override
    public String toString() {
        return "Report{" +
                "month='" + getMonth() + '\'' +
                ", sumOfIncome=" + sumOfIncome +
                '}';
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
