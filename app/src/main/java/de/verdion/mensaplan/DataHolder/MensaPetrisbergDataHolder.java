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
public class MensaPetrisbergDataHolder {
    private static MensaPetrisbergDataHolder instance;
    private ArrayList<TagesTheken> tagesTheken;
    private ArrayList<MahlzeitObject> allInOneList;
    private ArrayList<Integer> dateIndex;
    private ArrayList<String> dateList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private Filter filter;

    public static MensaPetrisbergDataHolder getInstance() {
        if (MensaPetrisbergDataHolder.instance ==  null){
            MensaPetrisbergDataHolder.instance = new MensaPetrisbergDataHolder();
        }
        return MensaPetrisbergDataHolder.instance;
    }

    private MensaPetrisbergDataHolder() {
        tagesTheken = new ArrayList<>();
    }

    public void setTagesTheken(ArrayList<TagesTheken> tagesTheken) {
        this.tagesTheken = tagesTheken;
    }

    public ArrayList<TagesTheken> getTagesTheken() {
        return tagesTheken;
    }

    public ArrayList<MahlzeitObject> getAllInOneList() {
        if (allInOneList == null || Config.RELOAD_PETRISBERG){
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

        Config.RELOAD_PETRISBERG = false;
        return allInOneList;
    }

    /*private boolean applayFilter(MahlzeitObject mahlzeitObject) {
        for (EssenObject obj: mahlzeitObject.getHauptspeise()) {
            if (!sharedPreferences.getBoolean("meet", true) && (obj.isHuhn() || obj.isRind() || obj.isSchwein())) {
                return false;
            } else if (!sharedPreferences.getBoolean("fish", true) && obj.isFisch()) {
                return false;
            } else if (!sharedPreferences.getBoolean("vegie", true) && obj.isVegetarisch() && !obj.isVegan()) {
                return false;
            } else if (!sharedPreferences.getBoolean("vegan", true) && obj.isVegan() && !obj.isVegetarisch()) {
                return false;
            }
        }

        return true;
    } */

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
