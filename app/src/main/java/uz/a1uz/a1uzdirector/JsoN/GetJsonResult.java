package uz.a1uz.a1uzdirector.JsoN;

import android.app.Activity;
import android.content.Context;

/**
 * Created by sh.khodjabaev on 12.03.2018.
 */

public abstract class GetJsonResult implements IGetJsonResult {
    public Context context;
    public GetJsonResult(Context context) {
        this.context=context;
    }

    public GetJsonResult() {
    }

    @Override
    public void OnError(Exception e) {
        new Throwable(String.format("%s", e));
    }
}
