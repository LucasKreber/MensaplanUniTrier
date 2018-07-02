package de.verdion.mensaplan.HelperClasses;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Lucas on 28.03.2016.
 */
public class AverageColor {

    public static int calculateAverageColor(){
        BitmapDrawable bitmap = BitmapShare.getInstance().bitmapDrawable;
        int redBucket = 0;
        int greenBucket = 0;
        int blueBucket = 0;
        int pixelCount = 0;

        for (int i = 0; i < bitmap.getBitmap().getHeight(); i++) {
            for (int j = 0; j < bitmap.getBitmap().getWidth(); j++) {
                int c = bitmap.getBitmap().getPixel(j,i);
                pixelCount++;
                redBucket+= Color.red(c);
                greenBucket+=Color.green(c);
                blueBucket+=Color.blue(c);
            }
        }

        int red = (redBucket/pixelCount);
        int green = (greenBucket/pixelCount);
        int blue = (blueBucket/pixelCount);


        return Color.rgb(red,green,blue);
    }
}
