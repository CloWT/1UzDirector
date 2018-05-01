package uz.a1uz.a1uzdirector.Helpers.CustomDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import uz.a1uz.a1uzdirector.Activity.models.Widget_item_model;
import uz.a1uz.a1uzdirector.R;

/**
 * Created by sh.khodjabaev on 26.04.2018.
 */

public class CustomDialogClass extends Dialog implements View.OnClickListener {
    Button color1,color2,color3,color4,color5,color6;
    ViewGroup group;
    Activity context;
    Widget_item_model widget_item_model;
    public CustomDialogClass(@NonNull Activity context, ViewGroup g, Widget_item_model wg) {
        super(context);
        this.context=context;
        widget_item_model=wg;
        group=g;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_for_color_picker);
        color1=(Button)findViewById(R.id.color1);
        color2=(Button)findViewById(R.id.color2);
        color3=(Button)findViewById(R.id.color3);
        color4=(Button)findViewById(R.id.color4);
        color5=(Button)findViewById(R.id.color5);
        color6=(Button)findViewById(R.id.color6);
        color1.setOnClickListener(this);
        color2.setOnClickListener(this);
        color3.setOnClickListener(this);
        color4.setOnClickListener(this);
        color5.setOnClickListener(this);
        color6.setOnClickListener(this);
    }
    int color;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.color1:color=R.color.bgc1;break;
            case R.id.color2:color=R.color.bgc2;break;
            case R.id.color3:color=R.color.bgc3;break;
            case R.id.color4:color=R.color.bgc4;break;
            case R.id.color5:color=R.color.bgc5;break;
            case R.id.color6:color=R.color.bgc6;break;
        }
        widget_item_model.setBackgroundColor(color);
        group.setBackgroundResource(color);


        dismiss();

    }
}
