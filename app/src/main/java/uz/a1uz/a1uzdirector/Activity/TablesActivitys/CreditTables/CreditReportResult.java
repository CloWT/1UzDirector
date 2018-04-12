package uz.a1uz.a1uzdirector.Activity.TablesActivitys.CreditTables;

/**
 * Created by sh.khodjabaev on 12.04.2018.
 */

public class CreditReportResult {
    private String number;
    private int reportDecriptionID;
    private String contragent,contract,summa, day30,
    day60, day90,day100;

    /**
     * @param number №
     * @param reportDecriptionID ID
     * @param contragent Контрагент
     * @param contract Договор
     * @param summa Сумма
     * @param day30 до 30 дней
     * @param day60 до 60 дней
     * @param day90 до 90 дней
     * @param day100 более 90 дней
     */
    public CreditReportResult(String number, int reportDecriptionID, String contragent, String contract, String summa, String day30, String day60, String day90, String day100) {
        this.number = number;
        this.reportDecriptionID = reportDecriptionID;
        this.contragent = contragent;
        this.contract = contract;
        this.summa = summa;
        this.day30 = day30;
        this.day60 = day60;
        this.day90 = day90;
        this.day100 = day100;
    }

    public String getNumber() {
        return number;
    }

    public int getReportDecriptionID() {
        return reportDecriptionID;
    }

    public String getContragent() {
        return contragent;
    }

    public String getContract() {
        return contract;
    }

    public String getSumma() {
        return summa;
    }

    public String getDay30() {
        return day30;
    }

    public String getDay60() {
        return day60;
    }

    public String getDay90() {
        return day90;
    }

    public String getDay100() {
        return day100;
    }
}
