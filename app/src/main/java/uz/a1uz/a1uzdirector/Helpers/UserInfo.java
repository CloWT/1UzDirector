package uz.a1uz.a1uzdirector.Helpers;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by sh.khodjabaev on 05.03.2018.
 */

public class UserInfo {
    public static final String Url="http://83.69.139.133:5050";//="http://10.0.2.2:8080";
    private static String GUID;
    private static EheaderLang lan=EheaderLang.eRu;

    public static String getGUID() {
        if(GUID==null) GUID=UUID.randomUUID().toString();
        return GUID;
    }

    public static void setGUID(String GUID) {
        UserInfo.GUID = GUID;
    }

    static Locale locale;
    static Configuration config;

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
