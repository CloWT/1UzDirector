package uz.a1uz.a1uzdirector;

/**
 * Created by sh.khodjabaev on 03.04.2018.
 */

public enum EdatePeriod {
    ThisDay("Сегодня")
    ,LastDay("Вчера"),ThisWeek("Текущая неделя"),LastWeek("Прошлая неделя"),ThisMonth("Текущий месяц"),LastMonth("Прошлый месяц"),BeginYear("С начала года"),CustomDate("Указат диапазон");

    private String stringVal;
    EdatePeriod(String stringVal) {
        this.stringVal=stringVal;
    }

    public String getStringVal() {
        return stringVal;
    }

    @Override
    public String toString() {
        return getStringVal();
    }
}
