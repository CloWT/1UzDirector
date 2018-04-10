package uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.models.BankPeriodResult;
import uz.a1uz.a1uzdirector.EdatePeriod;
import uz.a1uz.a1uzdirector.Helpers.DatePeriodPicker;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.Memory_tmp;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class BankPeriodTable extends AppCompatActivity {
    TableLayout periodTable;
    int ReportID;
    Context context=this;
    ProgressBar progressBar;
    TableRow[] TR;
    int requesCodeForDatePicker=1;
    Intent inDatePicker;
    TextView tDatePeriod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_period_table);
        periodTable=(TableLayout)findViewById(R.id.periodTable);
        periodTable.setBackgroundResource(R.drawable.border_dark_shape);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        progressBar.setVisibility(View.VISIBLE);
        tDatePeriod=(TextView)findViewById(R.id.tDatePeriod);
        TextView bankType=(TextView)findViewById(R.id.typeText);
        FirstLastDate da=new FirstLastDate();
        inDatePicker =new Intent(context, DatePeriodPicker.class);
        Intent bankTableIntent=getIntent();
        ReportID=bankTableIntent.getIntExtra("ReportID",-1);
        EdatePeriod e=EdatePeriod.valueOf(bankTableIntent.getStringExtra("DateE"));
        String url;

        bankType.setText("  "+Memory_tmp.getDropDown(ReportID));
        periodTable.setVisibility(View.INVISIBLE);
        da.GetPeriodDate(e);
        tDatePeriod.setText(da.getFirstDate()+" - "+da.getLastDate() );
        url= UrlHepler.Combine(URL_cons.BANKPERIODREPORT,ReportID+"",
                da.getFirstDate(),da.getLastDate(),UserInfo.getGUID());
        getFromJson(url);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {return;}
        if(requestCode==requesCodeForDatePicker&& resultCode==RESULT_OK){
        String firstDate = data.getStringExtra("fDate");
        String secondDate = data.getStringExtra("sDate");
        String url= UrlHepler.Combine(URL_cons.BANKPERIODREPORT,ReportID+"",
                firstDate,secondDate,UserInfo.getGUID());
            tDatePeriod.setText(firstDate+" - "+secondDate);
        getFromJson(url);
        }
    }

    /**
     *
     * @param url BankPeriodTable url
     */
    private void getFromJson(String url) {
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
                try{
                    progressBar.setVisibility(View.GONE);
                    periodTable.setVisibility(View.VISIBLE);
                    JSONObject tmp;
                    List<BankPeriodResult> periodResults=new ArrayList<>();
                    JSONObject js=(JSONObject)jsonObject.get("GetBankPeriodReportResult");
                    JSONArray jsonArray=js.getJSONArray("RowItemList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        tmp=jsonArray.getJSONObject(i);
                        periodResults.add(new BankPeriodResult(
                                tmp.getString("Detail"),
                                tmp.getString("InSum"),
                                tmp.getString("OutSum")

                        ));
                    }
                    if(jsonArray.length()<1) periodResults.add(new BankPeriodResult("    Нет данных","",""));
                    periodResults.add(new BankPeriodResult(
                            "Итого обороты ",
                            js.getString("TotalInSum"),
                            js.getString("TotalOutSum")
                    ));
                    periodResults.add(new BankPeriodResult(
                            "Остатки на ",
                            "начало: "+js.getString("BeginSum"),
                            "конец: "+js.getString("EndSum")
                    ));
                    ToTable(periodResults);


                }
                catch (JSONException ignored){

                }

            }

            @Override
            public void OnError(Exception e) {

            }
        });
    }
    /**
     *
     * @param periodResults
     */
    private void ToTable(List<BankPeriodResult> periodResults){
        if(TR!=null)
        for (TableRow tr:TR) {
            periodTable.removeView(tr);
        }
        int sizeP=periodResults.size();
        TextView TW[][]=new TextView[sizeP][3];
        TR=new TableRow[sizeP];

        for (int i = 0; i < sizeP; i++) {
            TR[i]=new TableRow(this);
            for (int j = 0; j < TW[0].length; j++) {TW[i][j]=new TextView(this);}
            TW[i][0].setText(periodResults.get(i).getDetail());
            TW[i][1].setText(periodResults.get(i).getInSum());
            TW[i][2].setText(periodResults.get(i).getOutSum());
            for (int j = 0; j < TW[0].length; j++) {
                if(i<sizeP-2&&sizeP>2){
                    TW[i][j].setGravity(j==0?Gravity.LEFT:Gravity.RIGHT);
                    TW[i][j].setTextColor(ContextCompat.getColor(this,R.color.tableTopColor));
                    TW[i][j].setBackgroundResource(R.drawable.border_shape);
                }else{
                    TW[i][j].setGravity(Gravity.RIGHT);
                    TW[i][j].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                    TW[i][j].setBackgroundResource(R.color.tableTopColor);

                }

                TW[i][j].setPadding(15,15,15,15);
                TR[i].addView(TW[i][j]);

            }

            periodTable.addView(TR[i]);
        }

    }

    public void tDateClick(View view) {
        startActivityForResult(inDatePicker,requesCodeForDatePicker);
    }
}