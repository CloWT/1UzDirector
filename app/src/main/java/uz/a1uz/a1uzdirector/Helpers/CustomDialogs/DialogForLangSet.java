package uz.a1uz.a1uzdirector.Helpers.CustomDialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;

import uz.a1uz.a1uzdirector.Helpers.ActionBarCustomizer;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.R;

/**
 * Created by sh.khodjabaev on 30.04.2018.
 */

public class DialogForLangSet extends Dialog implements CompoundButton.OnCheckedChangeListener {
    Context context;
    AppCompatActivity activity;
    public DialogForLangSet(@NonNull Context context,AppCompatActivity appCompatActivity) {
        super(context);
        activity=appCompatActivity;
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_for_lang_pick);
        Switch swLangSwitcher=(Switch)findViewById(R.id.lang_switcher);
        swLangSwitcher.setOnCheckedChangeListener(this);
        switch (UserInfo.getLanE()){
            case eRu: swLangSwitcher.setChecked(true);  break;
            case eUz: swLangSwitcher.setChecked(false);break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            if(UserInfo.getLanE()== UserInfo.EheaderLang.eRu) return;

            UserInfo.setLan(UserInfo.EheaderLang.eRu,context);
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
        else {
            if(UserInfo.getLanE()== UserInfo.EheaderLang.eUz) return;

            UserInfo.setLan(UserInfo.EheaderLang.eUz,context);

            activity.finish();
            activity.startActivity(activity.getIntent());
        }



    }
}
