package uz.a1uz.a1uzdirector.JsoN;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sh.khodjabaev on 01.05.2018.
 */

public class JsonFileWriterReader {
    String result;
    private File file;

    /***
     *
     * @param context
     * @param filename
     */
    public JsonFileWriterReader(Context context, String filename) {
        String fileName = filename;
        file=new File(String.format("/data/data/%s/%s.js", context.getPackageName(), fileName));

    }

    public boolean mIsHasJsonFile(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.HOUR,-1);

            boolean f=file.exists();
            if(f){
                if(calendar.getTimeInMillis()<file.lastModified()){
                    result=mReadJsonData(file);
                    return true;
                }
                else return false;
            }

        return false;
    }
    public void mCreateAndSaveFile(String mJsonResponse) {
        try {
            FileWriter file = new FileWriter(this.file);

            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String mReadJsonData(File file) {
        String mResponse= "";
        try {

            FileInputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            mResponse = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mResponse;
    }
    public void mDeleteFile(){
        if(file.exists()) file.delete();

    }


    private char[] mTransformFielName(String s) {
        int start = s.indexOf("widget")+"widget/".length();
        int end = start+8;
        char[] dst=new char[end - start];
        s.getChars(start, end, dst, 0);
        String ss=String.valueOf(dst);
        System.out.println(ss);
        return dst;
    }
}
