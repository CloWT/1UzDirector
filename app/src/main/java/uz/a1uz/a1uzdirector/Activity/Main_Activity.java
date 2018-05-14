package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import uz.a1uz.a1uzdirector.Activity.components.ButtonWidgetV2;
import uz.a1uz.a1uzdirector.Activity.components.models.Widget_item_Adapter;
import uz.a1uz.a1uzdirector.Activity.components.models.Widget_item_model;
import uz.a1uz.a1uzdirector.Helpers.CustomActivity;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.WidgetsUrlsArr;
import uz.a1uz.a1uzdirector.constants.URL_cons;

import static uz.a1uz.a1uzdirector.constants.URL_cons.ACCOUNTREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.BANKREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.CREDITREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.CREDITSECONDREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.CURRENTCOSTREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.CURRENTCOSTSECONDREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.DEBITREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.DEBITSECONDREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETBANKBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETCREDITBUTOON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETDEBITBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETPROCCEDSBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETRECURRENTCOSTSBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETSTOREBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.PROCCEDPERIODREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.PROCCEDREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.STOREPERIODREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.STOREREPORT;

public class Main_Activity extends CustomActivity {
    public static final String GUID = "UserGuid";
    public List<Widget_item_model> items;
    public Widget_item_Adapter adapter;
    public ProgressBar progressBar;
    public WidgetsUrlsArr[] urlsArr =
            {
                    new WidgetsUrlsArr(GETBANKBUTTON, ACCOUNTREPORT, BANKREPORT),
                    new WidgetsUrlsArr(GETSTOREBUTTON, STOREREPORT, STOREPERIODREPORT),
                    new WidgetsUrlsArr(GETPROCCEDSBUTTON, PROCCEDREPORT, PROCCEDPERIODREPORT),
                    new WidgetsUrlsArr(GETCREDITBUTOON, CREDITREPORT, CREDITSECONDREPORT),
                    new WidgetsUrlsArr(GETDEBITBUTTON, DEBITREPORT, DEBITSECONDREPORT),
                    new WidgetsUrlsArr(GETRECURRENTCOSTSBUTTON, CURRENTCOSTREPORT, CURRENTCOSTSECONDREPORT)
            };
    ListView listView;
     TextView orgName;
    TextView limitDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progres);
        orgName =(TextView)findViewById(R.id._organiz);
        limitDate =(TextView)findViewById(R.id._limitDate);


        if (UserInfo.getWidgetListItems() != null) {
            items = UserInfo.getWidgetListItems();
            orgName.setText(UserInfo.getOrganizationName());
            limitDate.setText(UserInfo.getLimitDate());

        } else {

            items = new ArrayList<>();
            GetJson.execute(UrlHepler.Combine(URL_cons.GETWEBUSERINFO, UserInfo.getGUID()), new IGetJsonResult() {
                @Override
                public void OnBegin() {

                }

                @Override
                public void OnProgressUpdate(int progress) {

                }

                @Override
                public void OnEnd(String[] jsonStringsArr) {

                }

                @Override
                public void OnEnd(JSONObject jsonObject) {
                    try {
                        JSONObject jc = (JSONObject) jsonObject.get("GetWebUserInfoResult");
                        UserInfo.setLimitDate(jc.getString("LimitDate"));
                        UserInfo.setOrganizationName(jc.getString("OrganizationName"));
                        orgName.setText(UserInfo.getOrganizationName());
                        limitDate.setText(UserInfo.getLimitDate());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void OnError(Exception e) {

                }
            });

            new ButtonWidgetV2(this, urlsArr);
        }

        adapter = new Widget_item_Adapter(this, items);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.custList);

        listView.setDividerHeight(15);
        listView.setAdapter(adapter);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("On: ", "Save");
        outState.putString(GUID, UserInfo.getGUID());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        UserInfo.setGUID(savedInstanceState.getString(GUID));
        Log.e("On: ", "Get");
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sPref = getPreferences(Context.MODE_PRIVATE);
        Set<String> set = new TreeSet<>();
        List<String> listOfExistingScores = new ArrayList<>();

        SharedPreferences.Editor ed = sPref.edit();
        if (items.size() > 0) {
            ed.putInt("w_bank", items.get(0).getBackgroundColor());
            ed.putInt("w_store", items.get(1).getBackgroundColor());
            ed.putInt("w_proceeds", items.get(2).getBackgroundColor());
            ed.putInt("w_credit", items.get(3).getBackgroundColor());
            ed.putInt("w_debit", items.get(4).getBackgroundColor());
            ed.putInt("w_currentconsumption", items.get(5).getBackgroundColor());
        }
        ed.apply();
    }


}



