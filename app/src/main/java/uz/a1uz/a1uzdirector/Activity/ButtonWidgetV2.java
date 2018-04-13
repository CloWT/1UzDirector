package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    int leng;
    public ButtonWidgetV2(Context context, WidgetsUrlsArr[] widgetsUrlsArrs) {
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
        ParseTaskList parseTaskList=new ParseTaskList(this);
        parseTaskList.execute(buttonUrls);

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

            Widget_item_model model=new Widget_item_model(dropDownLeft,BottomLEftTex,dropDownRight,BottomRightText,Middle,MiddleText,TopText,backgroundColor,reportUrl,secondReportUrl);

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
