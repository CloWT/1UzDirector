package uz.a1uz.a1uzdirector.JsoN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import uz.a1uz.a1uzdirector.Helpers.UserInfo;

import static uz.a1uz.a1uzdirector.constants.URL_cons.WIDGETS_NAMES;

/**
 * Created by sh.khodjabaev on 30.03.2018.
 */

public class ParseTaskList extends AsyncTask<String,Integer,String[]> {
    private IGetJsonResult logicItem;
    private Context context;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        logicItem.OnBegin();
    }

    public ParseTaskList(Context context,IGetJsonResult logic) {
        this.context=context;
        logicItem = logic;
    }
    private Boolean _isError;
    private Exception exception;
    @Override
    protected String[] doInBackground(String... getJ) {

        String[] tmp=new String[getJ.length];

        try {
            for (int i = 0; i < getJ.length; i++) {
                URL url= new URL(getJ[i]);
                JsonFileWriterReader jsonFileWriterReader=new JsonFileWriterReader(context,WIDGETS_NAMES[i]);
                if(jsonFileWriterReader.mIsHasJsonFile()){
                    tmp[i]=jsonFileWriterReader.result;
                    _isError =false;
                    continue;
                }
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept-Language", UserInfo.getLan());
                urlConnection.setConnectTimeout(20000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 64);
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                tmp[i] = buffer.toString();
                jsonFileWriterReader.mCreateAndSaveFile(tmp[i]);
                _isError =false;

                urlConnection.disconnect();
                publishProgress(i);
            }
        } catch (FileNotFoundException e){
            exception =e;
            System.out.println(e.toString());
            _isError =true;
        } catch (Exception e) {
            exception =e;
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
        super.onPostExecute(s);
        if(!_isError){
            logicItem.OnEnd(s);
        }
        else logicItem.OnError(exception);
    }
}
