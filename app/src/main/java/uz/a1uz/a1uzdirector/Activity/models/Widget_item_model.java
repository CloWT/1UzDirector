package uz.a1uz.a1uzdirector.Activity.models;

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

    /**
     *
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
        this.backgroundColor=backgroundColor;
        ReportUrl=reportUrl;
        SecondReportUrl=secondReportUrl;

    }

    public Widget_item_model() {

    }

    public WidgetDropDownItem[] getBottomLeftItems() {
        return BottomLeftItems;
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

    public WidgetDropDownItem[] getBottomRightItems() {
        return BottomRightItems;
    }

    public String getBottomRightText() {
        return BottomRightText;
    }

    public String getMiddleText() {
        return MiddleText;
    }

    public String getTopText() {
        return TopText;
    }

    public double getMiddle() {
        return Middle;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
    public void setBottomLeftItems(WidgetDropDownItem[] bottomLeftItems) {
        BottomLeftItems = bottomLeftItems;
    }

    public void setBottomLeftText(String bottomLeftText) {
        BottomLeftText = bottomLeftText;
    }

    public void setBottomRightItems(WidgetDropDownItem[] bottomRightItems) {
        BottomRightItems = bottomRightItems;
    }

    public void setBottomRightText(String bottomRightText) {
        BottomRightText = bottomRightText;
    }

    public void setMiddle(double middle) {
        Middle = middle;
    }

    public void setMiddleText(String middleText) {
        MiddleText = middleText;
    }

    public void setTopText(String topText) {
        TopText = topText;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
