package uz.a1uz.a1uzdirector.Activity.TablesActivitys.ProceedsTables.models;

/**
 * Created by sh.khodjabaev on 09.04.2018.
 */

public class ProceedsPeriodResult {

    String number, date, contragent, contract, vidType, summa;

    public ProceedsPeriodResult(String number, String date, String contragent, String contract, String vidType, String summa) {
        this.number = number;
        this.date = date;
        this.contragent = contragent;
        this.contract = contract;
        this.vidType = vidType;
        this.summa = summa;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getContragent() {
        return contragent;
    }

    public String getContract() {
        return contract;
    }

    public String getVidType() {
        return vidType;
    }

    public String getSumma() {
        return summa;
    }
}
