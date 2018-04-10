package uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables.models;

/**
 * Created by sh.khodjabaev on 10.04.2018.
 */

public class StorePeriodResult {

    private String Name,UM,Price,BQ ,BS,PDQ,PD,PCQ,PC,EQ,ES;

    /**
     *
      * @param Name Наименование
     * @param UM ЕдИзм
     * @param Price Цена за ед.
     * @param BQ Остаток на начало периода : Кол-во
     * @param BS Остаток на начало периода : Сумма
     * @param PDQ Приход : Кол-во
     * @param PD Приход : Сумма
     * @param PCQ Расход : Кол-во
     * @param PC Расход : Сумма
     * @param EQ Остаток на конец периода : Кол-во
     * @param ES Остаток на конец периода : Сумма
     */
    public StorePeriodResult(String Name, String UM, String Price, String BQ, String BS, String PDQ, String PD, String PCQ, String PC, String EQ, String ES) {
        this.Name = Name;
        this.UM = UM;
        this.Price = Price;
        this.BQ = BQ;
        this.BS = BS;
        this.PDQ = PDQ;
        this.PD = PD;
        this.PCQ = PCQ;
        this.PC = PC;
        this.EQ = EQ;
        this.ES = ES;
    }

    public String getName() {
        return Name;
    }

    public String getUM() {
        return UM;
    }

    public String getPrice() {
        return Price;
    }

    public String getBQ() {
        return BQ;
    }

    public String getBS() {
        return BS;
    }

    public String getPDQ() {
        return PDQ;
    }

    public String getPD() {
        return PD;
    }

    public String getPCQ() {
        return PCQ;
    }

    public String getPC() {
        return PC;
    }

    public String getEQ() {
        return EQ;
    }

    public String getES() {
        return ES;
    }
}
