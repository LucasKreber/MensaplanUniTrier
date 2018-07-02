package de.verdion.mensaplan.Network;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.verdion.mensaplan.DataHolder.APIConfig;
import de.verdion.mensaplan.Logger.CLog;

/**
 * Created by Lucas on 04.04.2016.
 */
public class LoadWidgetData extends AsyncTask<Integer, Void, String> {

    private int location;
    private StringBuilder builder;


    @Override
    protected String doInBackground(Integer... params) {
        location = params[0];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        builder = new StringBuilder();
        CLog.p("Loading Data from Server");

        try {
            URL url = new URL(APIConfig.BASE_URL+APIConfig.ENDPOINT_API+APIConfig.GET_BEGIN+APIConfig.GET_KEY+APIConfig.API_KEY+APIConfig.GET_LOCATION+params[0]+APIConfig.GET_DATE+sdf.format(new Date()));
            BufferedReader buf = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while((line = buf.readLine()) != null){
                builder.append(line);
            }

            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
            CLog.p("Exception LoadMensaData", e.toString(),null);
        }

        return builder.toString();
    }

}
