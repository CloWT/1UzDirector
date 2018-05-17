package uz.a1uz.a1uzdirector.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uz.a1uz.a1uzdirector.Activity.components.ButtonWidgetV2;
import uz.a1uz.a1uzdirector.Activity.components.models.Widget_item_Adapter;
import uz.a1uz.a1uzdirector.Activity.components.models.Widget_item_model;
import uz.a1uz.a1uzdirector.Helpers.CustomDialogs.DialogPayForINN;
import uz.a1uz.a1uzdirector.Helpers.FirstLastDate;
import uz.a1uz.a1uzdirector.Helpers.UrlHepler;
import uz.a1uz.a1uzdirector.Helpers.UserInfo;
import uz.a1uz.a1uzdirector.JsoN.GetJson;
import uz.a1uz.a1uzdirector.JsoN.IGetJsonResult;
import uz.a1uz.a1uzdirector.JsoN.JsonFileWriterReader;
import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.WidgetsUrlsArr;
import uz.a1uz.a1uzdirector.constants.URL_cons;

import static uz.a1uz.a1uzdirector.constants.URL_cons.ACCOUNTREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.BANKREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.CREDITREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.CREDITSECONDREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.CURRENTCOSTREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.CURRENTCOSTSECONDREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.DEBITREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.DEBITSECONDREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETBANKBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETCREDITBUTOON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETDEBITBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETPROCCEDSBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETRECURRENTCOSTSBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.GETSTOREBUTTON;
import static uz.a1uz.a1uzdirector.constants.URL_cons.PROCCEDPERIODREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.PROCCEDREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.STOREPERIODREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.STOREREPORT;
import static uz.a1uz.a1uzdirector.constants.URL_cons.WIDGETS_NAMES;

public class MainNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String GUID = "UserGuid";
    TextView navViewTitile;
    TextView navViewSubTitile;
    final int INN_LENGTH = 9;
    public List<Widget_item_model> items;
    public Widget_item_Adapter adapter;
    public ProgressBar progressBar;
    public WidgetsUrlsArr[] urlsArr =
            {
                    new WidgetsUrlsArr(GETBANKBUTTON, ACCOUNTREPORT, BANKREPORT),
                    new WidgetsUrlsArr(GETSTOREBUTTON, STOREREPORT, STOREPERIODREPORT),
                    new WidgetsUrlsArr(GETPROCCEDSBUTTON, PROCCEDREPORT, PROCCEDPERIODREPORT),
                    new WidgetsUrlsArr(GETCREDITBUTOON, CREDITREPORT, CREDITSECONDREPORT),
                    new WidgetsUrlsArr(GETDEBITBUTTON, DEBITREPORT, DEBITSECONDREPORT),
                    new WidgetsUrlsArr(GETRECURRENTCOSTSBUTTON, CURRENTCOSTREPORT, CURRENTCOSTSECONDREPORT)
            };
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigtation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navView = NavigationConfig(toolbar);
        navViewTitile = (TextView) navView.getHeaderView(0).findViewById(R.id.header_title);
        navViewSubTitile = (TextView) navView.getHeaderView(0).findViewById(R.id.header_subtitle);
        TextView currenDate = (TextView) findViewById(R.id.current_Date);

        currenDate.setText(FirstLastDate.CustomDateFormat(Calendar.getInstance()));

        progressBar = (ProgressBar) findViewById(R.id.progres);
        if (UserInfo.getWidgetListItems() != null) {
            items = UserInfo.getWidgetListItems();
            navViewTitile.setText(UserInfo.getOrganizationName());
            navViewSubTitile.setText(UserInfo.getLimitDate());

        } else {

            items = new ArrayList<>();
            GetJson.execute(UrlHepler.Combine(URL_cons.GETWEBUSERINFO, UserInfo.getGUID()), new IGetJsonResult() {
                @Override
                public void OnBegin() {

                }

                @Override
                public void OnProgressUpdate(int progress) {

                }

                @Override
                public void OnEnd(String[] jsonStringsArr) {

                }

                @Override
                public void OnEnd(JSONObject jsonObject) {
                    try {
                        JSONObject jc = (JSONObject) jsonObject.get("GetWebUserInfoResult");
                        if (jc.getBoolean("IsAuthorization")) {
                            UserInfo.setLimitDate(jc.getString("LimitDate"));
                            UserInfo.setOrganizationName(jc.getString("OrganizationName"));
                            navViewTitile.setText(UserInfo.getOrganizationName());
                            navViewSubTitile.setText(UserInfo.getLimitDate());
                        } else {
                            navViewTitile.setText("");
                            navViewSubTitile.setText(jc.getString("Message"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void OnError(Exception e) {

                }
            });

            new ButtonWidgetV2(this, urlsArr);
        }

        adapter = new Widget_item_Adapter(this, items);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.custList);

        listView.setDividerHeight(15);
        listView.setAdapter(adapter);
    }

    private NavigationView NavigationConfig(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        return navigationView;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("On: ", "Save");
        outState.putString(GUID, UserInfo.getGUID());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        UserInfo.setGUID(savedInstanceState.getString(GUID));
        Log.e("On: ", "Get");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_sync: {
                mUpdateCache();
                finish();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
            break;
            case R.id.nav_payment: {
                if (UserInfo.getINN() == null || UserInfo.getINN().length() < INN_LENGTH) {
                    DialogPayForINN dialog = new DialogPayForINN(this);
                    dialog.show();
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_cons.PAYMENT + UserInfo.getINN()));
                    startActivity(browserIntent);
                }
            }
            break;
            case R.id.nav_info: {

            }
            break;
            case R.id.nav_lock: {
                super.onBackPressed();
            }
            break;
            case R.id.nav_uz: {
                mUpdateCache();
                UserInfo.setLan(UserInfo.EheaderLang.eUz, getBaseContext());
                SaveLang(UserInfo.getLan());
                finish();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
            break;
            case R.id.nav_ru: {
                mUpdateCache();
                UserInfo.setLan(UserInfo.EheaderLang.eRu, getBaseContext());
                SaveLang(UserInfo.getLan());
                finish();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
            break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SaveLang(String lan) {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("lang", lan);
        ed.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sPref = getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = sPref.edit();
        if (items.size() > 0) {
            ed.putInt("w_bank", items.get(0).getBackgroundColor());
            ed.putInt("w_store", items.get(1).getBackgroundColor());
            ed.putInt("w_proceeds", items.get(2).getBackgroundColor());
            ed.putInt("w_credit", items.get(3).getBackgroundColor());
            ed.putInt("w_debit", items.get(4).getBackgroundColor());
            ed.putInt("w_currentconsumption", items.get(5).getBackgroundColor());
        }
        ed.apply();
    }

    private void mUpdateCache() {
        UserInfo.setWidgetListItems(null);
        UserInfo.setLimitDate("");
        UserInfo.setOrganizationName("");
        for (String fileName : WIDGETS_NAMES) {
            JsonFileWriterReader jsonFileWriterReader = new JsonFileWriterReader(this, fileName);
            jsonFileWriterReader.mDeleteFile();
        }

    }
}
