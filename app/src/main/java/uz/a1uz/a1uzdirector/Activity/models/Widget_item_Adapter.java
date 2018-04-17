package uz.a1uz.a1uzdirector.Activity.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.AccountsTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.CreditTables.CreditTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.CurrentTables.CurrentConsumptionTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.DebitTables.DebitTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.ProceedsTables.ProceedsTable;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables.StoreTable;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

/**
 * Created by sh.khodjabaev on 09.03.2018.
 */

public class Widget_item_Adapter extends ArrayAdapter<Widget_item_model> implements View.OnClickListener {
    private Context context;
    private List<Widget_item_model> items;
    private LayoutInflater inflater;



    public Widget_item_Adapter(Context context, List<Widget_item_model> items) {
        super(context, R.layout.widget, items);
        this.context = context;
        this.items = items;
        this.inflater= (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onClick(View v) {



        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Widget_item_model dataModel=(Widget_item_model)object;


        switch (v.getId())
        {
            case R.id.left_spinner:
                if(dataModel.getBottomLeftItems().length>0) v.performClick();

                break;
        }
    }

    static class ViewHolder{
        TextView     TopTex          ;
        ImageView    ImageRight      ;
        TextView     MiddleText      ;
        Spinner      LeftSpinner     ;
        TextView     BottomLeftText  ;
        ImageButton  LeftSpinImage   ;
        TextView     BottomRightText ;
        ImageButton  RightSpinImage  ;
        Spinner      RightSpinner    ;
        LinearLayout linearLayout    ;
        LinearLayout LeftBotomLayout ;
        LinearLayout RightBotomLayout ;

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
         ViewHolder holder;

        View view =convertView;
        if(view==null) {

            view=inflater.inflate(R.layout.widget,parent,false);
            holder=new ViewHolder();
            holder.TopTex         =(TextView   ) view.findViewById(R.id.TopText);
            holder.ImageRight     =(ImageView  ) view.findViewById(R.id.ImageRight);
            holder.MiddleText     =(TextView   ) view.findViewById(R.id.MiddleText);
            holder.LeftSpinner    =(Spinner    ) view.findViewById(R.id.left_spinner);
            holder.BottomLeftText =(TextView   ) view.findViewById(R.id.BottomLeftText);
            holder.LeftSpinImage  =(ImageButton) view.findViewById(R.id.left_spin_img);
            holder.BottomRightText=(TextView   ) view.findViewById(R.id.BottomRightText);
            holder.RightSpinImage =(ImageButton) view.findViewById(R.id.right_spin_img);
            holder.RightSpinner   =(Spinner    ) view.findViewById(R.id.right_spinner);
            holder.linearLayout   =(LinearLayout) view.findViewById(R.id.linearLayout);
            holder.LeftBotomLayout=(LinearLayout)view.findViewById(R.id.left_bottom_layout);
            holder.RightBotomLayout=(LinearLayout) view.findViewById(R.id.right_bottom_layout);
            view.setTag(holder);


                }else {
            holder= (ViewHolder) view.getTag();
        }
        Widget_item_model wg=items.get(position);
        SpinnerAddItems(holder,wg);
        holder.linearLayout.setBackgroundResource(wg.getBackgroundColor());
        holder.linearLayout.setOnClickListener(new LayoutClick(wg));
        holder.TopTex.setText(wg.getTopText());
        holder.MiddleText.setText(wg.getMiddleText());
        holder.BottomLeftText.setText(wg.getBottomLeftText());
        holder.BottomRightText.setText(wg.getBottomRightText());


        return view;
    }

    /**
     *
     * @param holder
     * @param wg
     */
    private void SpinnerAddItems(ViewHolder holder, Widget_item_model wg) {
        if(wg.getBottomLeftItems().length>0){
            //
            holder.LeftBotomLayout.setOnClickListener(new OnClick(holder.LeftSpinner));
            holder.LeftSpinImage.setVisibility(View.VISIBLE);
            ArrayAdapter<WidgetDropDownItem> spinnerArrayAdapter = new ArrayAdapter<>(
                    context,R.layout.simple_spinner_item,wg.getBottomLeftItems());
            spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            holder.LeftSpinner.setAdapter(spinnerArrayAdapter);
            holder.LeftSpinner.setOnItemSelectedListener(new ItemSelected(holder,wg));
        }
        if(wg.getBottomRightItems().length>0){
            System.out.println();
            //holder.RightBotomLayout.setOnClickListener(new OnClick(holder.RightSpinner));
            holder.RightSpinImage.setVisibility(View.VISIBLE);
            //holder.RightSpinner.setAdapter(wg.getBottomRightItems());
        }

    }

    private class ItemSelected implements AdapterView.OnItemSelectedListener{
        ViewHolder holder;
        Widget_item_model wg;
        public ItemSelected(ViewHolder holder, Widget_item_model wg) {
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
            wg.setBottomLeftText(dropDownItem.getName());
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
