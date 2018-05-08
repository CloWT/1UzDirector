package uz.a1uz.a1uzdirector.Activity.TablesActivitys.DebitTables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
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

import uz.a1uz.a1uzdirector.Helpers.CustomActivity;
import uz.a1uz.a1uzdirector.Helpers.LayoutConfiguration;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class DebitTable extends CustomActivity implements LayoutConfiguration<DebitReportResult> {
    String url= UrlHepler.Combine(URL_cons.DEBITREPORT, UserInfo.getGUID());
    GridLayout debitTable;
    Context context;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_table);
        setSubTitleC(getString(R.string.NamDoljny));
        context=this;
        debitTable =(GridLayout) findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        debitTable.setVisibility(View.GONE);
        param2.setGravity(Gravity.FILL);

        GetJson.execute(url, new IGetJsonResult() {
            @Override
            public void OnBegin() {
                ((DebitTable)context).progressBar.setVisibility(View.VISIBLE);
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
                    ((DebitTable)context).progressBar.setVisibility(View.GONE);
                    ((DebitTable)context).debitTable.setVisibility(View.VISIBLE);
                    List<DebitReportResult> creditReportResults=new ArrayList<>();
                    JSONObject js=(JSONObject) jsonObject.get("GetDebitReportResult");
                    JSONArray jsAr=js.getJSONArray("DebitItemList");
                    JSONObject tmp;
                    for (int i = 0; i < jsAr.length(); i++) {
                        tmp=jsAr.getJSONObject(i);
                        creditReportResults.add(new DebitReportResult(
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
                    }
                    creditReportResults.add(new DebitReportResult(
                            "",
                            -1,
                            getString(R.string.total),
                            "",
                            js.getString("TSumma"),
                            js.getString("TDay30"),
                            js.getString("TDay60"),
                            js.getString("TDay90"),
                            js.getString("TDay100")
                    ));
                    AddElemsToTable(creditReportResults);



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
    GridLayout.LayoutParams param2 =new GridLayout.LayoutParams();


    @Override
    public void AddElemsToTable(List<DebitReportResult> periodResult) {
        TextViewClear();

        int sizeP=periodResult.size();
        txResult=new TextView[sizeP][columnCount];
        for (int i = 0; i < txResult.length; i++) {
            if(i<sizeP-1){
                OnClick click=new OnClick(periodResult.get(i).getReportDecriptionID());

            for (int j = 0; j < txResult[0].length; j++) {
                txResult[i][j]=new TextView(this);
                txResult[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP, tableBodyTextSize);
                txResult[i][j].setOnClickListener(click);
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
               // txResult[i][j].setLayoutParams(param2);
                txResult[i][j].setPadding(15,15,15,15);
                debitTable.addView(txResult[i][j]);

            }
            }else{
                for (int j = 0; j < txResult[0].length; j++) {
                    txResult[i][j]=new TextView(this);
                    txResult[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP, tableBodyTextSize);
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
    public void CustomSetTex(TextView[] txV, DebitReportResult periodResults) {
        txV[0].setText(periodResults.getNumber());
        txV[1].setText(periodResults.getContragent());
        txV[2].setText(periodResults.getDay100());
        txV[3].setText(periodResults.getDay90());
        txV[4].setText(periodResults.getDay60());
        txV[5].setText(periodResults.getDay30());
        txV[6].setText(periodResults.getSumma());



        // Apply the updated layout parameters to TextView



    }

    @Override
    public void CustomLayoutParams(TextView[] txV) {
        for (TextView tx:txV) {
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tx.getLayoutParams();
            lp.setGravity(Gravity.FILL_HORIZONTAL);
            tx.setLayoutParams(lp);
        }
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
    class OnClick implements  View.OnClickListener{
        int id;
        OnClick(int ReportDecriptionID) {
            id=ReportDecriptionID;

        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,DebitSecondReport.class);
            intent.putExtra("ID",id);
            startActivity(intent);
        }
    }

}
