package uz.a1uz.a1uzdirector.Activity.TablesActivitys.CreditTables;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import uz.a1uz.a1uzdirector.Helpers.ActionBarCustomizer;
import uz.a1uz.a1uzdirector.Helpers.LayoutConfiguration;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class CreditTable extends ActionBarCustomizer implements LayoutConfiguration<CreditReportResult> {
    String url= UrlHepler.Combine(URL_cons.CREDITREPORT, UserInfo.getGUID());
    GridLayout creditTable;
    Context context;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_table);
        setSubTitleC("МЫ ДОЛЖНЫ");
        context=this;
        creditTable=(GridLayout) findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        creditTable.setVisibility(View.GONE);
        param2.setGravity(Gravity.FILL);

        GetJson.execute(url, new IGetJsonResult() {
            @Override
            public void OnBegin() {
                ((CreditTable)context).progressBar.setVisibility(View.VISIBLE);
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
                    ((CreditTable)context).progressBar.setVisibility(View.GONE);
                    ((CreditTable)context).creditTable.setVisibility(View.VISIBLE);
                    List<CreditReportResult> creditReportResults=new ArrayList<>();
                    JSONObject js=(JSONObject) jsonObject.get("GetCreditReportResult");
                    JSONArray jsAr=js.getJSONArray("CreditItemList");
                    JSONObject tmp;
                    for (int i = 0; i < jsAr.length(); i++) {
                        tmp=jsAr.getJSONObject(i);
                        creditReportResults.add(new CreditReportResult(
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
                    creditReportResults.add(new CreditReportResult(
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
                ((CreditTable)context).progressBar.setVisibility(View.GONE);
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    TextView[][] txResult;
    int columnCount=7;
    GridLayout.LayoutParams param2 =new GridLayout.LayoutParams();


    @Override
    public void AddElemsToTable(List<CreditReportResult> periodResult) {
        TextViewClear();

        int sizeP=periodResult.size();
        txResult=new TextView[sizeP][columnCount];
        for (int i = 0; i < txResult.length; i++) {
            if(i<sizeP-1){
                OnClick click=new OnClick(periodResult.get(i).getReportDecriptionID());

            for (int j = 0; j < txResult[0].length; j++) {
                txResult[i][j]=new TextView(this);
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
                creditTable.addView(txResult[i][j]);

            }
            }else{
                for (int j = 0; j < txResult[0].length; j++) {
                    txResult[i][j]=new TextView(this);
                    txResult[i][j].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                    txResult[i][j].setBackgroundResource(R.color.tableTopColor);

                    txResult[i][j].setPadding(15,15,15,15);
                    creditTable.addView(txResult[i][j]);

                }

            }

            CustomSetTex(txResult[i],periodResult.get(i));
            CustomLayoutParams(txResult[i]);
        }


    }

    @Override
    public void CustomSetTex(TextView[] txV, CreditReportResult periodResults) {
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
                    creditTable.removeView(tx);
                }
            }
        }
    }
    class OnClick implements  View.OnClickListener{
        int id;
        public OnClick(int ReportDecriptionID) {
            id=ReportDecriptionID;

        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,CreditSecondReport.class);
            intent.putExtra("ID",id);
            startActivity(intent);
        }
    }

}
