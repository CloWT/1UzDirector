package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashSet;
import java.util.Set;

import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.JsoN.JsonFileWriterReader;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

import static uz.a1uz.a1uzdirector.constants.URL_cons.WIDGETS_NAMES;

public class Login_Activity extends AppCompatActivity {
    public static final String INN_S_KEY = "INN";
    public static final String LASTINN = "INN_last";
    Button btLogin;
    AutoCompleteTextView INN;
    EditText etPass;
    SharedPreferences sPref;
    ArrayAdapter adapter;
    Context context;
    ToggleButton tgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sPref = getPreferences(MODE_PRIVATE);
        UserInfo.setLan(sPref.getString("lang", String.valueOf(UserInfo.EheaderLang.eUz)), getBaseContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_);
        etPass = (EditText) findViewById(R.id.password);
        btLogin = (Button) findViewById(R.id.btLogin);
        INN = (AutoCompleteTextView) findViewById(R.id.INN);
        tgButton = (ToggleButton) findViewById(R.id.toggleButton);
        context = this;

        tgButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (UserInfo.getLanE() == UserInfo.EheaderLang.eUz) return;

                    UserInfo.setLan(UserInfo.EheaderLang.eUz, getBaseContext());
                    SaveLang(UserInfo.getLan());
                    finish();
                    startActivity(getIntent());

                } else {
                    if (UserInfo.getLanE() == UserInfo.EheaderLang.eRu) return;
                    UserInfo.setLan(UserInfo.EheaderLang.eRu, getBaseContext());
                    SaveLang(UserInfo.getLan());
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        switch (UserInfo.getLanE()) {
            case eRu:
                tgButton.setChecked(false);
                break;
            case eUz:
                tgButton.setChecked(true);
                break;
        }
        Set<String> set = sPref.getStringSet(INN_S_KEY, null);

        if (set != null) {
            adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_dropdown_item, set.toArray(new String[set.size()]));
            INN.setAdapter(adapter);
        }

        mINN_Config();

    }

    private void mINN_Config() {
        INN.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        String inn = sPref.getString(LASTINN, "");
        INN.setText(inn);
        if (inn.length() > 2) etPass.requestFocus();
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
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.WiFiNoConnect, Toast.LENGTH_SHORT).show();
            return;
        }
        if (INN.getText().length() < 1 || etPass.getText().length() < 1) {
            //Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = String.format("%s/%s/%s/%s", URL_cons.LOGINRESULT, INN.getText().toString(), etPass.getText().toString(), UserInfo.getGUID());
        GetJson.execute(url, new IGetJsonResult() {
            @Override
            public void OnBegin() {
                ((Login_Activity) context).btLogin.setEnabled(false);


            }

            @Override
            public void OnProgressUpdate(int progress) {

            }

            @Override
            public void OnEnd(String[] jsonStringsArr) {

            }

            @Override
            public void OnError(Exception e) {
                Toast.makeText(context, "Error message"+e.getMessage(), Toast.LENGTH_LONG).show();

                ((Login_Activity) context).btLogin.setEnabled(true);
            }

            @Override
            public void OnEnd(JSONObject jsonObject) {
                Login_Activity.this.btLogin.setEnabled(true);
                try {
                    JSONObject c = (JSONObject) jsonObject.get("LogInResult");
                    if (c.getBoolean("IsOk") && c.getBoolean("IsAuthorization")) {
                        UserInfo.setINN(INN.getText().toString());
                        WriteClearCache();

                        mSaveINN(INN.getText().toString());

                        OpenAcitive();
                    } else {
                        Toast.makeText(Login_Activity.this, "" + c.getString("Message"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void WriteClearCache() {
        if (!INN.getText().toString().equals(sPref.getString(LASTINN, "")))
            for (String fileName : WIDGETS_NAMES) {
                JsonFileWriterReader jsonFileWriterReader = new JsonFileWriterReader(context, fileName);
                jsonFileWriterReader.mDeleteFile();
            }
        UserInfo.setWidgetListItems(null);
        UserInfo.setOrganizationName("");
        UserInfo.setLimitDate("");
    }

    void OpenAcitive() {
        Intent intent = new Intent(this, MainNavigation.class);
        startActivity(intent);
    }

    private void SaveLang(String lan) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("lang", lan);
        ed.apply();

    }

    void mSaveINN(String inn) {
        Set<String> set = sPref.getStringSet(INN_S_KEY, null);
        if (set == null) {
            set = new HashSet<String>();
        }
        set.add(inn);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putStringSet(INN_S_KEY, set);
        editor.putString(LASTINN, inn);
        editor.apply();
    }
}
