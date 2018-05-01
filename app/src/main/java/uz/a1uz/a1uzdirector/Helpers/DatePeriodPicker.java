package uz.a1uz.a1uzdirector.Helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uz.a1uz.a1uzdirector.Enums.EdatePeriod;
import uz.a1uz.a1uzdirector.R;

import static uz.a1uz.a1uzdirector.Helpers.FirstLastDate.CustomDateFormat;

public class DatePeriodPicker extends AppCompatActivity {
    DatePickerDialog dPDialogF,dPDialogS;
    String firstDate,secondDate;
    Calendar first;
    Calendar second;
    Spinner spinner;
    Context context;

    TextView fDate,sDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_period_picker);
        spinner=(Spinner)findViewById(R.id.dateSpin);
        SpinnerAction();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        fDate=(TextView)findViewById(R.id.firstDate);
        sDate=(TextView)findViewById(R.id.secondDate);
        first=Calendar.getInstance();
        second=Calendar.getInstance();
        SetTextDate(first,fDate);
        SetTextDate(second,sDate);



        dPDialogF=new DatePickerDialog(this,dateF,first.get(Calendar.YEAR),first.get(Calendar.MONTH),first.get(Calendar.DAY_OF_MONTH));
        dPDialogF.getDatePicker().setMaxDate(new Date().getTime());
        dPDialogS=new DatePickerDialog(this,dateS,second.get(Calendar.YEAR),second.get(Calendar.MONTH),second.get(Calendar.DAY_OF_MONTH));
        dPDialogS.getDatePicker().setMaxDate(new Date().getTime());




    }


    private void SpinnerAction() {

        EdatePeriod[] d=EdatePeriod.values(Locale.getDefault());
        ArrayAdapter<EdatePeriod> adapter=new ArrayAdapter<>(this,R.layout.simple_spinner_item,d);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EdatePeriod date=EdatePeriod.values()[position];

                FirstLastDate da=new FirstLastDate(date,context);
                first=da.first;
                second=da.second;
                SetTextDate(first,fDate);
                SetTextDate(second,sDate);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void SendDate(View view) {
        Intent intent = new Intent();
        String firstD=CustomDateFormat(first);
        String secondD=CustomDateFormat(second);
        intent.putExtra("fDate", firstD);
        intent.putExtra("sDate", secondD);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void PeriodChecker(View view) {
        switch (view.getId()){
            case R.id.Fir:{
                dPDialogF.show();
            }break;
            case R.id.Sec:{
                dPDialogS.show();
            }break;
        }

    }
    String tempDate;
    protected void SetTextDate(Calendar c, TextView date){
        tempDate=c.get(Calendar.DAY_OF_MONTH)+" "+c.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault())+" "+c.get(Calendar.YEAR);

        date.setText(tempDate);



    }

    DatePickerDialog.OnDateSetListener dateF = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            first.set(Calendar.YEAR, year);
            first.set(Calendar.MONTH, monthOfYear);
            first.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SetTextDate(first,fDate);

            }

    };
    DatePickerDialog.OnDateSetListener dateS = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            second.set(Calendar.YEAR, year);
            second.set(Calendar.MONTH, monthOfYear);
            second.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SetTextDate(second, sDate);
        }

    };


}
