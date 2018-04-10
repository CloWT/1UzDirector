package uz.a1uz.a1uzdirector.JsoN;

import org.json.JSONObject;

/**
 * Created by tempuser on 10.01.2018.
 */


public interface IGetJsonResult  {
    /**
     *
     */
    void OnBegin();

    /**
     *
     * @param progress
     */
    void OnProgressUpdate(int progress);

    /**
     *
     * @param jsonStringsArr
     */
    void OnEnd(String[] jsonStringsArr);

    void OnEnd(JSONObject jsonObject);

    /**
     *
     * @param e
     */
    void OnError(Exception e);
}
