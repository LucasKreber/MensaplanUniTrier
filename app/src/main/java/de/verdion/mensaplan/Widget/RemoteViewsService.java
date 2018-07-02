package de.verdion.mensaplan.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import de.verdion.mensaplan.R;

/**
 * Created by Lucas on 04.04.2016.
 */
public class RemoteViewsService extends android.widget.RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int version = intent.getExtras().getInt("Version");
        ListProvider provider = null;

        switch (version){
            case 0:
                provider = new ListProvider(this.getApplicationContext(),intent,0);
                break;
            case 1:
                provider = new ListProvider(this.getApplicationContext(),intent,1);
                break;
            case 2:
                provider = new ListProvider(this.getApplicationContext(),intent,2);
                break;
        }
        return (provider);
    }
}
