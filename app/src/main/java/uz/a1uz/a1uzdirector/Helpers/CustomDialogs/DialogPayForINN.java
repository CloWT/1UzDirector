package uz.a1uz.a1uzdirector.Helpers.CustomDialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import uz.a1uz.a1uzdirector.R;
import uz.a1uz.a1uzdirector.constants.URL_cons;

/**
 * Created by sh.khodjabaev on 15.05.2018.
 */

public class DialogPayForINN extends Dialog implements View.OnClickListener {
    Context context;
    private final int INN_LENGTH = 9;

    public DialogPayForINN(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    TextView tINN;
    TextView erorr_msg;
    Button bPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_pay_for_inn);
        tINN = (TextView) findViewById(R.id.INN);
        bPay = (Button) findViewById(R.id.btPay);
        erorr_msg = (TextView) findViewById(R.id.erorr_msg);
        bPay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String inn = tINN.getText().toString();

        if (inn.length() < INN_LENGTH) {
            erorr_msg.setVisibility(View.VISIBLE);
            erorr_msg.setText(String.format("%s%d", context.getString(R.string.innIsIncorrect), INN_LENGTH));

        } else {
            erorr_msg.setVisibility(View.GONE);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_cons.PAYMENT + inn));
            context.startActivity(browserIntent);
        }

    }
}
