package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import uz.a1uz.a1uzdirector.Helpers.ActionBarCustomizer;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class Login_Activity extends ActionBarCustomizer {
    Button btLogin;
    AutoCompleteTextView INN;
    EditText etPass;
    SharedPreferences sPref;
    ArrayAdapter<String> adapter;
    String first="",second="",third="";
    Context context;
    ToggleButton tgButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sPref = getPreferences(MODE_PRIVATE);
        UserInfo.setLan(sPref.getString("lang", String.valueOf(UserInfo.EheaderLang.eRu)),getBaseContext());
        System.out.println("OnCreate1");
        homeButton=false; // back button
        super.onCreate(savedInstanceState);
        System.out.println("OnCreate2");
        setContentView(R.layout.activity_login_);
        etPass=(EditText)findViewById(R.id.password);
        btLogin=(Button) findViewById(R.id.btLogin);
        INN=(AutoCompleteTextView)findViewById(R.id.INN);
        tgButton=(ToggleButton)findViewById(R.id.toggleButton);

        tgButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked) {
                   if(UserInfo.getLanE()== UserInfo.EheaderLang.eUz) return;

                    UserInfo.setLan(UserInfo.EheaderLang.eUz,getBaseContext());
                    SaveLang(UserInfo.getLan());
                    finish();
                    startActivity(getIntent());

                }
                else{
                   if(UserInfo.getLanE()== UserInfo.EheaderLang.eRu) return;
                    UserInfo.setLan(UserInfo.EheaderLang.eRu,getBaseContext());
                    SaveLang(UserInfo.getLan());
                    finish();
                    startActivity(getIntent());
                }
            }
        });





        Set<String> set = sPref.getStringSet("key", null);

        loadText();
        String[] cats = {first, second, third};
        List<String> catList = Arrays.asList(cats);
        context=this;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, catList);
        switch (UserInfo.getLanE()){
            case eRu:tgButton.setChecked(false);  break;
            case eUz: tgButton.setChecked(true);break;

        }
        INN.setAdapter(adapter);
        INN.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        INN.setText(cats[0]);
        INN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INN.showDropDown();
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void check(View view) {
        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Internet Or Wifi Not Connected", Toast.LENGTH_SHORT).show();
            return;
        }
        if(INN.getText().length()<1){
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String url= URL_cons.LOGINRESULT+"/"+INN.getText().toString()+"/"+etPass.getText().toString()+"/"+ UserInfo.getGUID();
        Log.e("URL",url);
        GetJson.execute(url, new IGetJsonResult() {
            @Override
            public void OnBegin() {
                ((Login_Activity)context).btLogin.setEnabled(false);


            }

            @Override
            public void OnProgressUpdate(int progress) {

            }

            @Override
            public void OnEnd(String[] jsonStringsArr) {

            }

            @Override
            public void OnError(Exception e) {
             //Toast.makeText(context, "Error message"+e.getMessage(), Toast.LENGTH_LONG).show();

                ((Login_Activity)context).btLogin.setEnabled(true);
            }

            @Override
            public void OnEnd(JSONObject jsonObject) {
                Login_Activity.this.btLogin.setEnabled(true);
                try {
                    JSONObject c= (JSONObject) jsonObject.get("LogInResult");
                   if(c.getBoolean("IsOk")&&c.getBoolean("IsAuthorization")){
                       saveText();
                       OpenAcitive();
                   }else {
                       Toast.makeText(Login_Activity.this, ""+c.getString("Message"), Toast.LENGTH_SHORT).show();
                   }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
    void OpenAcitive(){
        Intent intent= new Intent(this, Main_Activity.class);
        startActivity(intent);
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        String tmp=INN.getText().toString();
       if(tmp!=first||tmp!=second||tmp!=third) {
        ed.putString("first", tmp);
        ed.putString("second", first);
        ed.putString("third", second);
        ed.apply();
        loadText();
        adapter.notifyDataSetChanged();}

    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        first=sPref.getString("first", "");
        second=sPref.getString("second", "");
        third=sPref.getString("third", "");


    }
    private void SaveLang(String lan){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("lang", lan);
        ed.apply();

    }
}
