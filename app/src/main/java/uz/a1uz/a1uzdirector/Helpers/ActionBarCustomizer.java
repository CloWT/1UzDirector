package uz.a1uz.a1uzdirector.Helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.AccountsTable;
import uz.a1uz.a1uzdirector.R;

/**
 * Created by sh.khodjabaev on 17.04.2018.
 */

public class ActionBarCustomizer extends AppCompatActivity {
    AlertDialog.Builder builder;
    Intent alertInet;
    protected boolean homeButton=true;

   protected void setSubTitleC(String title){
       getSupportActionBar().setSubtitle(title);


   }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
                String languageToLoad = "ru"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                dialog.dismiss();



                finish();
                startActivity(getIntent());


            }
        });
        builder.setNegativeButton("Uz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog

                String languageToLoad = "uz"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        null);

                dialog.dismiss();



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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
