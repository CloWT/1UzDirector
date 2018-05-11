package uz.a1uz.a1uzdirector.Helpers.CustomDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import uz.a1uz.a1uzdirector.Activity.components.models.Widget_item_model;
import uz.a1uz.a1uzdirector.R;

/**
 * Created by sh.khodjabaev on 26.04.2018.
 */

public class CustomDialogClass extends Dialog implements View.OnClickListener {
    private ViewGroup group;
    private Activity context;
    private Widget_item_model widget_item_model;
    private int color;
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
        Button color1 = (Button) findViewById(R.id.color1);
        Button color2 = (Button) findViewById(R.id.color2);
        Button color3 = (Button) findViewById(R.id.color3);
        Button color4 = (Button) findViewById(R.id.color4);
        Button color5 = (Button) findViewById(R.id.color5);
        Button color6 = (Button) findViewById(R.id.color6);
        color1.setOnClickListener(this);
        color2.setOnClickListener(this);
        color3.setOnClickListener(this);
        color4.setOnClickListener(this);
        color5.setOnClickListener(this);
        color6.setOnClickListener(this);
    }


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
