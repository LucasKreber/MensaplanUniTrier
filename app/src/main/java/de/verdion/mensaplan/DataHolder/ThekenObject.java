package de.verdion.mensaplan.DataHolder;

import java.util.ArrayList;

/**
 * Created by Lucas on 23.03.2016.
 */
public class ThekenObject {

    private int date;
    private String label;
    private int geschlossen;
    private ArrayList<MahlzeitObject> mahlzeitList = new ArrayList<>();

    public ThekenObject(int date, String label, int geschlossen, ArrayList<MahlzeitObject> mahlzeitList) {
        this.date = date;
        this.label = label;
        this.geschlossen = geschlossen;
        this.mahlzeitList = mahlzeitList;
    }

    public int getDate() {
        return date;
    }

    public String getLabel() {
        return label;
    }

    public int getGeschlossen() {
        return geschlossen;
    }

    public ArrayList<MahlzeitObject> getMahlzeitList() {
        return mahlzeitList;
    }
}
