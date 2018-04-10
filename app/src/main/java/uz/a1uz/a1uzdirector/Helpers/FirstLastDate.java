package uz.a1uz.a1uzdirector.Helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.BankPeriodTable;
import uz.a1uz.a1uzdirector.EdatePeriod;

/**
 * Created by sh.khodjabaev on 03.04.2018.
 */

public class FirstLastDate{
    private String firstDate;
    private String lastDate;
    Context context;

    public FirstLastDate(EdatePeriod edatePeriod) {
        GetPeriodDate(edatePeriod);
    }
    public FirstLastDate(EdatePeriod edatePeriod, Context context) {
        this.context=context;
        GetPeriodDate(edatePeriod);
    }
    private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY", Locale.getDefault());
    public FirstLastDate() {

    }
    public static String CustomDateFormat(Calendar date){
        return dateFormat.format(date.getTime());
    }
    Calendar first = Calendar.getInstance();
    Calendar second = Calendar.getInstance();
    FirstLastDate dat = null;
    DatePickerDialog.OnDateSetListener dateF = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            first.set(Calendar.YEAR, year);
            first.set(Calendar.MONTH, monthOfYear);
            first.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new DatePickerDialog(context, dateS, second.get(Calendar.YEAR), second.get(Calendar.MONTH),
                    second.get(Calendar.DAY_OF_MONTH)).show();}

    };
    DatePickerDialog.OnDateSetListener dateS = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            second.set(Calendar.YEAR, year);
            second.set(Calendar.MONTH, monthOfYear);
            second.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            firstDate=dateFormat.format(first.getTime());
            lastDate=dateFormat.format(second.getTime());}

    };

    public FirstLastDate GetPeriodDate(EdatePeriod edatePeriod){
        switch (edatePeriod){
            case ThisDay:{
                this.firstDate=dateFormat.format(first.getTime());
                this.lastDate=dateFormat.format(second.getTime());
            }break;
            case LastDay:{
                first.add(Calendar.DATE,-1);
                second.add(Calendar.DATE,-1);
                this.firstDate=dateFormat.format(first.getTime());
                this.lastDate=dateFormat.format(second.getTime());
            }break;
            case ThisWeek:{
                first.set(Calendar.DAY_OF_WEEK,first.getFirstDayOfWeek());
                second.set(Calendar.DAY_OF_WEEK,1);
                this.firstDate=dateFormat.format(first.getTime());
                this.lastDate=dateFormat.format(second.getTime());

            }break;
            case LastWeek:{
                first.add(Calendar.WEEK_OF_YEAR,-1);
                first.set(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek());
                second.add(Calendar.WEEK_OF_YEAR,-1);
                second.set(Calendar.DAY_OF_WEEK,1);
                this.firstDate=dateFormat.format(first.getTime());
                this.lastDate=dateFormat.format(second.getTime());

            }break;
            case ThisMonth:{
                first.set(Calendar.DAY_OF_MONTH, 1);
                second.add(Calendar.MONTH,1);
                second.set(Calendar.DAY_OF_MONTH,0);
                this.firstDate=dateFormat.format(first.getTime());
                this.lastDate=dateFormat.format(second.getTime());

            }break;
            case LastMonth:{
                first.add(Calendar.MONTH,-1);
                first.set(Calendar.DAY_OF_MONTH, 1);
                second.add(Calendar.MONTH,0);
                second.set(Calendar.DAY_OF_MONTH,0);
                this.firstDate=dateFormat.format(first.getTime());
                this.lastDate=dateFormat.format(second.getTime());

            }break;
            case BeginYear:{
                first.set(Calendar.DAY_OF_YEAR,1);
                this.firstDate=dateFormat.format(first.getTime());
                this.lastDate=dateFormat.format(second.getTime());

            }break;        }

        return dat;
    }



;

    public String getFirstDate() {
        return firstDate;
    }

    public String getLastDate() {
        return lastDate;
    }


}


