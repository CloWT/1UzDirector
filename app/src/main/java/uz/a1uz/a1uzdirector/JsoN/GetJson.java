package uz.a1uz.a1uzdirector.JsoN;

/**
 * Created by tempuser on 10.01.2018.
 */

public class GetJson {

    public static void execute(String url, IGetJsonResult logic){
        ParseTask parseTask = new ParseTask(logic);
        parseTask.setUrltext(url);
        parseTask.execute();
    }
}
