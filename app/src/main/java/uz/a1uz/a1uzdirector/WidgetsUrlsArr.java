package uz.a1uz.a1uzdirector;

/**
 * Created by sh.khodjabaev on 02.04.2018.
 */

public class WidgetsUrlsArr {
    private String urlButoon;
    private String urlReport;
    private String urlSecondReport;

    /**
     * @param urlButoon
     * @param urlReport
     * @param urlSecondReport
     */
    public WidgetsUrlsArr(String urlButoon, String urlReport, String urlSecondReport) {
        this.urlButoon = urlButoon;
        this.urlReport = urlReport;
        this.urlSecondReport = urlSecondReport;
    }

    public String getUrlButoon() {
        return urlButoon;
    }

    public String getUrlReport() {
        return urlReport;
    }

    public String getUrlSecondReport() {
        return urlSecondReport;
    }
}
