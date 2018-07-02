package de.verdion.mensaplan.DataHolder;

/**
 * Created by Lucas on 04.11.2016.
 */

public class MessageObject {

    private String title;
    private String message;
    private String buttonTitle;
    private int version;
    private int cancelable;

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public int isCancelable() {
        return cancelable;
    }

    public int getVersion() {
        return version;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setCancelable(int cancelable) {
        this.cancelable = cancelable;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }


    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }
}
