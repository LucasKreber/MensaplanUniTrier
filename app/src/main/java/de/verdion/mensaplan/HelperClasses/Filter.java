package de.verdion.mensaplan.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import de.verdion.mensaplan.DataHolder.EssenObject;
import de.verdion.mensaplan.DataHolder.MahlzeitObject;

public class Filter {

    private SharedPreferences sharedPreferences;

    public Filter(Context context) {
        sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
    }

    public boolean applayFilter(MahlzeitObject mahlzeitObject) {
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
    }

}
