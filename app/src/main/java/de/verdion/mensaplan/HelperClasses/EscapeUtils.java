package de.verdion.mensaplan.HelperClasses;

/**
 * Created by Lucas on 26.03.2016.
 */
public class EscapeUtils {

    public static String unescape(String str){
        str = str.replace("u00fc", "ü");
        str = str.replace("u00dc", "Ü");
        str = str.replace("u00f6", "ö");
        str = str.replace("u00d6", "Ö");
        str = str.replace("u00e4", "ä");
        str = str.replace("u00c4", "Ä");
        str = str.replace("u00df", "ß");
        str = str.replace("u00e0", "à");
        str = str.replace("u00e1", "á");
        str = str.replace("u00e8", "è");
        str = str.replace("u00e9", "é");
        str = str.replace("u0022", "\"");
        return str;
    }
}
