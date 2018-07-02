package de.verdion.mensaplan.DataHolder;

/**
 * Created by Lucas on 18.01.2017.
 */

public class SettingObject {

    public SettingObject(String title, String subtitle, boolean isSwitch) {
        this.title = title;
        this.subtitle = subtitle;
        this.isSwitch = isSwitch;
    }

    private String title;
    private String subtitle;
    private boolean isSwitch;

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setSwitch(boolean aSwitch) {
        isSwitch = aSwitch;
    }
}
