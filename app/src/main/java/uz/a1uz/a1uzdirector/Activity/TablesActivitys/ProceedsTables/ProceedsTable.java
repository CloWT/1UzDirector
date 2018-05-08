package uz.a1uz.a1uzdirector.Activity.TablesActivitys.ProceedsTables;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import uz.a1uz.a1uzdirector.Enums.EdatePeriod;
import uz.a1uz.a1uzdirector.Helpers.CustomActivity;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class ProceedsTable extends CustomActivity {
    String url= UrlHepler.Combine(URL_cons.PROCCEDREPORT, UserInfo.getGUID());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSubTitleC(getString(R.string.Vyruchka));
        setContentView(R.layout.activity_proceeds_table);
        AddElemsToTable();
    }
    private void AddElemsToTable(){
        GetJson.execute(url, new IGetJsonResult() {
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


                    JSONObject j =jsonObject.getJSONObject("GetProceedsReportResult");
                    ((TextView) findViewById(R.id.ReadyLastDay))        .setText(j.getString("ReadyLastDay"));
                    ((TextView) findViewById(R.id.ReadyLastMonth))      .setText(j.getString("ReadyLastMonth"));
                    ((TextView) findViewById(R.id.ReadyLastWeek))       .setText(j.getString("ReadyLastWeek"));
                    ((TextView) findViewById(R.id.ReadyThisMonth))      .setText(j.getString("ReadyThisMonth"));
                    ((TextView) findViewById(R.id.ReadyThisWeek))       .setText(j.getString("ReadyThisWeek"));
                    ((TextView) findViewById(R.id.ReadyThisYearBegin))  .setText(j.getString("ReadyThisYearBegin"));
                    ((TextView) findViewById(R.id.OTLastDay))           .setText(j.getString("OTLastDay"));
                    ((TextView) findViewById(R.id.OTLastWeek))          .setText(j.getString("OTLastWeek"));
                    ((TextView) findViewById(R.id.OTLastMonth))         .setText(j.getString("OTLastMonth"));
                    ((TextView) findViewById(R.id.OTThisMonth))         .setText(j.getString("OTThisMonth"));
                    ((TextView) findViewById(R.id.OTThisWeek))          .setText(j.getString("OTThisWeek"));
                    ((TextView) findViewById(R.id.OTThisYearBegin))     .setText(j.getString("OTThisYearBegin"));
                    ((TextView) findViewById(R.id.SerLastDay))          .setText(j.getString("SerLastDay"));
                    ((TextView) findViewById(R.id.SerLastMonth))        .setText(j.getString("SerLastMonth"));
                    ((TextView) findViewById(R.id.SerLastWeek))         .setText(j.getString("SerLastWeek"));
                    ((TextView) findViewById(R.id.SerThisMonth))        .setText(j.getString("SerThisMonth"));
                    ((TextView) findViewById(R.id.SerThisWeek))         .setText(j.getString("SerThisWeek"));
                    ((TextView) findViewById(R.id.SerThisYearBegin))    .setText(j.getString("SerThisYearBegin"));
                    ((TextView) findViewById(R.id.TotalLastDay))        .setText(j.getString("TotalLastDay"));
                    ((TextView) findViewById(R.id.TotalLastMonth))      .setText(j.getString("TotalLastMonth"));
                    ((TextView) findViewById(R.id.TotalLastWeek))       .setText(j.getString("TotalLastWeek"));
                    ((TextView) findViewById(R.id.TotalThisMonth))      .setText(j.getString("TotalThisMonth"));
                    ((TextView) findViewById(R.id.TotalThisWeek))       .setText(j.getString("TotalThisWeek"));
                    ((TextView) findViewById(R.id.TotalThisYearBegin))  .setText(j.getString("TotalThisYearBegin"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }         }            @Override
            public void OnError(Exception e) {

            }
        });    }
    public void cLastDay(View view) {
        switch (view.getId()){
            case R.id.ReadyLastDay: activityStart(EdatePeriod.LastDay,1);break;
            case R.id.OTLastDay: activityStart(EdatePeriod.LastDay,2);break;
            case R.id.SerLastDay: activityStart(EdatePeriod.LastDay,3);break;
            case R.id.TotalLastDay: activityStart(EdatePeriod.LastDay,0);break;

        }

    }
    public void cThisWeek(View view) {
        switch (view.getId()){
            case R.id.ReadyThisWeek: activityStart(EdatePeriod.ThisWeek,1);break;
            case R.id.OTThisWeek: activityStart(EdatePeriod.ThisWeek,2);break;
            case R.id.SerThisWeek: activityStart(EdatePeriod.ThisWeek,3);break;
            case R.id.TotalThisWeek: activityStart(EdatePeriod.ThisWeek,0);break;

        }
    }
    public void cLastWeek(View view) {
        switch (view.getId()){
            case R.id.ReadyLastWeek: activityStart(EdatePeriod.LastWeek,1);break;
            case R.id.OTLastWeek: activityStart(EdatePeriod.LastWeek,2);break;
            case R.id.SerLastWeek: activityStart(EdatePeriod.LastWeek,3);break;
            case R.id.TotalLastWeek: activityStart(EdatePeriod.LastWeek,0);break;

        }
    }
    public void cThisMonth(View view){switch (view.getId()){
        case R.id.ReadyThisMonth: activityStart(EdatePeriod.ThisMonth,1);break;
        case R.id.OTThisMonth: activityStart(EdatePeriod.ThisMonth,2);break;
        case R.id.SerThisMonth: activityStart(EdatePeriod.ThisMonth,3);break;
        case R.id.TotalThisMonth: activityStart(EdatePeriod.ThisMonth,0);break;

    }
    }
    public void cLastMonth(View view) {
        switch (view.getId()){
            case R.id.ReadyLastMonth: activityStart(EdatePeriod.LastMonth,1);break;
            case R.id.OTLastMonth: activityStart(EdatePeriod.LastMonth,2);break;
            case R.id.SerLastMonth: activityStart(EdatePeriod.LastMonth,3);break;
            case R.id.TotalLastMonth: activityStart(EdatePeriod.LastMonth,0);break;

        }
    }
    public void cThisyear(View view) {
        switch (view.getId()){
            case R.id.ReadyThisYearBegin: activityStart(EdatePeriod.BeginYear,1);break;
            case R.id.OTThisYearBegin: activityStart(EdatePeriod.BeginYear,2);break;
            case R.id.SerThisYearBegin: activityStart(EdatePeriod.BeginYear,3);break;
            case R.id.TotalThisYearBegin: activityStart(EdatePeriod.BeginYear,0);break;

        }
    }

    public void activityStart(EdatePeriod e,int id){
       // int id =((WidgetDropDownItem)spinner.getSelectedItem()).getId();
        FirstLastDate d=new FirstLastDate();
        d.GetPeriodDate(e);
        Intent intent= new Intent(this, ProceedsPeriodTable.class);
        intent.putExtra("ReportID",id);
        intent.putExtra("DateE",e.name());
        startActivity(intent);
    }
}
