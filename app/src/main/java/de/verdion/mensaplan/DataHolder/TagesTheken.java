package de.verdion.mensaplan.DataHolder;

import java.util.ArrayList;

/**
 * Created by Lucas on 26.03.2016.
 */
public class TagesTheken {

    private ArrayList<ThekenObject> thekenList = new ArrayList<>();

    public void addTheke(ThekenObject obj){
        thekenList.add(obj);
    }

    public ArrayList<ThekenObject> getThekenList() {
        return thekenList;
    }
}
