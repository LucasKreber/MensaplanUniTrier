package de.verdion.mensaplan.Logger;

/**
 * Created by Lucas on 26.03.2016.
 *
 */
public class CLog {

    private static String LOGTAG = "TestMsg";
    private static boolean OUTPUT = false;

    public static void p(String msg){
        if (OUTPUT)
            android.util.Log.d(LOGTAG, msg);
    }

    public static void p(int msg){
        if (OUTPUT)
            android.util.Log.d(LOGTAG, msg+"");
    }

    public static void p(String prefix, String msg, String suffix){
        if (prefix == null)
            prefix = "";

        if (suffix == null)
            suffix = "";

        if (OUTPUT)
            android.util.Log.d(LOGTAG, prefix + ": "+ msg + " " + suffix);
    }

    public static void p(String prefix, String msg) {
        if (prefix == null)
            prefix = "";


        if (OUTPUT)
            android.util.Log.d(LOGTAG, prefix + ": " + msg);
    }

    public static void p(String prefix, int msg) {
        if (prefix == null)
            prefix = "";


        if (OUTPUT)
            android.util.Log.d(LOGTAG, prefix + ": " + msg);
    }

    public static void p(String prefix, Object msg) {
        if (prefix == null)
            prefix = "";


        if (OUTPUT)
            android.util.Log.d(LOGTAG, prefix + ": " + msg);
    }

    public static void p(String prefix, int msg, String suffix){
        if (prefix == null)
            prefix = "";

        if (suffix == null)
            suffix = "";

        if (OUTPUT)
            android.util.Log.d(LOGTAG, prefix + ": "+ msg + " " + suffix);
    }

    public static void p(String prefix, Object msg, String suffix){
        if (prefix == null)
            prefix = "";

        if (suffix == null)
            suffix = "";

        if (OUTPUT)
            android.util.Log.d(LOGTAG, prefix + ": "+ msg + " " + suffix);
    }
}
