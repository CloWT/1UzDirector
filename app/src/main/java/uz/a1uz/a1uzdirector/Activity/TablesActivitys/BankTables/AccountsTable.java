package uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.models.AccountReportResult;
import uz.a1uz.a1uzdirector.Activity.components.models.WidgetDropDownItem;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.Helpers.Memory_tmp;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class AccountsTable extends AppCompatActivity {
String url= UrlHepler.Combine(URL_cons.ACCOUNTREPORT, UserInfo.getGUID());
TableLayout bankTable;
Context context;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_accounts_table);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        bankTable=(TableLayout)findViewById(R.id.tableForBank);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbarcust));

        bankTable.setVisibility(View.INVISIBLE);
        GetJson.execute(url, new IGetJsonResult() {
            AccountsTable activity;
            @Override
            public void OnBegin() {
                activity=(AccountsTable)context;
                activity.progressBar.setVisibility(View.VISIBLE);

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
                    activity.progressBar.setVisibility(View.GONE);
                    activity.bankTable.setVisibility(View.VISIBLE);
                JSONObject tmp;
                List<WidgetDropDownItem>list = new ArrayList<>();
                List<AccountReportResult> accRepResults=new ArrayList<>();
                    JSONObject hh= (JSONObject) jsonObject.get("GetAccountsReportResult");
                JSONArray jsAr=hh.getJSONArray("Items");
                    for (int i = 0; i < jsAr.length(); i++) {
                        tmp=jsAr.getJSONObject(i);
                        accRepResults.add(new AccountReportResult(
                                tmp.getString("Name"),
                                tmp.getString("BeginPeriodSum"),
                                tmp.getString("InSum"),
                                tmp.getString("OutSum"),
                                tmp.getString("CurrentSum"),
                                tmp.getInt("ReportDecriptionID")
                        ));
                        if(i<=jsAr.length()-1)
                            list.add(new WidgetDropDownItem(tmp.getInt("ReportDecriptionID"),tmp.getString("Name")));

                    }
                    accRepResults.add(new AccountReportResult(
                            "Итого",
                            hh.getString("TotalBeginPeriodSum"),
                            hh.getString("TotalInSum"),
                            hh.getString("TotalOutSum"),
                            hh.getString("TotalCurrentSum"),
                            -1)
                    );
                    AddElemsToTable(accRepResults);
                    Memory_tmp.setReportResults_dropdownList(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(Exception e) {
                Log.e("Eroor",e.getMessage());

            }
        });

    }
    private void AddElemsToTable(List<AccountReportResult> accountReportResults){
        TextView TW[][]=new TextView[5][5];
        TableRow[] TR=new TableRow[accountReportResults.size()];
        for (int i = 0; i < accountReportResults.size(); i++) {
            TR[i]=new TableRow(this);

            for (int j = 0; j <TW[0].length ; j++) {
                TW[i][j]=new TextView(this);
            }
            TW[i][0].setText(accountReportResults.get(i).getName().toString());
            TW[i][1].setText(accountReportResults.get(i).getBeginPeriodSum());
            TW[i][2].setText(accountReportResults.get(i).getInSum());
            TW[i][3].setText(accountReportResults.get(i).getOutSum());
            TW[i][4].setText(accountReportResults.get(i).getCurrentSum());

            for (int j = 0; j <5 ; j++) {
                TW[i][j].setGravity(j==0? Gravity.LEFT:Gravity.RIGHT);
                TW[i][j].setBackgroundResource(R.drawable.border_shape);
                if(accountReportResults.size()-1==i){
                    TW[i][j].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                    TW[i][j].setBackgroundResource(R.color.tableTopColor);
                }
                if (j==0&&i<accountReportResults.size()-1)
                    TW[i][j].setTextColor(ContextCompat.getColor(this,R.color.blue));
                TW[i][j].setPadding(5,5,5,5);
                TR[i].addView(TW[i][j]);
            }
            if(accountReportResults.size()-1==i){

                TR[i].setBackgroundColor(ContextCompat.getColor(this,R.color.tableTopColor));
            }

            else{
                TR[i].setBackgroundColor(ContextCompat.getColor(this,R.color.textColor));
                TR[i].setOnClickListener(new ClickT(accountReportResults.get(i).getReportDecriptionID(),this));
            }


            bankTable.addView(TR[i]);




        }

    }
    private class ClickT implements View.OnClickListener {
        int accountReportResult;
        Context context;
        public ClickT(int accountReportResult, Context context) {
            this.accountReportResult=accountReportResult;
            this.context=context;
        }

        @Override
        public void onClick(View v) {
            Intent intent= new Intent(context, BankBankReport.class);
            intent.putExtra("ReportID",accountReportResult);
            startActivity(intent);

        }
    }

}