package uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables.models;

/**
 * Created by sh.khodjabaev on 10.04.2018.
 */

public class StorePeriodResult {

    private String Name, UM, Price, BQ, BS, PDQ, PD, PCQ, PC, EQ, ES;

    /**
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
        this.Name = Name.equals("null") ? "  " : Name;
        this.UM = UM.equals("null") ? "  " : UM;
        this.Price = Price.equals("null") ? "  " : Price;
        this.BQ = BQ.equals("null") ? "  " : BQ;
        this.BS = BS.equals("null") ? "  " : BS;
        this.PDQ = PDQ.equals("null") ? "  " : PDQ;
        this.PD = PD.equals("null") ? "  " : PD;
        this.PCQ = PCQ.equals("null") ? "  " : PCQ;
        this.PC = PC.equals("null") ? "  " : PC;
        this.EQ = EQ.equals("null") ? "  " : EQ;
        this.ES = ES.equals("null") ? "  " : ES;
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
