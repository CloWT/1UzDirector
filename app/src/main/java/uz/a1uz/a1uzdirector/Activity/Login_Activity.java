package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.constants.URL_cons;

public class Login_Activity extends AppCompatActivity {
    Button btLogin;
    AutoCompleteTextView INN;
    EditText etPass;
    SharedPreferences sPref;
    ArrayAdapter<String> adapter;
    String first="",second="",third="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        etPass=(EditText)findViewById(R.id.password);
        loadText();
        String[] cats = {first, second, third};
        List<String> catList = Arrays.asList(cats);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, catList);
        INN=(AutoCompleteTextView)findViewById(R.id.INN);
        INN.setAdapter(adapter);
        INN.setText(cats[0]);
        INN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INN.showDropDown();
            }
        });
        btLogin=(Button) findViewById(R.id.btLogin);
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
                Login_Activity.this.btLogin.setEnabled(false);


            }

            @Override
            public void OnProgressUpdate(int progress) {

            }

            @Override
            public void OnEnd(String[] jsonStringsArr) {

            }

            @Override
            public void OnError(Exception e) {
             //   Toast.makeText(Login_Activity.this, "Error message"+e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Exception ",e.getMessage());
                Login_Activity.this.btLogin.setEnabled(false);
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
        ed.putString("second", first.toString());
        ed.putString("third", second.toString());
        ed.commit();
        loadText();
        adapter.notifyDataSetChanged();}

    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        first=sPref.getString("first", "");
        second=sPref.getString("second", "");
        third=sPref.getString("third", "");


    }
}
