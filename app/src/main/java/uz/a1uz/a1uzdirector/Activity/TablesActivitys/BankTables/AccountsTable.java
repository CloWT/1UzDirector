package uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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
import java.util.Locale;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.models.AccountReportResult;
import uz.a1uz.a1uzdirector.Activity.models.WidgetDropDownItem;
import uz.a1uz.a1uzdirector.Helpers.ActionBarCustomizer;
import uz.a1uz.a1uzdirector.Helpers.LayoutConfiguration;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.Helpers.Memory_tmp;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class AccountsTable extends ActionBarCustomizer implements LayoutConfiguration<AccountReportResult> {
String url= UrlHepler.Combine(URL_cons.ACCOUNTREPORT, UserInfo.getGUID());
TableLayout bankTable;
Context context;
ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
//        UserInfo.setLan(sPref.getString("lang", String.valueOf(UserInfo.EheaderLang.eRu)),getBaseContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accounts_table);


        context=this;
        System.out.println("OnCreat");
        setSubTitleC(getString(R.string.Bank));
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
                            getString(R.string.total),
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

    @Override
    public void AddElemsToTable(List<AccountReportResult> accountReportResults){
        TextView TW[][]=new TextView[5][5];
        TableRow[] TR=new TableRow[accountReportResults.size()];
        for (int i = 0; i < accountReportResults.size(); i++) {
            TR[i]=new TableRow(this);

            for (int j = 0; j <TW[0].length ; j++) {
                TW[i][j]=new TextView(this);
            }

            CustomSetTex(TW[i],accountReportResults.get(i));

            for (int j = 0; j <5 ; j++) {
                TW[i][j].setGravity(j==0? Gravity.START:Gravity.END);
                TW[i][j].setBackgroundResource(R.drawable.border_shape);
                if(accountReportResults.size()-1==i){
                    TW[i][j].setTextColor(ContextCompat.getColor(this,R.color.textColor));
                    TW[i][j].setBackgroundResource(R.color.tableTopColor);
                }
                if (j==0&&i<accountReportResults.size()-1)
                    TW[i][j].setTextColor(ContextCompat.getColor(this,R.color.blue));
                TW[i][j].setPadding(10,10,10,10);
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

    @Override
    public void CustomSetTex(TextView[] txV, AccountReportResult periodResults) {
        txV[0].setText(periodResults.getName());
        txV[1].setText(periodResults.getBeginPeriodSum());
        txV[2].setText(periodResults.getInSum());
        txV[3].setText(periodResults.getOutSum());
        txV[4].setText(periodResults.getCurrentSum());
    }

    @Override
    public void CustomLayoutParams(TextView[] txV) {

    }

    private class ClickT implements View.OnClickListener {
        int accountReportResult;
        Context context;
        ClickT(int accountReportResult, Context context) {
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
