package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import uz.a1uz.a1uzdirector.Activity.components.ButtonWidgetV2;
import uz.a1uz.a1uzdirector.Activity.components.models.Widget_item_Adapter;
import uz.a1uz.a1uzdirector.Activity.components.models.Widget_item_model;
import uz.a1uz.a1uzdirector.Helpers.ActionBarCustomizer;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.WidgetsUrlsArr;

import static uz.a1uz.a1uzdirector.constants.URL_cons.*;

public class Main_Activity extends ActionBarCustomizer {
    public static final String GUID="UserGuid";
    public List<Widget_item_model> items;
    public Widget_item_Adapter adapter;
    public ProgressBar progressBar;
    ListView listView;
    public WidgetsUrlsArr[] urlsArr=
            {
                    new WidgetsUrlsArr(GETBANKBUTTON,ACCOUNTREPORT,BANKREPORT),
                    new WidgetsUrlsArr(GETSTOREBUTTON,STOREREPORT,STOREPERIODREPORT),
                    new WidgetsUrlsArr(GETPROCCEDSBUTTON,PROCCEDREPORT,PROCCEDPERIODREPORT),
                    new WidgetsUrlsArr(GETCREDITBUTOON,CREDITREPORT,CREDITSECONDREPORT),
                    new WidgetsUrlsArr(GETDEBITBUTTON,DEBITREPORT,DEBITSECONDREPORT),
                    new WidgetsUrlsArr(GETRECURRENTCOSTSBUTTON,CURRENTCOSTREPORT,CURRENTCOSTSECONDREPORT)
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        if(UserInfo.getWidgetListItems()!=null){ items=UserInfo.getWidgetListItems();

        }
        else{
            items=new ArrayList<>();
            new ButtonWidgetV2(this, urlsArr);}
        adapter=new Widget_item_Adapter(this,items);
        adapter.notifyDataSetChanged();
        listView= (ListView) findViewById(R.id.custList);
        listView.setDividerHeight(15);
        listView.setAdapter(adapter);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("On: ","Save");
        outState.putString(GUID,UserInfo.getGUID());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        UserInfo.setGUID(savedInstanceState.getString(GUID));
        Log.e("On: ","Get");
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sPref=getPreferences(Context.MODE_PRIVATE);
        Set<String> set = new TreeSet<>();
        List<String> listOfExistingScores = new ArrayList<>();

        SharedPreferences.Editor ed = sPref.edit();
        if(items.size()>0){
        ed.putInt("w_bank",items.get(0).getBackgroundColor());
        ed.putInt("w_store",items.get(1).getBackgroundColor());
        ed.putInt("w_proceeds",items.get(2).getBackgroundColor());
        ed.putInt("w_credit",items.get(3).getBackgroundColor());
        ed.putInt("w_debit",items.get(4).getBackgroundColor());
        ed.putInt("w_currentconsumption",items.get(5).getBackgroundColor());}
        ed.apply();
    }


}



