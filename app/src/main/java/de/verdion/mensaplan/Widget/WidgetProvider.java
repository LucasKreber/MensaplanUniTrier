package de.verdion.mensaplan.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import de.verdion.mensaplan.MainActivity;
import de.verdion.mensaplan.R;

/**
 * Created by Lucas on 04.04.2016.
 */
public class WidgetProvider extends AppWidgetProvider {

    public static String EXTRA = "de.verdion.Mensaplan.widget";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i=0; i<appWidgetIds.length; i++) {
            Intent svcIntent=new Intent(context, RemoteViewsService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            //svcIntent.putExtra("Version", 0);
            svcIntent.putExtra("Version", 0);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget=new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);


            //SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd.MM");
            //widget.setTextViewText(R.id.widgetMainTitle, sdf.format(new Date()) + "\nMensa Tarforst");

            ComponentName componentName = new ComponentName(context, WidgetProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widgetListView);
            appWidgetManager.updateAppWidget(componentName,widget);

            widget.setRemoteAdapter(appWidgetIds[i], R.id.widgetListView,
                    svcIntent);

            Intent clickIntent=new Intent(context, MainActivity.class);
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
                context.getPackageName(),R.layout.widget_layout);

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
