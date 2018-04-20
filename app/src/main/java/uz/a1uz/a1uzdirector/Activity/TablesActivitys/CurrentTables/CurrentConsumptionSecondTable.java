package uz.a1uz.a1uzdirector.Activity.TablesActivitys.CurrentTables;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

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

public class CurrentConsumptionSecondTable extends ActionBarCustomizer implements LayoutConfiguration {
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
        setSubTitleC("СТАТЬИ РАСХОДОВ");
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



        FirstLastDate datFL=new FirstLastDate(EdatePeriod.ThisMonth);
        beginDate=datFL.getFirstDate();
        endDate=datFL.getLastDate();

        tDatePeriod.setText(String.format("%s - %s",beginDate , endDate));
        String url= UrlHepler.Combine(URL_cons.CURRENTCOSTSECONDREPORT,ReportID+"",
                datFL.getFirstDate(),datFL.getLastDate(),
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

    }

    @Override
    public void AddElemsToTable(List reportResults) {

    }

    @Override
    public void CustomSetTex(TextView[] txV, Object periodResults) {
        for (TextView tx:txV) {
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tx.getLayoutParams();
            lp.setGravity(Gravity.FILL_HORIZONTAL);
            tx.setLayoutParams(lp);
        }

    }
}

