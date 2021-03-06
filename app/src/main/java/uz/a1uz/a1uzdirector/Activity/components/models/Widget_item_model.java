package uz.a1uz.a1uzdirector.Activity.components.models;

import java.io.Serializable;

/**
 * Created by sh.khodjabaev on 05.03.2018.
 */

public class Widget_item_model implements Serializable {
    private WidgetDropDownItem[] BottomLeftItems;
    //    private ArrayAdapter<WidgetDropDownItem> BottomLeftItems;
    private String BottomLeftText;
    //private ArrayAdapter<WidgetDropDownItem> BottomRightItems;
    private WidgetDropDownItem[] BottomRightItems;
    private String BottomRightText;
    private double Middle;
    private String MiddleText;
    private String TopText;
    private int backgroundColor;
    private String ReportUrl;
    private String SecondReportUrl;
    private boolean isError=false;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    /**
     * @param bottomLeftItems
     * @param bottomLeftText
     * @param bottomRightItems
     * @param bottomRightText
     * @param middle
     * @param middleText
     * @param topText
     * @param backgroundColor
     * @param reportUrl
     * @param secondReportUrl
     */
    public Widget_item_model(WidgetDropDownItem[] bottomLeftItems,
                             String bottomLeftText,
                             WidgetDropDownItem[] bottomRightItems,
                             String bottomRightText,
                             double middle,
                             String middleText,
                             String topText,
                             int backgroundColor, String reportUrl, String secondReportUrl) {
        BottomLeftItems = bottomLeftItems;
        BottomLeftText = bottomLeftText;
        BottomRightItems = bottomRightItems;
        BottomRightText = bottomRightText;
        Middle = middle;
        MiddleText = middleText;
        TopText = topText;
        this.backgroundColor = backgroundColor;
        ReportUrl = reportUrl;
        SecondReportUrl = secondReportUrl;

    }

    public Widget_item_model() {

    }

    public WidgetDropDownItem[] getBottomLeftItems() {
        return BottomLeftItems;
    }

    public void setBottomLeftItems(WidgetDropDownItem[] bottomLeftItems) {
        BottomLeftItems = bottomLeftItems;
    }

    public String getReportUrl() {
        return ReportUrl;
    }

    public String getSecondReportUrl() {
        return SecondReportUrl;
    }

    public String getBottomLeftText() {
        return BottomLeftText;
    }

    public void setBottomLeftText(String bottomLeftText) {
        BottomLeftText = bottomLeftText;
    }

    public WidgetDropDownItem[] getBottomRightItems() {
        return BottomRightItems;
    }

    public void setBottomRightItems(WidgetDropDownItem[] bottomRightItems) {
        BottomRightItems = bottomRightItems;
    }

    public String getBottomRightText() {
        return BottomRightText;
    }

    public void setBottomRightText(String bottomRightText) {
        BottomRightText = bottomRightText;
    }

    public String getMiddleText() {
        return MiddleText;
    }

    public void setMiddleText(String middleText) {
        MiddleText = middleText;
    }

    public String getTopText() {
        return TopText;
    }

    public void setTopText(String topText) {
        TopText = topText;
    }

    public double getMiddle() {
        return Middle;
    }

    public void setMiddle(double middle) {
        Middle = middle;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
