package uz.a1uz.a1uzdirector.JsoN;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import uz.a1uz.a1uzdirector.Helpers.UserInfo;

/**
 * Created by tempuser on 10.01.2018.
 */

public class ParseTask extends AsyncTask<Void, Void, String> {
    private Boolean _isError;
    private String urltext;

    private String resultJson = "";
    private IGetJsonResult logicItem;


    public ParseTask(IGetJsonResult logic) {

        logicItem = logic;
    }

    public ParseTask(String urltext) {
        this.urltext = urltext;
    }

    public void setUrltext(String urltext) {
        this.urltext = urltext;
    }
    Exception ee;

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url= new URL(urltext);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Language", UserInfo.getHeaderLang());
            urlConnection.setConnectTimeout(50000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 128);
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();
            urlConnection.disconnect();
            _isError=false;
        } catch (FileNotFoundException e){ee=e;
            _isError=true;
            ee=e;
        }catch (SocketTimeoutException e){
            _isError=true;
            ee=e;
        }
        catch (Exception e) {
            _isError=true;
            ee=e;

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
            else logicItem.OnError(ee);
        } catch (JSONException e) {
            e.printStackTrace();
            logicItem.OnError(e);
        }
    }
}
