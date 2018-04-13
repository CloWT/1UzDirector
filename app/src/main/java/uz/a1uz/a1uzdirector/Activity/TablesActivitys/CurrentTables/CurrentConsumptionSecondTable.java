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

import java.util.List;

import uz.a1uz.a1uzdirector.EdatePeriod;
import uz.a1uz.a1uzdirector.Helpers.DatePeriodPicker;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.LayoutConfiguration;
import uz.a1uz.a1uzdirector.R;

public class CurrentConsumptionSecondTable extends AppCompatActivity implements LayoutConfiguration {
    GridLayout periodTable;
    Context context;
    ProgressBar progressBar;
    Intent inDatePicker;
    int requesCodeForDatePicker=3;
    TextView tDatePeriod,tForName;
    String beginDate,endDate;
    Intent inParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_consumption_second_table);
        context=this;
        periodTable=(GridLayout)findViewById(R.id.periodTable);
        progressBar=(ProgressBar)findViewById(R.id.progres);
        tDatePeriod=(TextView) findViewById(R.id.tDatePeriod);
        tForName=(TextView) findViewById(R.id.forName);

        inDatePicker=new Intent(context, DatePeriodPicker.class);
        inParent=getIntent();

        inParent.getIntExtra("id",-1);
        tForName.setText(inParent.getStringExtra("Name"));
        beginDate=inParent.getStringExtra("bDate");
        endDate=inParent.getStringExtra("eDate");



        FirstLastDate datFL=new FirstLastDate(EdatePeriod.ThisMonth);
        beginDate=datFL.getFirstDate();
        endDate=datFL.getLastDate();

        tDatePeriod.setText(String.format("%s - %s",beginDate , endDate));
    }

    public void tDateClick(View view) {
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

