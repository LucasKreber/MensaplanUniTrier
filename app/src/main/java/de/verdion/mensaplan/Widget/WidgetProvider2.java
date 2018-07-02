package de.verdion.mensaplan.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.verdion.mensaplan.MainActivity;
import de.verdion.mensaplan.R;

/**
 * Created by Lucas on 04.04.2016.
 */
public class WidgetProvider2 extends AppWidgetProvider {

    public static String EXTRA = "de.verdion.Mensaplan.widget2";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i=0; i<appWidgetIds.length; i++) {
            Intent svcIntent=new Intent(context, RemoteViewsService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            svcIntent.putExtra("Version", 1);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget=new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);


            ComponentName componentName = new ComponentName(context, WidgetProvider2.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widgetListView);
            appWidgetManager.updateAppWidget(componentName, widget);

            widget.setRemoteAdapter(appWidgetIds[i], R.id.widgetListView,
                    svcIntent);

            Intent clickIntent=new Intent(context, MainActivity.class);
            /*Bundle bundle = new Bundle();
            bundle.putInt("location", 1);
            clickIntent.setAction("FROM_WIDGET");
            clickIntent.putExtras(bundle);*/
            PendingIntent clickPI=PendingIntent
                    .getActivity(context, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.widgetListView, clickPI);

            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context,
                                             int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(),R.layout.activity_main);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, RemoteViewsService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.widgetListView,
                svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.widgetListView, R.id.empty_view);
        return remoteViews;
    }
}
