package de.verdion.mensaplan.Network;

import android.appwidget.AppWidgetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import java.io.IOException;
import java.net.URL;

import de.verdion.mensaplan.Logger.CLog;

/**
 * Created by Lucas on 04.04.2016.
 */
public class LoadImageForWidget extends AsyncTask<String,Void,Bitmap> {

    private Bitmap bitmap;
    private RemoteViews view;
    private int WidgetID;
    private AppWidgetManager WidgetManager;




    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL imageURL = new URL("https://studiwerk.de/eo/scale?s=mensa_standard&p=" + params[0]);
            bitmap = BitmapFactory.decodeStream(imageURL.openStream());
        } catch (IOException e) {
            bitmap = null;
            CLog.p("ERROR", e.toString());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
