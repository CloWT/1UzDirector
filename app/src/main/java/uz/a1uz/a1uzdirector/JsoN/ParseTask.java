package uz.a1uz.a1uzdirector.JsoN;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by tempuser on 10.01.2018.
 */

public class ParseTask extends AsyncTask<Void, Void, String> {
    private Boolean _isError;
    String urltext;

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";
    IGetJsonResult logicItem;


    public ParseTask(IGetJsonResult logic) {

        logicItem = logic;
    }

    public ParseTask(String urltext) {
        this.urltext = urltext;
    }

    public void setUrltext(String urltext) {
        this.urltext = urltext;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url= new URL(urltext);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            System.out.println(url);
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"), 8);
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();
            _isError=false;
        } catch (FileNotFoundException e){
            System.out.println(e.toString());
            _isError=true;
        }catch (SocketTimeoutException e){
            _isError=true;
            logicItem.OnError(e);
        }
        catch (Exception e) {
            _isError=true;
            e.printStackTrace();
            logicItem.OnError(e);
        }

        return resultJson;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        logicItem.OnBegin();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            if(!_isError){
                logicItem.OnEnd(new JSONObject(s));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            logicItem.OnError(e);
        }
    }
}
