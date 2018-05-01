package uz.a1uz.a1uzdirector.Enums;

import android.content.Context;

import java.util.Locale;

/**
 * Created by sh.khodjabaev on 03.04.2018.
 */

public enum EdatePeriod {
    ThisDay("Сегодня","Бугун"),
    LastDay("Вчера", "Кеча"),
    ThisWeek("Текущая неделя", "Жорий ҳафта"),
    LastWeek("Прошлая неделя", "Ўтган ҳафта"),
    ThisMonth("Текущий месяц", "Жорий ой"),
    LastMonth("Прошлый месяц", "Ўтган ой"),
    BeginYear("С начала года", "Йил бошидан"),
    CustomDate("Указат диапазон", "Даврни белгилаш");

    private String stringVal;
    private String secondValue;
    static Locale locale;
    EdatePeriod(String stringVal, String secondValue) {
        this.stringVal=stringVal;
        this.secondValue=secondValue;
    }

    public String getStringVal() {
       Locale l=Locale.getDefault();
       String country=l.getDisplayLanguage();
        switch (l.toString()){
            case "uz":return secondValue;
            default:return stringVal;

        }

    }

    @Override
    public String toString() {
        return getStringVal();
    }

    public static EdatePeriod[] values(Locale aDefault) {
        locale=aDefault;
        return values();

    }
}
