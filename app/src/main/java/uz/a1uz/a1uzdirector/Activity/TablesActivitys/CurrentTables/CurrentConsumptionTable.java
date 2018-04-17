package uz.a1uz.a1uzdirector.Activity.TablesActivitys.CurrentTables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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
import uz.a1uz.a1uzdirector.Helpers.DatePeriodPicker;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class CurrentConsumptionTable extends ActionBarCustomizer {
    GridLayout periodTable;
    Context context;
    ProgressBar progressBar;
    Intent inDatePicker;
    int requesCodeForDatePicker=3;
    TextView tDatePeriod;
    String beginDate,endDate;
    Intent secondConsumTable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_consumption_table);
        context=this;
        periodTable=(GridLayout)findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        tDatePeriod=(TextView) findViewById(R.id.tDatePeriod);

        inDatePicker=new Intent(context, DatePeriodPicker.class);
        secondConsumTable=new Intent(context,CurrentConsumptionSecondTable.class);

        FirstLastDate datFL=new FirstLastDate(EdatePeriod.ThisMonth);
        beginDate=datFL.getFirstDate();
        endDate=datFL.getLastDate();

        tDatePeriod.setText(String.format("%s - %s",beginDate , endDate));

        getFromJson(UrlHepler.Combine(URL_cons.CURRENTCOSTREPORT,datFL.getFirstDate(),datFL.getLastDate(),
                UserInfo.getGUID()));


    }

    private void getFromJson(String url) {
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
                    JSONArray js=jsonObject.getJSONArray("GetCurrentConsumptionReportResult");



                    ((TextView)findViewById(R.id.curr1)).setText(js.getString(0));
                    ((TextView)findViewById(R.id.curr2)).setText(js.getString(1));
                    ((TextView)findViewById(R.id.curr3)).setText(js.getString(2));
                    ((TextView)findViewById(R.id.curr4)).setText(js.getString(3));
                    ((TextView)findViewById(R.id.curr5)).setText(js.getString(4));
                    ((TextView)findViewById(R.id.curr6)).setText(js.getString(5));
                    ((TextView)findViewById(R.id.curr7)).setText(js.getString(6));
                    ((TextView)findViewById(R.id.curr8)).setText(js.getString(7));
                    ((TextView)findViewById(R.id.curr9)).setText(js.getString(8));
                    ((TextView)findViewById(R.id.curr10)).setText(js.getString(9));
                    ((TextView)findViewById(R.id.curr11)).setText(js.getString(10));
                    ((TextView)findViewById(R.id.curr12)).setText(js.getString(11));
                    ((TextView)findViewById(R.id.curr13)).setText(js.getString(12));
                    ((TextView)findViewById(R.id.curr14)).setText(js.getString(13));
                    ((TextView)findViewById(R.id.curr15)).setText(js.getString(14));
                    ((TextView)findViewById(R.id.curr16)).setText(js.getString(15));
                    ((TextView)findViewById(R.id.curr17)).setText(js.getString(16));


                }
                catch (JSONException ignored){

                }

            }

            @Override
            public void OnError(Exception e) {
                progressBar.setVisibility(View.VISIBLE);


            }
        };

        GetJson.execute(url,logic);

    }

    public void Click1(View view) {
        sendDataToSecondTable(1,getString(R.string.SebeStoimostGotovyProduksii));
    }
    public void Click2(View view) {
        sendDataToSecondTable(2,getString(R.string.SebeStoimostRealizoTovar));
    }
    public void Click3(View view) {
        sendDataToSecondTable(3,getString(R.string.SebeStoimVypolnenRabotiUsl));
    }
    public void Click4(View view) {
        sendDataToSecondTable(4,getString(R.string.PoRealizatsii));
    }
    public void Click5(View view) {
        sendDataToSecondTable(5,getString(R.string.AdministratiroRasxod));
    }
    public void Click6(View view) {
        sendDataToSecondTable(6,getString(R.string.ProchieOperatRasxodi));
    }
    public void Click7(View view) {
        sendDataToSecondTable(7,getString(R.string.VychitaemIzNalogoPribilivBudushem));
    }

    private void sendDataToSecondTable(int value, String name) {
        secondConsumTable.removeExtra("id");
        secondConsumTable.removeExtra("bDate"); //begin Date
        secondConsumTable.removeExtra("eDate"); // end Date
        secondConsumTable.removeExtra("Name");

        secondConsumTable.putExtra("bDate", beginDate);
        secondConsumTable.putExtra("eDate", endDate);
        secondConsumTable.putExtra("id", value);
        secondConsumTable.putExtra("Name",name);
        startActivity(secondConsumTable);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null){

            return;}
        if(requestCode==requesCodeForDatePicker && resultCode==RESULT_OK){
            beginDate=data.getStringExtra("fDate");
            endDate=data.getStringExtra("sDate");
            String url = UrlHepler.Combine(URL_cons.CURRENTCOSTREPORT, beginDate,
                    endDate,
                    UserInfo.getGUID());
            getFromJson(url);
        }
    }

    public void tDateClick(View view) {
        startActivityForResult(inDatePicker,requesCodeForDatePicker);
    }
}

