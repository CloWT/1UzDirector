package uz.a1uz.a1uzdirector.Helpers;

import java.util.UUID;

/**
 * Created by sh.khodjabaev on 05.03.2018.
 */

public class UserInfo {
    public static final String Url="http://83.69.139.133:5050";
    private static String GUID;
    private static EheaderLang lan=EheaderLang.eRu;

    public static String getGUID() {
        if(GUID==null) GUID=UUID.randomUUID().toString();
        return GUID;
    }

    public static String getLan() {
        return String.valueOf(lan);
    }

    public static void setLan(EheaderLang lan) {
        UserInfo.lan = lan;
    }
    public static void setLan(String lan) {
        for (EheaderLang e :EheaderLang.values()) {
            if(e.getStringVal().equals(lan)) UserInfo.lan=e;
        }

    }

    public enum EheaderLang {
        eRu("ru-RU"),eUz("uz-Cyrl-UZ");


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
