package uz.a1uz.a1uzdirector.Helpers;

import android.content.Context;
import android.content.res.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import uz.a1uz.a1uzdirector.Activity.components.models.Widget_item_model;

/**
 * Created by sh.khodjabaev on 05.03.2018.
 */

public class UserInfo {
    public static final String Url="http://83.69.139.133:5050";//="http://10.0.2.2:8080";
    private static String GUID;
    private static EheaderLang lan=EheaderLang.eRu;
    private static List<Widget_item_model> widgetListItems;
    private static String INN;
    static Locale locale;
    static Configuration config;


    public static String getGUID() {
        if(GUID==null) GUID=UUID.randomUUID().toString();
        return GUID;
    }

    public static void setGUID(String GUID) {
        UserInfo.GUID = GUID;
    }



    public static String getLan() {
        return String.valueOf(lan);
    }
    public static EheaderLang getLanE() {
        return lan;
    }

    public static void setLan(EheaderLang lan, Context context) {
        UserInfo.lan = lan;
        locale = new Locale(lan.toString());
        Locale.setDefault(locale);
        config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
    public static void setLan(String lan,Context context) {
        for (EheaderLang e :EheaderLang.values()) {
            if(e.getStringVal().equals(lan)) UserInfo.setLan(e,context);
        }

    }

    public static void setWidgetListItems(List<Widget_item_model> widgetListItems) {
        UserInfo.widgetListItems = widgetListItems;
    }

    public static List<Widget_item_model> getWidgetListItems() {
        return widgetListItems;
    }

    public static void setINN(String INN) {
        UserInfo.INN = INN;
    }

    public static String getINN() {
        return INN;
    }

    
    public enum EheaderLang {
        eRu("ru-RU"),eUz("uz"),eUz_Cyrl("uz-Cyrl-UZ");


        private String stringVal;
        EheaderLang(String stringVal) {
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

}
