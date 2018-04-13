package uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import uz.a1uz.a1uzdirector.Activity.models.WidgetDropDownItem;
import uz.a1uz.a1uzdirector.EdatePeriod;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.Helpers.Memory_tmp;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class BankBankReport extends AppCompatActivity {
int reportID;
    Spinner spinner;
    TableLayout tableLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bank_bank_report);
        spinner=(Spinner) findViewById(R.id.SchetSpin);
        tableLayout=(TableLayout)findViewById(R.id.tableForBank);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbarcust));
        tableLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ArrayAdapter<WidgetDropDownItem> adapter=
                new ArrayAdapter<>(this,
                        R.layout.simple_spinner_item, Memory_tmp.getReportResults_dropdownList() );
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddElemsToTable(Memory_tmp.getReportResults_dropdownList().get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Intent bankTableIntent=getIntent();
        int ReportID=bankTableIntent.getIntExtra("ReportID",-1);
        List<WidgetDropDownItem> reportResults_dropdownList = Memory_tmp.getReportResults_dropdownList();
        for (int i = 0; i < reportResults_dropdownList.size(); i++) {
            WidgetDropDownItem d = reportResults_dropdownList.get(i);
            if (d.getId() == ReportID) spinner.setSelection(i);
        }
    }

    private void AddElemsToTable(int reportID){
        this.reportID=reportID;
        String url= UrlHepler.Combine(URL_cons.BANKREPORT,reportID+"", UserInfo.getGUID());
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
                    progressBar.setVisibility(View.GONE);
                    tableLayout.setVisibility(View.VISIBLE);


                    JSONObject j =jsonObject.getJSONObject("GetBankReportResult");

                    ((TextView) findViewById(R.id.BPLastDay))        .setText(j.getString("BPLastDay"));
                    ((TextView) findViewById(R.id.BPLastWeek))       .setText(j.getString("BPLastWeek"));
                    ((TextView) findViewById(R.id.BPLastMonth))      .setText(j.getString("BPLastMonth"));
                    ((TextView) findViewById(R.id.BPThisMonth))      .setText(j.getString("BPThisMonth"));
                    ((TextView) findViewById(R.id.BPThisWeek))       .setText(j.getString("BPThisWeek"));
                    ((TextView) findViewById(R.id.BPThisYearBegin))  .setText(j.getString("BPThisYearBegin"));
                    ((TextView) findViewById(R.id.INLastDay))        .setText(j.getString("INLastDay"));
                    ((TextView) findViewById(R.id.INLastMonth))      .setText(j.getString("INLastMonth"));
                    ((TextView) findViewById(R.id.INLastWeek))       .setText(j.getString("INLastWeek"));
                    ((TextView) findViewById(R.id.INThisMonth))      .setText(j.getString("INThisMonth"));
                    ((TextView) findViewById(R.id.INThisWeek))       .setText(j.getString("INThisWeek"));
                    ((TextView) findViewById(R.id.INThisYearBegin))  .setText(j.getString("INThisYearBegin"));
                    ((TextView) findViewById(R.id.LPLastDay))        .setText(j.getString("LPLastDay"));
                    ((TextView) findViewById(R.id.LPLastMonth))      .setText(j.getString("LPLastMonth"));
                    ((TextView) findViewById(R.id.LPLastWeek))       .setText(j.getString("LPLastWeek"));
                    ((TextView) findViewById(R.id.LPThisMonth))      .setText(j.getString("LPThisMonth"));
                    ((TextView) findViewById(R.id.LPThisWeek))       .setText(j.getString("LPThisWeek"));
                    ((TextView) findViewById(R.id.LPThisYearBegin))  .setText(j.getString("LPThisYearBegin"));
                    ((TextView) findViewById(R.id.OutLastDay))       .setText(j.getString("OutLastDay"));
                    ((TextView) findViewById(R.id.OutLastMonth))     .setText(j.getString("OutLastMonth"));
                    ((TextView) findViewById(R.id.OutLastWeek))      .setText(j.getString("OutLastWeek"));
                    ((TextView) findViewById(R.id.OutThisMonth))     .setText(j.getString("OutThisMonth"));
                    ((TextView) findViewById(R.id.OutThisWeek))      .setText(j.getString("OutThisWeek"));
                    ((TextView) findViewById(R.id.OutThisYearBegin)) .setText(j.getString("OutThisYearBegin"));
                    ((TextView) findViewById(R.id.dateReport_text)) .setText("Отчет актуален на "+j.getString("ActualDate"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }         }            @Override
            public void OnError(Exception e) {

            }
        });    }
    public void cLastDay(View view) {
        activityStart(EdatePeriod.LastDay);
    }
    public void cThisWeek(View view) {
        activityStart(EdatePeriod.ThisWeek);
    }
    public void cLastWeek(View view) {
        activityStart(EdatePeriod.LastWeek);
    }
    public void cThisMonth(View view){
        activityStart(EdatePeriod.ThisMonth);
    }
    public void cLastMonth(View view) {
        activityStart(EdatePeriod.LastMonth);
    }
    public void cThisyear(View view) {
        activityStart(EdatePeriod.BeginYear);
    }

    public void activityStart(EdatePeriod e){
        int id =((WidgetDropDownItem)spinner.getSelectedItem()).getId();
        FirstLastDate d=new FirstLastDate();
        d.GetPeriodDate(e);
        Intent intent= new Intent(this, BankPeriodTable.class);
        intent.putExtra("ReportID",id);
        intent.putExtra("firsDate",d.getFirstDate());
        intent.putExtra("secondDate",d.getLastDate());
        intent.putExtra("DateE",e.name());
        startActivity(intent);
    }


}
