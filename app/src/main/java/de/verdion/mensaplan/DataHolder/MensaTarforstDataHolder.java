package de.verdion.mensaplan.DataHolder;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

import de.verdion.mensaplan.Dialogs.FilterDialog;
import de.verdion.mensaplan.HelperClasses.Filter;
import de.verdion.mensaplan.HelperClasses.FoodUtils;
import de.verdion.mensaplan.Logger.CLog;

/**
 * Created by Lucas on 23.03.2016.
 *
 */
public class MensaTarforstDataHolder {
    private static MensaTarforstDataHolder instance;
    private ArrayList<TagesTheken> tagesTheken;
    private ArrayList<MahlzeitObject> allInOneList;
    private ArrayList<Integer> dateIndex;
    private ArrayList<String> dateList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private Filter filter;

    public static MensaTarforstDataHolder getInstance() {
        if (MensaTarforstDataHolder.instance ==  null){
            MensaTarforstDataHolder.instance = new MensaTarforstDataHolder();
        }
        return MensaTarforstDataHolder.instance;
    }

    private MensaTarforstDataHolder() {
        tagesTheken = new ArrayList<>();
    }

    public void setTagesTheken(ArrayList<TagesTheken> tagesTheken) {
        this.tagesTheken = tagesTheken;
    }

    public ArrayList<TagesTheken> getTagesTheken() {
        return tagesTheken;
    }

    public ArrayList<MahlzeitObject> getAllInOneList() {
        CLog.p("CHOICE", Arrays.toString(Config.CHOICES));
        if (allInOneList == null  || Config.RELOAD_TARFORST){
            allInOneList = new ArrayList<>();
            dateList = new ArrayList<>();
            dateIndex = new ArrayList<>();
            int counter = 0;
            for (TagesTheken tagestheke: tagesTheken) {
                for (ThekenObject theke: tagestheke.getThekenList()) {
                    String thekenLabel = theke.getLabel();
                    for (MahlzeitObject mahlzeit: theke.getMahlzeitList()) {
                        mahlzeit.setThekenLabel(thekenLabel);
                        if (!sharedPreferences.getBoolean(mahlzeit.getThekenLabel(),false) && filter.applayFilter(mahlzeit)){
                            if (!sharedPreferences.getBoolean(mahlzeit.getLabel(), false)){
                                allInOneList.add(mahlzeit);
                                dateIndex.add(counter);
                            }
                        }

                    }
                    if (!dateList.contains(theke.getDate()+"")){
                        dateList.add(theke.getDate()+"");
                    }
                }
                    counter++;
            }
        }
        return allInOneList;
    }

    public ArrayList<Integer> getDateIndex() {
        if (dateIndex == null)
            getAllInOneList();
        return dateIndex;
    }

    public ArrayList<String> getDateList() {
        if (dateList == null)
            getAllInOneList();
        return dateList;
    }

    public void init(Context context) {
        this.context = context;
        filter = new Filter(context);
        sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
    }
}
