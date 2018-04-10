package uz.a1uz.a1uzdirector.Activity.components.models;

/**
 * Created by sh.khodjabaev on 05.03.2018.
 */

public class WidgetDropDownItem {
    int Id;
    String ButtonMiddleText;
    double ButtonMiddle;
    String Name;

    public WidgetDropDownItem(int id, String buttonMiddleText,double buttonMiddle, String name) {
        Id = id;
        ButtonMiddleText = buttonMiddleText;
        Name = name;
        ButtonMiddle=buttonMiddle;
    }

    public WidgetDropDownItem(int reportDecriptionID, String name) {
        Id=reportDecriptionID;
        Name=name;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getButtonMiddleText() {
        return ButtonMiddleText;
    }
    public double getButtonMiddle() {
        return ButtonMiddle;
    }

    @Override
    public String toString() {
        return Name;
    }
}
