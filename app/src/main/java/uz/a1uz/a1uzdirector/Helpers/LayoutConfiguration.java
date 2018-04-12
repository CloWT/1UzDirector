package uz.a1uz.a1uzdirector.Helpers;

import android.widget.TextView;

import java.util.List;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.models.AccountReportResult;
import uz.a1uz.a1uzdirector.Activity.TablesActivitys.StoreTables.models.StorePeriodResult;

/**
 * Created by sh.khodjabaev on 12.04.2018.
 */

public interface LayoutConfiguration<T> {
    void AddElemsToTable(List<T> reportResults);
    void CustomSetTex(TextView[] txV, T periodResults);
    void CustomLayoutParams(TextView[] txV);
}
