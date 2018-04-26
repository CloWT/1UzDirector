package uz.a1uz.a1uzdirector.Activity;

import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uz.a1uz.a1uzdirector.Activity.models.Widget_item_Adapter;
import uz.a1uz.a1uzdirector.Activity.models.Widget_item_model;
import uz.a1uz.a1uzdirector.Helpers.ActionBarCustomizer;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.WidgetsUrlsArr;

import static uz.a1uz.a1uzdirector.constants.URL_cons.*;

public class Main_Activity extends ActionBarCustomizer {
    public static final String GUID="UserGuid";
    public List<Widget_item_model> items;
    public Widget_item_Adapter adapter;
    ProgressBar progressBar;
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
        getConfigForWidgetsFromPreferecnce();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    private void getConfigForWidgetsFromPreferecnce(){
        //Set the values
        SharedPreferences sPref=getPreferences(MODE_PRIVATE);
        Set<String> sets = new HashSet<String>();
        List<String> listOfExistingScores = new ArrayList<>();
        listOfExistingScores.add(""+R.color.bgc1);
        listOfExistingScores.add(""+R.color.bgc2);
        listOfExistingScores.add(""+R.color.bgc3);
        listOfExistingScores.add(""+R.color.bgc4);
        listOfExistingScores.add(""+R.color.bgc5);
        listOfExistingScores.add(""+R.color.bgc6);
        sets.addAll(listOfExistingScores);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putStringSet("WIDGETCOLORS", sets);

        ed.commit();


        Set<String> set = sPref.getStringSet("WIDGETCOLORS", null);

        for (String s:set) {
            System.out.println(s);
        }

    };

}



