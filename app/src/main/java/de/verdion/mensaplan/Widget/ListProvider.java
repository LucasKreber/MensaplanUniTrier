package de.verdion.mensaplan.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.*;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import de.verdion.mensaplan.DataHolder.DataParserWidget;
import de.verdion.mensaplan.DataHolder.MahlzeitObject;
import de.verdion.mensaplan.DataHolder.TagesTheken;
import de.verdion.mensaplan.DataHolder.ThekenObject;
import de.verdion.mensaplan.HelperClasses.EscapeUtils;
import de.verdion.mensaplan.Logger.CLog;
import de.verdion.mensaplan.Network.LoadImageForWidget;
import de.verdion.mensaplan.Network.LoadWidgetData;
import de.verdion.mensaplan.R;

/**
 * Created by Lucas on 04.04.2016.
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Intent intent;
    private int appWidgetId;
    private AppWidgetManager appWidgetManager;
    private ArrayList<TagesTheken> list;
    private ArrayList<MahlzeitObject> allInOneList;
    private Bitmap[] bitmaps;
    private int version;
    private String[] locationNames = {"Mensa Tarforst", "Bistro A/B", "Mensa Petrisberg"};

    public ListProvider(Context context,Intent intent, int version) {
        this.context = context;
        this.intent = intent;
        this.version = version;
        appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        loadData();
    }

    @Override
    public void onDataSetChanged() {
        loadData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return allInOneList.size();
    }

    @Override
    public RemoteViews getViewAt(final int position) {
        CLog.p("getViewAt");
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_row);
        if (allInOneList.get(position).getId() == -28){
            remoteViews.setTextViewText(R.id.widgetTitle, "Die Mensa hat heute geschlossen");
            remoteViews.setTextViewText(R.id.widgetLocation, "");
            //
        } else {
            remoteViews.setTextViewText(R.id.widgetTitle, EscapeUtils.unescape(allInOneList.get(position).getLabel()));
            remoteViews.setTextViewText(R.id.widgetLocation, allInOneList.get(position).getThekenLabel());

            String price = allInOneList.get(position).getPrice()[0]+"";
            if (price.length() == 3){
                price+="0€";
            } else {
                price+="€";
            }
            remoteViews.setTextViewText(R.id.widgetPrice, price);

            if (position % 2 == 0){
                remoteViews.setInt(R.id.widgetBackground, "setBackgroundColor", Color.WHITE);
            } else {
                remoteViews.setInt(R.id.widgetBackground, "setBackgroundColor", Color.parseColor("#F1F8E9"));
            }

            if (bitmaps[position] == null){
                try {
                    bitmaps[position] = getCircleBitmap(new LoadImageForWidget().execute(allInOneList.get(position).getHauptspeise().get(0).getUrl()).get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            if (bitmaps[position] != null)
                remoteViews.setImageViewBitmap(R.id.widgetIcon, bitmaps[position]);
        }





        //new LoadImageForWidget(remoteViews,appWidgetId,appWidgetManager).execute(allInOneList.get(position).getHauptspeise().get(0).getUrl());


        Intent i = new Intent();
        Bundle extras = new Bundle();
        extras.putString(WidgetProvider.EXTRA, "extra");
        i.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.widgetBackground,i);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = null;
        if (bitmap != null){
            output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();
        }


        return output;
    }


    public ArrayList<MahlzeitObject> getAllInOneList() {
        allInOneList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int date = Integer.parseInt(sdf.format(new Date()));
            for (TagesTheken tagestheke: list) {
                for (ThekenObject theke: tagestheke.getThekenList()) {
                    String thekenLabel = theke.getLabel();
                    if (theke.getDate() != date)
                        break;
                    for (MahlzeitObject mahlzeit: theke.getMahlzeitList()) {
                        mahlzeit.setThekenLabel(thekenLabel);
                        allInOneList.add(mahlzeit);

                    }
                }
            }
        CLog.p("SIZE ALL OF ONE", allInOneList.size());
        if (allInOneList.size() == 0){
            MahlzeitObject obj = new MahlzeitObject("Die Mensa hat heute geschlossen", new double[]{0,0,0},-28);
            obj.setThekenLabel("");
            allInOneList.add(obj);
        }
        return allInOneList;
    }

    private void loadData(){

        RemoteViews widget=new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd.MM");
        widget.setTextViewText(R.id.widgetMainTitle,sdf.format(new Date()) + "\n" + locationNames[version]);
        appWidgetManager.updateAppWidget(appWidgetId, widget);

        try {
            list = DataParserWidget.parse(new LoadWidgetData().execute(version).get());
            allInOneList = getAllInOneList();
            CLog.p("DATA SIZE AFTER PARSE", allInOneList.size());
            bitmaps = new Bitmap[allInOneList.size()];
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
