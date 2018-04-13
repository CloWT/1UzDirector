package uz.a1uz.a1uzdirector.JsoN;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sh.khodjabaev on 30.03.2018.
 */

public class ParseTaskList extends AsyncTask<String,Integer,String[]> {
    private IGetJsonResult logicItem;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        logicItem.OnBegin();
    }

    public ParseTaskList(IGetJsonResult logic) {
        logicItem = logic;
    }

    @Override
    protected String[] doInBackground(String... getJ) {

        String[] tmp=new String[getJ.length];
        Boolean _isError;
        try {
            for (int i = 0; i < getJ.length; i++) {
                URL url= new URL(getJ[i]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                tmp[i] = buffer.toString();
                _isError =false;

                urlConnection.disconnect();
                publishProgress(i);
            }
        } catch (FileNotFoundException e){
            System.out.println(e.toString());
            _isError =true;
        } catch (Exception e) {
            _isError =true;
            e.printStackTrace();

        }
        return tmp;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values[0]);

        logicItem.OnProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(String[] s) {
        logicItem.OnEnd(s);
        super.onPostExecute(s);
    }
}
