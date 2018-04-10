package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;

import org.json.JSONObject;

import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.GetJsonResult;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;

/**
 * Created by sh.khodjabaev on 12.03.2018.
 */

public class ButtonWidget extends GetJsonResult {
    int backgroundColor;
    public ButtonWidget(Context mainActivity, String getbankbutton, int bgc) {
        super(mainActivity);
        String url= UrlHepler.Combine(getbankbutton, UserInfo.getGUID());
        GetJson.execute(url,this);
        backgroundColor=bgc;
    }

    @Override
    public void OnBegin() {
        System.out.println("Begin");
    }

    @Override
    public void OnProgressUpdate(int progress) {

    }

    @Override
    public void OnEnd(String[] jsonStringsArr) {

    }

    @Override
    public void OnEnd(JSONObject jsonObject) {

    }


    @Override
    public void OnError(Exception e) {
        System.out.println("Errorr");
    }

    /**
     *
     * @param jsonObject
     */
//    @Override
//    public void OnEnd(JSONObject jsonObject) {
//        try {
//            System.out.println("End");
//            JSONObject js=(JSONObject) jsonObject.get("GetValueResult");
//            Boolean isAuthorization=js.getBoolean("IsAuthorization");
//            Boolean isException=js.getBoolean("IsException");
//            Boolean isLicensed=js.getBoolean("IsLicensed");
//            Boolean isOk=js.getBoolean("IsOk");
//            String message=js.getString("Message");
//            if(!isOk) {
//                Toast.makeText(context, "Message: "+Math.random()*150, Toast.LENGTH_LONG).show();
//                Widget_item_model model=new Widget_item_model(null,
//                        "",
//                        null,"",0,message,"",backgroundColor);
//                Main_Activity activity=(Main_Activity) context;
//                activity.items.add(model);
//                activity.adapter.notifyDataSetChanged();
//                return;
//            }
//
//            ArrayAdapter<WidgetDropDownItem> BottomLeftItems=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
//            if(js.getJSONArray("ButtonBottomLeftItems")!=null){
//                JSONArray bbli= js.getJSONArray("ButtonBottomLeftItems");
//
//                for (int i = 0; i < bbli.length(); i++) {
//                    JSONObject dd=bbli.getJSONObject(i);
//                    BottomLeftItems.add(new WidgetDropDownItem(dd.getInt("Id"),
//                            dd.getString("ButtonMiddleText"),
//                            dd.getDouble("ButtonMiddle"),
//                            dd.getString("Name")));
//                }
//                BottomLeftItems.notifyDataSetChanged();
//            }
//            ArrayAdapter<WidgetDropDownItem> BottomRightItems=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
//            String BottomLEftTex=js.getString("ButtonBottomLeftText");
//            if(js.getJSONArray("ButtonBottomRightItems")!=null){
//                JSONArray bbri= js.getJSONArray("ButtonBottomRightItems");//ButtonBottomRightItems
//
//                for (int i = 0; i < bbri.length(); i++) {
//                    JSONObject dd=bbri.getJSONObject(i);
//                    BottomRightItems.add(new WidgetDropDownItem(dd.getInt("Id"),
//                            dd.getString("ButtonMiddleText"),
//                            dd.getDouble("ButtonMiddle"),
//                            dd.getString("Name")));
//                }
//                BottomRightItems.notifyDataSetChanged();
//            }
//
//            String BottomRightText=js.getString("ButtonBottomRightText");
//            double Middle=js.getDouble("ButtonMiddle");
//            String MiddleText=js.getString("ButtonMiddleText");
//            String TopText=js.getString("ButtonTopText");
//
//
//            Widget_item_model model=new Widget_item_model(BottomLeftItems,
//                    BottomLEftTex,
//                    BottomRightItems,BottomRightText,Middle,MiddleText,TopText,backgroundColor);
//           Main_Activity activity=(Main_Activity) context;
//           activity.items.add(model);
//            activity.adapter.notifyDataSetChanged();
//            System.out.println("Donee");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
