package uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.models;

/**
 * Created by sh.khodjabaev on 03.04.2018.
 */

public class BankPeriodResult {
    String detail;
    String inSum;
    String outSum;

    public BankPeriodResult(String detail, String inSum, String outSum) {
        this.detail = detail;
        this.inSum = inSum;
        this.outSum = outSum;
    }

    public String getDetail() {
        return detail;
    }

    public String getInSum() {
        return inSum;
    }

    public String getOutSum() {
        return outSum;
    }
}
