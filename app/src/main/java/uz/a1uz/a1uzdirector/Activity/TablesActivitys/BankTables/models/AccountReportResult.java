package uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.models;

/**
 * Created by sh.khodjabaev on 16.02.2018.
 */

public class AccountReportResult {
    private String name;
    private String BeginPeriodSum;
    private String InSum;
    private String OutSum;
    private String CurrentSum;
    private int ReportDecriptionID;


    public AccountReportResult(String name, String beginPeriodSum, String inSum, String outSum, String currentSum, int reportDecriptionID) {
        this.name = name;
        BeginPeriodSum = beginPeriodSum;
        InSum = inSum;
        OutSum = outSum;
        CurrentSum = currentSum;
        ReportDecriptionID = reportDecriptionID;
    }

    public int getReportDecriptionID() {
        return ReportDecriptionID;
    }

    public void setReportDecriptionID(int reportDecriptionID) {
        ReportDecriptionID = reportDecriptionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginPeriodSum() {
        return BeginPeriodSum;
    }

    public void setBeginPeriodSum(String beginPeriodSum) {
        BeginPeriodSum = beginPeriodSum;
    }

    public String getInSum() {
        return InSum;
    }

    public void setInSum(String inSum) {
        InSum = inSum;
    }

    public String getOutSum() {
        return OutSum;
    }

    public void setOutSum(String outSum) {
        OutSum = outSum;
    }

    public String getCurrentSum() {
        return CurrentSum;
    }

    public void setCurrentSum(String currentSum) {
        CurrentSum = currentSum;
    }
}
