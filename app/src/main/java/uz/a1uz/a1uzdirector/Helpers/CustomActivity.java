package uz.a1uz.a1uzdirector.Helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.AccountsTable;
import uz.a1uz.a1uzdirector.Helpers.CustomDialogs.DialogForLangSet;
import uz.a1uz.a1uzdirector.JsoN.JsonFileWriterReader;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

import static uz.a1uz.a1uzdirector.constants.URL_cons.WIDGETS_NAMES;

/**
 * Created by sh.khodjabaev on 17.04.2018.
 */

public class CustomActivity extends AppCompatActivity {
    protected AlertDialog.Builder builder;
    DialogForLangSet diLangSet;
    Intent alertInet;
    protected int tableBodyTextSize =14;
    protected boolean homeButton=true;

   protected void setSubTitleC(String title){
       getSupportActionBar().setSubtitle(title);


   }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sPref = getPreferences(MODE_PRIVATE);
        UserInfo.setLan(sPref.getString("lang", String.valueOf(UserInfo.getLanE())),getBaseContext());
        super.onCreate(savedInstanceState);
        diLangSet=new DialogForLangSet(this,this);
        builder = new AlertDialog.Builder(this);
        AlertBuild();


    }



    @Override
    protected void onStart() {
        super.onStart();
        if(homeButton)EnableHomeButton();
    }

    private void EnableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.left_arrow);
    }

    private void AlertBuild() {
        builder.setTitle("");
        // Add the buttons
        builder.setPositiveButton("ru", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                UserInfo.setLan(UserInfo.EheaderLang.eRu,getBaseContext());
                SaveLang(UserInfo.getLan());
                finish();
                startActivity(getIntent());


            }
        });
        builder.setNegativeButton("Uz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                UserInfo.setLan(UserInfo.EheaderLang.eUz,getBaseContext());
                SaveLang(UserInfo.getLan());
                finish();
              startActivity(getIntent());


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
            case android.R.id.home: finish(); break;
           case R.id.languageSet: builder.show();break;
           case R.id.payForDirecotr:
               Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_cons.PAYMENT+UserInfo.getINN()));
               startActivity(browserIntent);
               break;
           case R.id.updatActivty:
               UserInfo.setWidgetListItems(null);
               UserInfo.setLimitDate("");
               UserInfo.setOrganizationName("");
               mUpdateCache();
               finish();
               startActivity(getIntent());
               break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    SharedPreferences sPref;
    private void SaveLang(String lan){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("lang", lan);
            ed.apply();
    }
    private void mUpdateCache(){
        for (String fileName:WIDGETS_NAMES) {
            JsonFileWriterReader jsonFileWriterReader=new JsonFileWriterReader(this,fileName);
            jsonFileWriterReader.mDeleteFile();
        }

    }
}
