package uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables.models.StorePeriodResult;
import uz.a1uz.a1uzdirector.EdatePeriod;
import uz.a1uz.a1uzdirector.Helpers.ActionBarCustomizer;
import uz.a1uz.a1uzdirector.Helpers.LayoutConfiguration;
import uz.a1uz.a1uzdirector.Helpers.DatePeriodPicker;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class StorePeriodTable extends ActionBarCustomizer implements LayoutConfiguration<StorePeriodResult> {
    GridLayout periodTable;
    int ReportID;
    Context context;
    ProgressBar progressBar;
    Intent inDatePicker;
    String url;
    TextView tDatePeriod;
    int requesCodeForDatePicker=6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setSubTitleC(getString(R.string.Sklad));
        setContentView(R.layout.activity_store_period_table);
        context=this;

        periodTable=(GridLayout)findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        tDatePeriod=(TextView) findViewById(R.id.tDatePeriod);

        inDatePicker=new Intent(context, DatePeriodPicker.class);

        FirstLastDate datFL=new FirstLastDate(EdatePeriod.ThisMonth);
        tDatePeriod.setText(String.format("%s - %s", datFL.getFirstDate(), datFL.getLastDate()));


        Intent inProceedTable=getIntent();
        ReportID=inProceedTable.getIntExtra("ReportID",-1);
        String name=inProceedTable.getStringExtra("Name");
        url= UrlHepler.Combine(URL_cons.STOREPERIODREPORT, String.valueOf(ReportID),
                datFL.getFirstDate(),datFL.getLastDate(), UserInfo.getGUID());
        getFromJson(datFL.getFirstDate(),datFL.getLastDate());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null) return;
        if(requestCode==requesCodeForDatePicker && resultCode==RESULT_OK){
            String firstDate=data.getStringExtra("fDate");
            String secondDate=data.getStringExtra("sDate");
            tDatePeriod.setText(String.format("%s - %s", firstDate, secondDate));
            getFromJson(firstDate,secondDate);

        }

    }

    private void getFromJson(String fDate, String sDate) {
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
                    AddElemsToTable(periodResults);



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

        url= UrlHepler.Combine(URL_cons.STOREPERIODREPORT, String.valueOf(ReportID),
                fDate,sDate, UserInfo.getGUID());

        GetJson.execute(url,logic);
    }
    TextView textView[][];



    private void TextViewClear() {
        if(textView!=null){
            for (TextView[] textViews:textView){
                for (TextView tx :textViews) {
                    periodTable.removeView(tx);
                }
            }
            if (tx != null) periodTable.removeView(tx);

        }
    }

    public void tDateClick(View view) {
        startActivityForResult(inDatePicker,requesCodeForDatePicker);
    }
    TextView tx;
    @Override
    public void AddElemsToTable(List<StorePeriodResult> periodResults) {

        GridLayout.LayoutParams param2 =new GridLayout.LayoutParams();
        param2.setGravity(Gravity.FILL);
        TextViewClear();
        int sizeP=periodResults.size();
        textView=new TextView[sizeP][11];
        if(sizeP==1){
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.columnSpec = GridLayout.spec(0,11);
            param.setGravity(Gravity.FILL);
            tx=new TextView(this);
            tx.setGravity(Gravity.CENTER_HORIZONTAL);
            tx.setTextColor(ContextCompat.getColor(this,R.color.tableTopColor));
            tx.setBackgroundResource(R.drawable.border_shape);
            tx.setText(getString(R.string.NotData));
            tx.setLayoutParams(param);
            periodTable.addView(tx);

            for (int i = 0; i < textView[0].length; i++) {
                textView[0][i]=new TextView(this);
//                textView[0][i].setGravity(Gravity.FILL);


                textView[0][i].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                textView[0][i].setBackgroundResource(R.color.tableTopColor);
                periodTable.addView(textView[0][i]);
            }
            CustomSetTex(textView[0],periodResults.get(0));
            CustomLayoutParams(textView[0]);

            return;}

        for (int i = 0; i < textView.length; i++) {

            for (int j = 0; j <textView[0].length ; j++) {
                textView[i][j]=new TextView(this);

                if(i<sizeP-1&&sizeP>1){

//                    textView[i][j].setGravity(j==0? Gravity.START:Gravity.END);
                    textView[i][j].setTextColor(ContextCompat.getColor(this,R.color.tableTopColor));
                    textView[i][j].setBackgroundResource(R.drawable.border_shape);

                }else{

//
                    textView[i][j].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                    textView[i][j].setBackgroundResource(R.color.tableTopColor);

                }
                textView[i][i].setLayoutParams(param2);
                textView[i][j].setPadding(15,15,15,15);
                periodTable.addView(textView[i][j]);
            }
            CustomSetTex(textView[i],periodResults.get(i));
            CustomLayoutParams(textView[i]);

        }


    }
    @Override
    public void CustomSetTex(TextView[] txV, StorePeriodResult periodResults){
        txV[0].setText(periodResults.getName());
        txV[1].setText(periodResults.getUM());
        txV[2].setText(periodResults.getPrice());
        txV[3].setText(periodResults.getBQ());
        txV[4].setText(periodResults.getBS());
        txV[5].setText(periodResults.getPDQ());
        txV[6].setText(periodResults.getPD());
        txV[7].setText(periodResults.getPCQ());
        txV[8].setText(periodResults.getPC());
        txV[9].setText(periodResults.getEQ());
        txV[10].setText(periodResults.getES());

    }

    @Override
    public void CustomLayoutParams(TextView[] txV) {
        for (TextView tx:txV) {
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tx.getLayoutParams();
            lp.setGravity(Gravity.FILL_HORIZONTAL);
            tx.setLayoutParams(lp);
        }
    }
}
