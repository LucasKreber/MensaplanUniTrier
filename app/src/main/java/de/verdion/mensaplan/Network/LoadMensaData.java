package de.verdion.mensaplan.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.verdion.mensaplan.Adapter.ListViewAdapter;
import de.verdion.mensaplan.DataHolder.APIConfig;
import de.verdion.mensaplan.DataHolder.DataParser;
import de.verdion.mensaplan.Logger.CLog;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Lucas on 22.03.2016.
 */
public class LoadMensaData extends AsyncTask<String,Void,Void> {
    private StringBuilder builder,builder2;
    private Context context;
    private StickyListHeadersListView listView;
    private ProgressBar progressBar;
    private boolean error = false;
    private int location;
    private Date date;

    public LoadMensaData(Context context, StickyListHeadersListView listView, ProgressBar progressBar,int location){
        this.context = context;
        this.listView = listView;
        this.progressBar = progressBar;
        this.location = location;
    }

    @Override
    protected Void doInBackground(String... params) {

        location = Integer.parseInt(params[0]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        builder = new StringBuilder();
        builder2 = new StringBuilder();
        CLog.p("Loading Data from Server");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        try {
            if ((params[0].equals("0") || params[0].equals("2")) && hourOfDay >= 15) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                date = calendar.getTime();
            } else if (params[0].equals("1") && hourOfDay >= 20) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                date = calendar.getTime();
            }

            if (date == null)
                date = new Date();

            URL url = new URL(APIConfig.BASE_URL+APIConfig.ENDPOINT_API+APIConfig.GET_BEGIN+APIConfig.GET_KEY+APIConfig.API_KEY+APIConfig.GET_LOCATION+params[0]+APIConfig.GET_DATE+sdf.format(date));
            BufferedReader buf = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while((line = buf.readLine()) != null){
                builder.append(line);
            }

            buf.close();
            DataParser.parse(builder.toString(), Integer.parseInt(params[0]), context);
        } catch (IOException e) {
            e.printStackTrace();
            error = true;
            CLog.p("Exception LoadMensaData", e.toString(),null);
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (!error){
            progressBar.setVisibility(View.GONE);
            ListViewAdapter adapter = new ListViewAdapter(context,location);
            ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
            StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
            stickyListHeadersAdapterDecorator.setStickyListHeadersListView(listView);
            listView.setAdapter(stickyListHeadersAdapterDecorator);
        } else {
            Toast.makeText(context, "Ein Fehler ist aufgetreten. Prüfe deine Internetverbindung", Toast.LENGTH_LONG).show();
        }

        return;
    }
}
