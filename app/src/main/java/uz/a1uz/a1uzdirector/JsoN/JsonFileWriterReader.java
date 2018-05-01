package uz.a1uz.a1uzdirector.JsoN;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by sh.khodjabaev on 01.05.2018.
 */

public class JsonFileWriterReader {
    Context context;
    String fileName;

    public JsonFileWriterReader(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    private boolean mIsHasJsonFile(){
        int start = fileName.indexOf("Get"); //6
        int end =fileName.length();
        char[] dst=new char[end - start];
        fileName.getChars(start, end, dst, 0);
        fileName= String.valueOf(dst);
            File file=new File("/data/data/" + context.getPackageName() + "/" + fileName+".js");
            boolean f=file.exists();

        return file.exists();
    }
    public void mCreateAndSaveFile(Context context, String params, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter("/data/data/" + context.getPackageName() + "/" + params);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mReadJsonData(Context context,String params) {
        try {
            File f = new File("/data/data/" + context.getPackageName() + "/" + params);

            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
