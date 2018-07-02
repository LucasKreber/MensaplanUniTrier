package de.verdion.mensaplan.HelperClasses;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

/**
 * Created by Lucas on 28.03.2016.
 */
public class BitmapShare {

    private static BitmapShare instance;
    public BitmapDrawable bitmapDrawable;

    private BitmapShare(){

    }

    public static BitmapShare getInstance(){
        if (BitmapShare.instance == null){
            BitmapShare.instance = new BitmapShare();
        }

        return BitmapShare.instance;
    }

}
