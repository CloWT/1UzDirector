package uz.a1uz.a1uzdirector.Helpers;

/**
 * Created by sh.khodjabaev on 12.03.2018.
 */

public class UrlHepler {
    public static String Combine(String... urls){
        StringBuilder result = new StringBuilder(urls[0]);
        for (int i = 1; i <urls.length ; i++) {
            result.append("/").append(urls[i]);
        }

        return result.toString();
    }
}
