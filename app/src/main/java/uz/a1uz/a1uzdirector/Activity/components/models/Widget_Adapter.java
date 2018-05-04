package uz.a1uz.a1uzdirector.Activity.components.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.AccountsTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.CreditTables.CreditTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.CurrentTables.CurrentConsumptionTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.DebitTables.DebitTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.ProceedsTables.ProceedsTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables.StoreTable;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

/**
 * Created by sh.khodjabaev on 09.04.2018.
 */

public class Widget_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Widget_item_model> items;
    private LayoutInflater inflater;



    public Widget_Adapter(Context context, ArrayList<Widget_item_model> items) {
        this.context = context;
        this.items = items;
        this.inflater= (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public Widget_item_model getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view =convertView;
       if(view==null) {
           view = inflater.inflate(R.layout.widget, parent, false);
       }
        Widget_item_model wg=getWidget(position);
        TextView TopTex         =(TextView   ) view.findViewById(R.id.TopText);
        ImageView ImageRight     =(ImageView  ) view.findViewById(R.id.ImageRight);
        TextView MiddleText     =(TextView   ) view.findViewById(R.id.MiddleText);
        Spinner LeftSpinner    =(Spinner    ) view.findViewById(R.id.left_spinner);
        TextView BottomLeftText =(TextView   ) view.findViewById(R.id.BottomLeftText);
        ImageButton LeftSpinImage  =(ImageButton) view.findViewById(R.id.left_spin_img);
        TextView BottomRightText=(TextView   ) view.findViewById(R.id.BottomRightText);
        ImageButton RightSpinImage =(ImageButton) view.findViewById(R.id.right_spin_img);
        Spinner RightSpinner   =(Spinner    ) view.findViewById(R.id.right_spinner);
        LinearLayout linearLayout   =(LinearLayout) view.findViewById(R.id.linearLayout);
        LinearLayout LeftBotomLayout=(LinearLayout)view.findViewById(R.id.left_bottom_layout);
        LinearLayout RightBotomLayout=(LinearLayout) view.findViewById(R.id.right_bottom_layout);

        SpinnerAddElements(wg, LeftSpinner, RightSpinner);

        SpinnerAddItems(LeftBotomLayout,LeftSpinImage,LeftSpinner,wg);
        TopTex.setText(wg.getTopText());
        MiddleText.setText(wg.getMiddleText());
        BottomLeftText.setText(wg.getBottomLeftText());
        BottomRightText.setText(wg.getBottomRightText());
        linearLayout.setBackgroundResource(wg.getBackgroundColor());
        linearLayout.setOnClickListener(new Widget_Adapter.LayoutClick(wg));

        return view;
    }

    private void SpinnerAddElements(Widget_item_model wg, Spinner leftSpinner, Spinner rightSpinner) {
        ArrayAdapter<WidgetDropDownItem> leftSpinAdap = new ArrayAdapter<>(
                context, R.layout.simple_spinner_item,wg.getBottomLeftItems());
        leftSpinAdap.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        leftSpinner.setAdapter(leftSpinAdap);
        leftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Clickk");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<WidgetDropDownItem> rightSpinAdap = new ArrayAdapter<>(
                context,R.layout.simple_spinner_item,wg.getBottomLeftItems());
        rightSpinAdap.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        rightSpinner.setAdapter(rightSpinAdap);
        rightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Clickk");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    Widget_item_model getWidget(int position){
        return ((Widget_item_model) getItem(position));
    }
    private void SpinnerAddItems(LinearLayout leftBotomLayout, ImageButton leftSpinImage, Spinner leftSpinner, Widget_item_model wg) {
        if(wg.getBottomLeftItems().length>0){
            leftBotomLayout.setOnClickListener(new Widget_Adapter.OnClick(leftSpinner));
            leftSpinImage.setVisibility(View.VISIBLE);
//            ArrayAdapter<WidgetDropDownItem> spinnerArrayAdapter = new ArrayAdapter<>(
//                    context,R.layout.spinner_item,wg.getBottomLeftItems());
//            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//            holder.LeftSpinner.setAdapter(spinnerArrayAdapter);
//           // holder.LeftSpinner.setOnItemSelectedListener(new ItemSelected(holder,wg));
//            holder.LeftSpinner.setOnItemSelectedListener(new ItemSelected(holder,wg));
        }
        if(wg.getBottomRightItems().length>0){

            //holder.RightBotomLayout.setOnClickListener(new OnClick(holder.RightSpinner));
           // holder.RightSpinImage.setVisibility(View.VISIBLE);
            //holder.RightSpinner.setAdapter(wg.getBottomRightItems());
        }

    }

    private class ItemSelected implements AdapterView.OnItemSelectedListener{
        Widget_item_Adapter.ViewHolder holder;
        Widget_item_model wg;
        public ItemSelected(Widget_item_Adapter.ViewHolder holder, Widget_item_model wg) {
            this.holder=holder;
            this.wg=wg;
        }

        /**
         *
         * @param parent
         * @param view
         * @param position
         * @param id
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            WidgetDropDownItem dropDownItem=(WidgetDropDownItem)parent.getSelectedItem();
            wg.setMiddle(dropDownItem.ButtonMiddle);
            wg.setMiddleText(dropDownItem.ButtonMiddleText);
            wg.setBottomLeftText(dropDownItem.ButtonMiddleText);
            holder.MiddleText.setText(wg.getMiddleText());
            holder.BottomLeftText.setText(wg.getBottomLeftText());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class OnClick implements View.OnClickListener{
        Spinner spinner;
        public OnClick(Spinner leftSpinner) {
            spinner=leftSpinner;
        }

        @Override
        public void onClick(View v) {
            spinner.performClick();
        }
    }





    private class LayoutClick implements View.OnClickListener {
        Widget_item_model wg;
        public LayoutClick(Widget_item_model wg) {
            this.wg=wg;
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (wg.getReportUrl()){
                case URL_cons.ACCOUNTREPORT:
                    intent= new Intent(context, AccountsTable.class);
                    context.startActivity(intent);
                    break;
                case URL_cons.STOREREPORT:
                    intent= new Intent(context, StoreTable.class);
                    context.startActivity(intent);break;
                case URL_cons.PROCCEDREPORT:
                    intent= new Intent(context, ProceedsTable.class);
                    context.startActivity(intent);
                    break;
                case URL_cons.CREDITREPORT:
                    intent= new Intent(context, CreditTable.class);
                    context.startActivity(intent);break;
                case URL_cons.DEBITREPORT:
                    intent= new Intent(context, DebitTable.class);
                    context.startActivity(intent);
                    break;

                case URL_cons.CURRENTCOSTREPORT: intent= new Intent(context, CurrentConsumptionTable.class);
                    context.startActivity(intent);break;
            }

        }
    }
}
