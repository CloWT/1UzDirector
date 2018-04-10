package uz.a1uz.a1uzdirector.Helpers;

import java.util.UUID;

/**
 * Created by sh.khodjabaev on 05.03.2018.
 */

public class UserInfo {
    public static final String Url="http://83.69.139.133:5050";
    private static String GUID;

    public static String getGUID() {
        if(GUID==null) GUID=UUID.randomUUID().toString();
        return GUID;
    }
}
