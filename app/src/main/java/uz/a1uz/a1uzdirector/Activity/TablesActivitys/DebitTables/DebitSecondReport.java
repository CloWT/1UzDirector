package uz.a1uz.a1uzdirector.Activity.TablesActivitys.DebitTables;

import android.content.Context;
import android.content.Intent;
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

import uz.a1uz.a1uzdirector.Helpers.LayoutConfiguration;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class DebitSecondReport extends AppCompatActivity implements LayoutConfiguration<DebitReportResult> {
    GridLayout debitTable;
    String url;
    Context context;
    ProgressBar progressBar;
    TextView debitT;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_second_report);
        context=this;
        debitTable =(GridLayout) findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        debitT =(TextView)findViewById(R.id.creditT);

        intent=getIntent();
        int id=intent.getIntExtra("ID",-1);

        debitTable.setVisibility(View.GONE);
        url= UrlHepler.Combine(URL_cons.DEBITSECONDREPORT, String.valueOf(id),UserInfo.getGUID());
        GetJson.execute(url, new IGetJsonResult() {
            @Override
            public void OnBegin() {
                ((DebitSecondReport)context).progressBar.setVisibility(View.VISIBLE);
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
                    ((DebitSecondReport)context).progressBar.setVisibility(View.GONE);
                    ((DebitSecondReport)context).debitTable.setVisibility(View.VISIBLE);
                    List<DebitReportResult> debitReportResults=new ArrayList<>();
                    JSONObject js=(JSONObject) jsonObject.get("GetDebitSecondReportResult");
                    JSONArray jsAr=js.getJSONArray("DebitItemList");
                    JSONObject tmp;
                    for (int i = 0; i < jsAr.length(); i++) {
                        tmp=jsAr.getJSONObject(i);
                        debitReportResults.add(new DebitReportResult(
                                tmp.getString("Number"),
                                tmp.getInt("ReportDecriptionID"),
                                tmp.getString("Contragent"),
                                tmp.getString("Contract"),
                                tmp.getString("Summa"),
                                tmp.getString("Day30"),
                                tmp.getString("Day60"),
                                tmp.getString("Day90"),
                                tmp.getString("Day100")
                        ));
                        if(i==0) debitT.setText(tmp.getString("Contragent"));
                    }
                    debitReportResults.add(new DebitReportResult(
                            "",
                            -1,
                            "Итого",
                            "Итого",
                            js.getString("TSumma"),
                            js.getString("TDay30"),
                            js.getString("TDay60"),
                            js.getString("TDay90"),
                            js.getString("TDay100")
                    ));
                    AddElemsToTable(debitReportResults);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void OnError(Exception e) {
                ((DebitTable)context).progressBar.setVisibility(View.GONE);
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    TextView[][] txResult;
    int columnCount=7;

    @Override
    public void AddElemsToTable(List<DebitReportResult> periodResult) {
        TextViewClear();
        int sizeP=periodResult.size();
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
                    debitTable.addView(txResult[i][j]);

                }
            }else{
                for (int j = 0; j < txResult[0].length; j++) {
                    txResult[i][j]=new TextView(this);
                    txResult[i][j].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                    txResult[i][j].setBackgroundResource(R.color.tableTopColor);
                    txResult[i][j].setPadding(15,15,15,15);
                    debitTable.addView(txResult[i][j]);

                }

            }


            CustomSetTex(txResult[i],periodResult.get(i));
            CustomLayoutParams(txResult[i]);
        }


    }
    @Override
    public void CustomLayoutParams(TextView[] txV) {
        for (TextView tx:txV) {
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tx.getLayoutParams();
            lp.setGravity(Gravity.FILL_HORIZONTAL);
            tx.setLayoutParams(lp);
        }
    }
    @Override
    public void CustomSetTex(TextView[] txV, DebitReportResult periodResults) {
        txV[0].setText(periodResults.getNumber());
        txV[1].setText(periodResults.getContract());
        txV[2].setText(periodResults.getDay100());
        txV[3].setText(periodResults.getDay90());
        txV[4].setText(periodResults.getDay60());
        txV[5].setText(periodResults.getDay30());
        txV[6].setText(periodResults.getSumma());


    }

    private void TextViewClear() {
        if(txResult!=null){
            for (TextView[] textViews:txResult){
                for (TextView tx :textViews) {
                    debitTable.removeView(tx);
                }
            }
        }
    }
}
