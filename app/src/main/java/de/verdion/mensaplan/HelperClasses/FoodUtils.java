package de.verdion.mensaplan.HelperClasses;

import de.verdion.mensaplan.DataHolder.Config;

/**
 * Created by Lucas on 14.07.2016.
 *
 */
public class FoodUtils {

    public static boolean isDisable(String name){
        int position = -1;

        switch (name){
            case "BEILAGEN":
                position = 0;
                break;
            case "Tagessuppe":
                position = 1;
                break;
            case "UNTERGESCHOSS":
                position = 2;
                break;
            case "THEKE 1":
                position = 3;
                break;
            case "THEKE 2":
                position = 4;
                break;
            case "WOK forU":
                position = 5;
                break;
            case "KLEINE KARTE forU":
                position = 6;
                break;
            case "KLEINE KARTE":
                position = 7;
                break;
            case "ABENDMENSA":
                position = 8;
                break;
        }

        return position == -1 ? false : Config.CHOICES[position].equals("1");
    }

}
