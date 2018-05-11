package uz.a1uz.a1uzdirector.JsoN;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by sh.khodjabaev on 01.05.2018.
 */

public class JsonFileWriterReader {
    String result;
    private File file;
    Calendar calendar;

    /***
     *
     * @param context
     * @param filename
     */
    public JsonFileWriterReader(Context context, String filename) {
        String fileName = filename;
        calendar=Calendar.getInstance();
        file=new File(String.format("/data/data/%s/%s.js", context.getPackageName(), fileName));

    }

    public boolean mIsHasJsonFile(){



            boolean f=file.exists();
            if(f){
                if(isFileOutDate()){
                    result=mReadJsonData(file);
                    return true;
                }
                else return false;
            }

        return false;
    }

    private boolean isFileOutDate() {
        calendar.add(Calendar.MINUTE,-60);
        return calendar.getTimeInMillis()<file.lastModified();
    }

    public void mCreateAndSaveFile(String textForSave) {
        try {
            FileWriter file = new FileWriter(this.file);

            file.write(textForSave);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String mReadJsonData(File file) {
        try {

            FileInputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void mDeleteFile(){
        if(file.exists()){
            file.delete();
        }

    }

//old version code
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
