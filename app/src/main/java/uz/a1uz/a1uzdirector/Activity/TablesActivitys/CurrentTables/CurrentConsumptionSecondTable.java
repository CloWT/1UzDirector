package uz.a1uz.a1uzdirector.Activity.TablesActivitys.CurrentTables;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uz.a1uz.a1uzdirector.EdatePeriod;
import uz.a1uz.a1uzdirector.Helpers.ActionBarCustomizer;
import uz.a1uz.a1uzdirector.Helpers.DatePeriodPicker;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.LayoutConfiguration;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class CurrentConsumptionSecondTable extends ActionBarCustomizer implements LayoutConfiguration<CurrentConsumptionSecondTable.CurrentReportResult> {
    GridLayout periodTable;
    int ReportID;
    Context context;
    ProgressBar progressBar;
    Intent inDatePicker;
    int requesCodeForDatePicker=4;
    TextView tDatePeriod,tForName;
    String beginDate,endDate;
    Intent inParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_consumption_second_table);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setSubTitleC(getString(R.string.StatyaRasxodov));

        context=this;
        periodTable=(GridLayout)findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        tDatePeriod=(TextView) findViewById(R.id.tDatePeriod);
        tForName=(TextView) findViewById(R.id.forName);

        inDatePicker=new Intent(context, DatePeriodPicker.class);
        inParent=getIntent();

        ReportID=inParent.getIntExtra("id",-1);
        tForName.setText(inParent.getStringExtra("Name"));
        beginDate=inParent.getStringExtra("bDate");
        endDate=inParent.getStringExtra("eDate");
        tDatePeriod.setText(String.format("%s - %s",beginDate , endDate));
        String url= UrlHepler.Combine(URL_cons.CURRENTCOSTSECONDREPORT,ReportID+"",
                beginDate,endDate,
                UserInfo.getGUID());
        getFromJson(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {return;}
        if(requestCode==requesCodeForDatePicker&& resultCode==RESULT_OK){
            String firstDate = data.getStringExtra("fDate");
            String secondDate = data.getStringExtra("sDate");
            String url= UrlHepler.Combine(URL_cons.CURRENTCOSTSECONDREPORT,ReportID+"",
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
                try {

                    JSONObject js=(JSONObject) jsonObject.get("GetSecondCurrentConsumptionReportResult");
                    List<CurrentReportResult> currentReportResultList=new ArrayList<>();
                    JSONArray jsonArray=js.getJSONArray("RecurrentCostsItemList");
                    JSONObject tmp;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        tmp=jsonArray.getJSONObject(i);
                        currentReportResultList.add(new CurrentReportResult(
                                tmp.getString("Name"),
                                tmp.getString("Summa")
                        ));
                    }
                    currentReportResultList.add(new CurrentReportResult(getString(R.string.total),js.getString("Total")));
                    AddElemsToTable(currentReportResultList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(Exception e) {

            }
        });
    }

    public void tDateClick(View view) {
        startActivityForResult(inDatePicker,requesCodeForDatePicker);
    }




    @Override
    public void CustomLayoutParams(TextView[] txV) {
        for (TextView tx:txV) {
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tx.getLayoutParams();
            lp.setGravity(Gravity.FILL_HORIZONTAL);
            tx.setLayoutParams(lp);
        }

    }
    TextView[][] txResult;
    int columnCount=2;

    /***
     *
     * @param reportResults
     */
    @Override
    public void AddElemsToTable(List<CurrentReportResult> reportResults) {

        TextViewClear();
        int sizeP=reportResults.size();
        txResult=new TextView[sizeP][columnCount];
        for (int i = 0; i < txResult.length; i++) {
            if(i<sizeP-1){


                for (int j = 0; j < txResult[0].length; j++) {
                    txResult[i][j]=new TextView(this);

                    txResult[i][j].setTextColor(ContextCompat.getColor(this,R.color.tableTopColor));
                    txResult[i][j].setBackgroundResource(R.drawable.border_shape);

                    if(j<2||j==columnCount-1){
                        txResult[i][j].setBackgroundResource(R.drawable.border_shape);
                    }else {
                        switch (j){
                            case 2: txResult[i][j].setBackgroundResource(R.drawable.border_shape_red1); break;
                            case 3: txResult[i][j].setBackgroundResource(R.drawable.border_shape_red2);break;
                            case 4: txResult[i][j].setBackgroundResource(R.drawable.border_shape_red3);break;
                            case 5: txResult[i][j].setBackgroundResource(R.drawable.border_shape_red4);break;
                        }
                    }

                    txResult[i][j].setPadding(15,15,15,15);
                    periodTable.addView(txResult[i][j]);

                }
            }else{
                for (int j = 0; j < txResult[0].length; j++) {
                    txResult[i][j]=new TextView(this);
                    txResult[i][j].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                    txResult[i][j].setBackgroundResource(R.color.tableTopColor);
                    txResult[i][j].setPadding(15,15,15,15);
                    periodTable.addView(txResult[i][j]);

                }

            }


            CustomSetTex(txResult[i],reportResults.get(i));
            CustomLayoutParams(txResult[i]);
        }



    }

    @Override
    public void CustomSetTex(TextView[] txV, CurrentReportResult periodResults) {
        txV[0].setText(periodResults.getName());
        txV[1].setText(periodResults.getSumma());

    }

    private void TextViewClear() {
        if(txResult!=null){
            for (TextView[] textViews:txResult){
                for (TextView tx :textViews) {
                    periodTable.removeView(tx);
                }
            }
        }
    }

    /***
     *
     */
     class  CurrentReportResult{
        String name;
        String summa;

        CurrentReportResult(String name, String summa) {
            this.name = name;
            this.summa = summa;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSumma() {
            return summa;
        }

        public void setSumma(String summa) {
            this.summa = summa;
        }
    }
}

