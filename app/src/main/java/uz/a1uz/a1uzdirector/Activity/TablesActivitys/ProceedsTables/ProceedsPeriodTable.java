package uz.a1uz.a1uzdirector.Activity.TablesActivitys.ProceedsTables;

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
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.ProceedsTables.models.ProceedsPeriodResult;
import uz.a1uz.a1uzdirector.EdatePeriod;
import uz.a1uz.a1uzdirector.Helpers.LayoutConfiguration;
import uz.a1uz.a1uzdirector.Helpers.DatePeriodPicker;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class ProceedsPeriodTable extends AppCompatActivity implements LayoutConfiguration<ProceedsPeriodResult> {
    TableLayout periodTable;
    int ReportID;
    Context context;
    ProgressBar progressBar;
    TextView tDatePeriod;
    Intent inDatePicker;
    TableRow[] TR;
    String url;
    int requesCodeForDatePicker=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceeds_period_table);
        context=this;
        periodTable=(TableLayout) findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        progressBar.setVisibility(View.VISIBLE);
        tDatePeriod=(TextView) findViewById(R.id.tDatePeriod);
        FirstLastDate datFL=new FirstLastDate();
        inDatePicker =new Intent(context, DatePeriodPicker.class);
        Intent inProceedTable=getIntent();
        ReportID=inProceedTable.getIntExtra("ReportID",-1);
        EdatePeriod e=EdatePeriod.valueOf(inProceedTable.getStringExtra("DateE"));

        periodTable.setVisibility(View.INVISIBLE);
        datFL.GetPeriodDate(e);
        tDatePeriod.setText(String.format("%s - %s", datFL.getFirstDate(), datFL.getLastDate()));
        url= UrlHepler.Combine(URL_cons.PROCCEDPERIODREPORT,ReportID+"",
                datFL.getFirstDate(),datFL.getLastDate(),
                UserInfo.getGUID());
        getFromJson(url);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {return;}
        if(requestCode==requesCodeForDatePicker&& resultCode==RESULT_OK){
            String firstDate = data.getStringExtra("fDate");
            String secondDate = data.getStringExtra("sDate");
            String url= UrlHepler.Combine(URL_cons.PROCCEDPERIODREPORT,ReportID+"",
                    firstDate,secondDate,
                    UserInfo.getGUID());
            tDatePeriod.setText(String.format("%s - %s", firstDate, secondDate));
            getFromJson(url);
        }
    }
    void getFromJson(String url){
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
                    List<ProceedsPeriodResult> periodResults=new ArrayList<>();
                    JSONObject js=(JSONObject)jsonObject.get("GetProceedsPeriodReportResult");
                    JSONArray jsonArray=js.getJSONArray("ItemList");

                    for (int i=0;i<jsonArray.length(); i++){
                        tmp=jsonArray.getJSONObject(i);
                        periodResults.add(new ProceedsPeriodResult(
                                ""+tmp.getInt("Number"),
                                tmp.getString("Date"),
                                tmp.getString("Contragent"),
                                tmp.getString("Contract"),
                                tmp.getString("VidType"),
                                tmp.getString("Summa"))
                        );
                    }
                    if(jsonArray.length()<1)periodResults.add(new ProceedsPeriodResult(
                            "",""," Нет данных ","","",""
                    ));
                    periodResults.add(new ProceedsPeriodResult("Итого","","","",
                            "",js.getString("TotalSumma")));
                    AddElemsToTable(periodResults);



                }
                catch (JSONException ignored){

                }

            }

            @Override
            public void OnError(Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void AddElemsToTable(List<ProceedsPeriodResult> periodResults){
        if(TR!=null)
            for (TableRow tr:TR) {
                periodTable.removeView(tr);
            }
        int sizeP=periodResults.size();
        TextView TW[][]=new TextView[sizeP][6];
        TR=new TableRow[sizeP];
        for (int i = 0; i < sizeP; i++) {
            TR[i]=new TableRow(this);
            for (int j = 0; j < TW[0].length; j++) {TW[i][j]=new TextView(this);}
            TW[i][0].setText(periodResults.get(i).getNumber());
            TW[i][1].setText(periodResults.get(i).getDate());
            TW[i][2].setText(periodResults.get(i).getContragent());
            TW[i][3].setText(periodResults.get(i).getContract());
            TW[i][4].setText(periodResults.get(i).getVidType());
            TW[i][5].setText(periodResults.get(i).getSumma());
            for (int j = 0; j < TW[0].length; j++) {
                if(i<sizeP-1&&sizeP>1){
                    TW[i][j].setGravity(j==0? Gravity.START:Gravity.END);
                    TW[i][j].setTextColor(ContextCompat.getColor(this,R.color.tableTopColor));
                    TW[i][j].setBackgroundResource(R.drawable.border_shape);
                }else{
                    TW[i][j].setGravity(Gravity.END);
                    TW[i][j].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                    TW[i][j].setBackgroundResource(R.color.tableTopColor);

                }

                TW[i][j].setPadding(15,15,15,15);
                TR[i].addView(TW[i][j]);
        }
        periodTable.addView(TR[i]);

        }


    }

    @Override
    public void CustomSetTex(TextView[] txV, ProceedsPeriodResult periodResults) {

    }

    @Override
    public void CustomLayoutParams(TextView[] txV) {

    }

    public void tDateClick(View view) {
        startActivityForResult(inDatePicker,requesCodeForDatePicker);
    }
}
