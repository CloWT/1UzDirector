package uz.a1uz.a1uzdirector.Helpers;

import java.util.List;

import uz.a1uz.a1uzdirector.Activity.TablesActivitys.BankTables.models.AccountReportResult;
import uz.a1uz.a1uzdirector.Activity.models.WidgetDropDownItem;

/**
 * Created by sh.khodjabaev on 02.04.2018.
 */

public class Memory_tmp {
    private static List<AccountReportResult> reportResults;
    private static List<WidgetDropDownItem> reportResults_dropdownList;

    public static List<WidgetDropDownItem> getReportResults_dropdownList() {
        return reportResults_dropdownList;
    }

    public static void setReportResults_dropdownList(List<WidgetDropDownItem> reportResults_dropdownList) {
        Memory_tmp.reportResults_dropdownList = reportResults_dropdownList;
    }
    public static String getDropDown(int reportId){
        for (WidgetDropDownItem d :reportResults_dropdownList) {
            if(d.getId()==reportId) return d.getName();
        }
        return "";
    }



    public static List<AccountReportResult> getReportResults() {
        return reportResults;
    }

    public static void setReportResults(List<AccountReportResult> reportResults) {
        Memory_tmp.reportResults = reportResults;
    }
}
