package uz.a1uz.a1uzdirector.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import uz.a1uz.a1uzdirector.Activity.models.Widget_item_Adapter;
import uz.a1uz.a1uzdirector.Activity.models.Widget_item_model;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.WidgetsUrlsArr;

import static uz.a1uz.a1uzdirector.constants.URL_cons.*;

public class Main_Activity extends AppCompatActivity  {
public List<Widget_item_model> items;
    public Widget_item_Adapter adapter;


    public WidgetsUrlsArr[] urlsArr=
            {
                    new WidgetsUrlsArr(GETBANKBUTTON,ACCOUNTREPORT,BANKREPORT),
                    new WidgetsUrlsArr(GETSTOREBUTTON,STOREREPORT,STOREPERIODREPORT),
                    new WidgetsUrlsArr(GETPROCCEDSBUTTON,PROCCEDREPORT,PROCCEDPERIODREPORT),
                    new WidgetsUrlsArr(GETCREDITBUTOON,CREDITREPORT,CREDITSECONDREPORT),
                    new WidgetsUrlsArr(GETDEBITBUTTON,DEBITREPORT,DEBITSECONDREPORT),
                    new WidgetsUrlsArr(GETRECURRENTCOSTSBUTTON,CURRENTCOSTREPORT,CURRENTCOSTSECONDREPORT)
            };
    ProgressBar progressBar;


    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        progressBar=(ProgressBar)findViewById(R.id.progres);


        items=new ArrayList<>();
        adapter=new Widget_item_Adapter(this,items);

        listView= (ListView) findViewById(R.id.custList);
        listView.setAdapter(adapter);
        new ButtonWidgetV2(this, urlsArr);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}



