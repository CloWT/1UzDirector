package uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.ProceedsTables.models.ProceedsPeriodResult;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables.models.StorePeriodResult;
import uz.a1uz.a1uzdirector.EdatePeriod;
import uz.a1uz.a1uzdirector.Helpers.DatePeriodPicker;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class StorePeriodTable extends AppCompatActivity {
    GridLayout periodTable;
    int ReportID;
    Context context;
    ProgressBar progressBar;
    Intent inDatePicker;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_period_table);
        context=this;
        periodTable=(GridLayout)findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        inDatePicker=new Intent(context, DatePeriodPicker.class);

        FirstLastDate datFL=new FirstLastDate(EdatePeriod.ThisMonth);



        Intent inProceedTable=getIntent();
        ReportID=inProceedTable.getIntExtra("ReportID",-1);
        String name=inProceedTable.getStringExtra("Name");
        url= UrlHepler.Combine(URL_cons.STOREPERIODREPORT, String.valueOf(ReportID),
                datFL.getFirstDate(),datFL.getLastDate());
        getFromJson(url);




    }

    private void getFromJson(String url) {
        IGetJsonResult logic=new IGetJsonResult() {
            @Override
            public void OnBegin() {
                progressBar.setVisibility(View.VISIBLE);
                periodTable.setVisibility(View.INVISIBLE);

            }

            @Override
            public void OnProgressUpdate(int progress) {

            }

            @Override
            public void OnEnd(String[] jsonStringsArr) {

            }

            @Override
            public void OnEnd(JSONObject jsonObject) {
                try{
                    progressBar.setVisibility(View.GONE);
                    periodTable.setVisibility(View.VISIBLE);
                    JSONObject tmp;
                    List<StorePeriodResult> periodResults=new ArrayList<>();
                    JSONObject js=(JSONObject)jsonObject.get("GetStorePeriodReportResult");
                    JSONArray jsonArray=js.getJSONArray("StorePeriodItemList");

                    for (int i=0;i<jsonArray.length(); i++){
                        tmp=jsonArray.getJSONObject(i);
                        periodResults.add(new StorePeriodResult(
                                tmp.getString("Name"),
                                tmp.getString("UM"),
                                tmp.getString("Price"),
                                tmp.getString("BQ"),
                                tmp.getString("BS"),
                                tmp.getString("PDQ"),
                                tmp.getString("PD"),
                                tmp.getString("PCQ"),
                                tmp.getString("PC"),
                                tmp.getString("EQ"),
                                tmp.getString("ES")));
                    }

                    periodResults.add(new StorePeriodResult(
                            getString(R.string.total),
                            "",
                            "",
                            js.getString("BQ"),
                            js.getString("BS"),
                            js.getString("PDQ"),
                            js.getString("PD"),
                            js.getString("PCQ"),
                            js.getString("PC"),
                            js.getString("EQ"),
                            js.getString("ES")));
                    ToTable(periodResults);



                }
                catch (JSONException ignored){

                }

            }

            @Override
            public void OnError(Exception e) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };

        GetJson.execute(url,logic);
    }
    private void ToTable(List<StorePeriodResult> periodResults){



    }

    public void tDateClick(View view) {
    }
}
