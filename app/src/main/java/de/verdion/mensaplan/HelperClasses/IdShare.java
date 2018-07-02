package de.verdion.mensaplan.HelperClasses;

/**
 * Created by Lucas on 31.03.2016.
 */
public class IdShare {
    private static IdShare instance;
    public String userid;
    public int pageId;

    private IdShare(){

    }

    public static IdShare getInstance(){
        if (IdShare.instance == null){
            IdShare.instance = new IdShare();
        }

        return IdShare.instance;
    }
}
