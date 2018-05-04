package uz.a1uz.a1uzdirector.constants;

import uz.a1uz.a1uzdirector.Helpers.UserInfo;

/**
 * Created by sh.khodjabaev on 05.03.2018.
 */

public final class URL_cons {
    public static final String
            LOGINRESULT= UserInfo.Url+
            "/rest/user/LogIn";

    public static final String PAYMENT="http://1uz.uz/ru/offer/?designname=director&inn=";


    public static final String SETDATABASES=UserInfo.Url+"/Rest/DataBase/SetDataBase/1"; // 1
    public static final String DATABASES = UserInfo.Url + "/Rest/DataBase/GetDataBases";
    /**
     *
     */
    public static final String GETBANKBUTTON=UserInfo.Url + "/rest/widget/GetBankButton";
        public static final String ACCOUNTREPORT=UserInfo.Url+"/Rest/bank/GetAccountsReport/";
            public static final String BANKREPORT=UserInfo.Url+"/Rest/bank/GetBankReport/";
                public static final String BANKPERIODREPORT=UserInfo.Url+"/Rest/bank/GetBankPeriodReport/";
    /**
     *
     */
    public static final String GETSTOREBUTTON=UserInfo.Url+"/Rest/widget/GetStoreButton/";
        public static final String STOREREPORT=UserInfo.Url+"/Rest/store/GetStoresReport/";
            public static final String STOREPERIODREPORT=UserInfo.Url+"/Rest/store/GetStorePeriodReport/";
    /**
     *
     */
    public static final String GETPROCCEDSBUTTON = UserInfo.Url + "/Rest/widget/GetProccedsButton/";
        public static final String PROCCEDREPORT=UserInfo.Url+"/Rest/proceeds/GetProceedsReport/";
            public static final String PROCCEDPERIODREPORT=UserInfo.Url+"/Rest/proceeds/GetProceedsPeriodReport/";
    /**
     *
     */
    public static final String GETCREDITBUTOON = UserInfo.Url + "/Rest/widget/GetCreditButton/";
        public static final String CREDITREPORT=UserInfo.Url+"/Rest/credit/GetCreditReport/";
            public static final String CREDITSECONDREPORT=UserInfo.Url+"/Rest/credit/GetCreditSecondReport/";
    /**
     *
     */
    public static final String GETDEBITBUTTON = UserInfo.Url + "/Rest/widget/GetDebitButton/";
        public static final String DEBITREPORT=UserInfo.Url+"/Rest/debit/GetDebitReport/";
            public static final String DEBITSECONDREPORT=UserInfo.Url+"/Rest/debit/GetDebitSecondReport/";
    /**
     *
     */
    public static final String GETRECURRENTCOSTSBUTTON = UserInfo.Url + "/Rest/widget/GetRecurrentCostsButton/";
        public static final String CURRENTCOSTREPORT=UserInfo.Url+"/Rest/currentconsumption/GetCurrentConsumptionReport/";
            public static final String CURRENTCOSTSECONDREPORT=UserInfo.Url+"/Rest/currentconsumption/GetSecondCurrentConsumptionReport/";

    //Important if add new widgets, add WIDGETE_NAME too;
    //WIDGETS_NAME for cache file
    public static final String[] WIDGETS_NAMES={"GETBANKBUTTON","GETSTOREBUTTON","GETPROCCEDSBUTTON","GETCREDITBUTOON","GETDEBITBUTTON","GETRECURRENTCOSTSBUTTON"};

    public static final String ACCOUNTFORBANK= UserInfo.Url+"/Rest/widget/GetAccountForBankButton/";
    public static final String STOWAGESFORSTORE=UserInfo.Url+"/Rest/widget/GetStowagesForStoreButton/";
    public static final String ACCOUNTID=UserInfo.Url+"/Rest/widget/GetBankButtonWithAccountId/";
    public static final String STOWAGEID=UserInfo.Url+"/Rest/widget/GetStoreButtonWithStowageId/";
    public static final String PROCCEDVALUE=UserInfo.Url+ "/Rest/widget/GetValueProccedsButton/";
}
