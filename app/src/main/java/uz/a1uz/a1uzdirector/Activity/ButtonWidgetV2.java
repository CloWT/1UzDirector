package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uz.a1uz.a1uzdirector.Activity.models.WidgetDropDownItem;
import uz.a1uz.a1uzdirector.Activity.models.Widget_item_model;
import uz.a1uz.a1uzdirector.JsoN.GetJsonResult;
import uz.a1uz.a1uzdirector.JsoN.ParseTaskList;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.WidgetsUrlsArr;

/**
 * Created by sh.khodjabaev on 12.03.2018.
 */

public class ButtonWidgetV2 extends GetJsonResult {
    private int[] bcgColor={R.color.bgc1,R.color.bgc2,R.color.bgc3,R.color.bgc4,R.color.bgc5,R.color.bgc6};
    private String[] buttonUrls;
    private String[] reportUrls;
    private String[] secondReportUrls;
    private Main_Activity activity;
    private int leng;
    ButtonWidgetV2(Context context, WidgetsUrlsArr[] widgetsUrlsArrs) {
        super(context);
        activity=(Main_Activity) context;
        leng=widgetsUrlsArrs.length;
        buttonUrls=new String[leng];
        reportUrls=new String[leng];
        secondReportUrls=new String[leng];
        for (int i = 0; i <leng ; i++) {
            buttonUrls[i]=UrlHepler.Combine(widgetsUrlsArrs[i].getUrlButoon(),UserInfo.getGUID());
            reportUrls[i]=widgetsUrlsArrs[i].getUrlReport();
            secondReportUrls[i]=widgetsUrlsArrs[i].getUrlSecondReport();
        }
        addColorFromSharedPrefrence();
        ParseTaskList parseTaskList=new ParseTaskList(context,this);
        parseTaskList.execute(buttonUrls);

    }
    private void addColorFromSharedPrefrence(){
        int counter=0;
        SharedPreferences sPref=activity.getPreferences(Context.MODE_PRIVATE);

        if(sPref.getInt("w_bank",-1)<0) {

            SharedPreferences.Editor ed = sPref.edit();
            ed.putInt("w_bank",bcgColor[0]);
            ed.putInt("w_store",bcgColor[1]);
            ed.putInt("w_proceeds",bcgColor[2]);
            ed.putInt("w_credit",bcgColor[3]);
            ed.putInt("w_debit",bcgColor[4]);
            ed.putInt("w_currentconsumption",bcgColor[5]);
            ed.apply();
        }
        else {
            bcgColor=new int[6];
            bcgColor[0]=sPref.getInt("w_bank",R.color.bgc1);
            bcgColor[1]=sPref.getInt("w_store",R.color.bgc2);
            bcgColor[2]=sPref.getInt("w_proceeds",R.color.bgc3);
            bcgColor[3]=sPref.getInt("w_credit",R.color.bgc4);
            bcgColor[4]=sPref.getInt("w_debit",R.color.bgc5);
            bcgColor[5]=sPref.getInt("w_currentconsumption",R.color.bgc6);

        }
    }

    @Override
    public void OnBegin() {
        activity.progressBar.setVisibility(View.VISIBLE);
        activity.progressBar.setMax(leng);
    }
    @Override
    public void OnProgressUpdate(int progress) {




    }
    @Override
    public void OnEnd(String[] jsonStringsArr) {
        int cnt=0;
        activity.progressBar.setVisibility(View.GONE);
        for (int i = 0; i < jsonStringsArr.length; i++) {
            try {

                StringToJson(jsonStringsArr[i],bcgColor[cnt],reportUrls[i],secondReportUrls[i]);
                cnt=bcgColor.length<i?0:cnt+1;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        UserInfo.setWidgetListItems(activity.items);

    }
    @Override
    public void OnEnd(JSONObject jsonObject) {

    }
    @Override
    public void OnError(Exception e) {

    }
    public void StringToJson(String str, int backgroundColor, String reportUrl, String secondReportUrl) throws JSONException {
        JSONObject js=(JSONObject) new JSONObject(str).get("GetValueResult");
        Boolean isOk=js.getBoolean("IsOk");
        String message=js.getString("Message");
        Boolean isAuthorization=js.getBoolean("IsAuthorization");
        Boolean isException=js.getBoolean("IsException");
        Boolean isLicensed=js.getBoolean("IsLicensed");
        String BottomRightText=js.getString("ButtonBottomRightText");
        double Middle=js.getDouble("ButtonMiddle");
        String MiddleText=js.getString("ButtonMiddleText");
        String TopText=js.getString("ButtonTopText");
        String BottomLEftTex=js.getString("ButtonBottomLeftText");
        WidgetDropDownItem[] dropDownLeft=new WidgetDropDownItem[0];
        WidgetDropDownItem[] dropDownRight = new WidgetDropDownItem[0];
        Log.e("Message:", message);
        if(!isOk) {

            Widget_item_model model=new Widget_item_model(dropDownLeft,"",
                    dropDownRight,"",Middle,MiddleText,"",backgroundColor,reportUrl,secondReportUrl);

            activity.items.add(model);
            activity.adapter.notifyDataSetChanged();
            return;
        }
        /**
         *
         */
        if(js.getJSONArray("ButtonBottomLeftItems")!=null){
            JSONArray bbli= js.getJSONArray("ButtonBottomLeftItems");
            dropDownLeft=new WidgetDropDownItem[bbli.length()];
            for (int i = 0; i < bbli.length(); i++) {
                JSONObject dd=bbli.getJSONObject(i);
                dropDownLeft[i]=new WidgetDropDownItem(dd.getInt("Id"),
                        dd.getString("ButtonMiddleText"),
                        dd.getDouble("ButtonMiddle"),
                        dd.getString("Name"));
            }
            //BottomLeftItems.notifyDataSetChanged();
        }
        ArrayAdapter<WidgetDropDownItem> BottomLeftItems=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,dropDownLeft);
        BottomLeftItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BottomLeftItems.notifyDataSetChanged();
        /**
         *
         */


        if(js.getJSONArray("ButtonBottomRightItems")!=null){
            JSONArray bbri= js.getJSONArray("ButtonBottomRightItems");//ButtonBottomRightItems
            dropDownRight=new WidgetDropDownItem[bbri.length()];
            for (int i = 0; i < bbri.length(); i++) {
                JSONObject dd=bbri.getJSONObject(i);
                dropDownRight[i]=new WidgetDropDownItem(dd.getInt("Id"),
                        dd.getString("ButtonMiddleText"),
                        dd.getDouble("ButtonMiddle"),
                        dd.getString("Name"));
            }
        }
        ArrayAdapter<WidgetDropDownItem> BottomRightItems=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,dropDownRight);
        BottomRightItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BottomRightItems.notifyDataSetChanged();


        Widget_item_model model=new Widget_item_model(dropDownLeft,BottomLEftTex,dropDownRight,BottomRightText,Middle,MiddleText,TopText,backgroundColor,reportUrl,secondReportUrl);
         activity=(Main_Activity) context;
        activity.items.add(model);
        activity.adapter.notifyDataSetChanged();
    }
}
