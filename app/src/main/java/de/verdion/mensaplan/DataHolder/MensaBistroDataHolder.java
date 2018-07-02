package de.verdion.mensaplan.DataHolder;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import de.verdion.mensaplan.HelperClasses.Filter;
import de.verdion.mensaplan.HelperClasses.FoodUtils;
import de.verdion.mensaplan.Logger.CLog;

/**
 * Created by Lucas on 27.03.2016.
 *
 */
public class MensaBistroDataHolder {
    private static MensaBistroDataHolder instance;
    private ArrayList<TagesTheken> tagesTheken;
    private ArrayList<MahlzeitObject> allInOneList;
    private ArrayList<Integer> dateIndex;
    private ArrayList<String> dateList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private Filter filter;

    public static MensaBistroDataHolder getInstance() {
        if (MensaBistroDataHolder.instance ==  null){
            MensaBistroDataHolder.instance = new MensaBistroDataHolder();
        }
        return MensaBistroDataHolder.instance;
    }

    private MensaBistroDataHolder() {
        tagesTheken = new ArrayList<>();
    }

    public void setTagesTheken(ArrayList<TagesTheken> tagesTheken) {
        this.tagesTheken = tagesTheken;
    }

    public ArrayList<TagesTheken> getTagesTheken() {
        return tagesTheken;
    }

    public ArrayList<MahlzeitObject> getAllInOneList() {
        if (allInOneList == null || Config.RELOAD_BISTROAB){
            allInOneList = new ArrayList<>();
            dateList = new ArrayList<>();
            dateIndex = new ArrayList<>();
            int counter = 0;
            for (TagesTheken tagestheke: tagesTheken) {
                for (ThekenObject theke: tagestheke.getThekenList()) {
                    String thekenLabel = theke.getLabel();
                    for (MahlzeitObject mahlzeit: theke.getMahlzeitList()) {
                        mahlzeit.setThekenLabel(thekenLabel);
                        if (!sharedPreferences.getBoolean(mahlzeit.getLabel(), false) && filter.applayFilter(mahlzeit)){
                            allInOneList.add(mahlzeit);
                            dateIndex.add(counter);
                        }
                    }
                    if (!dateList.contains(theke.getDate()+"")){
                        dateList.add(theke.getDate()+"");
                    }
                }
                counter++;
            }
        }

        Config.RELOAD_BISTROAB = false;
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
