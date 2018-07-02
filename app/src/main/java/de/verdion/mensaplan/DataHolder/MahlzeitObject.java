package de.verdion.mensaplan.DataHolder;

import java.util.ArrayList;

/**
 * Created by Lucas on 23.03.2016.
 */
public class MahlzeitObject {

    private String label;
    private double[] price = new double[3];
    private String thekenLabel;
    private int id;
    private ArrayList<EssenObject> vorspeise = new ArrayList<>();
    private ArrayList<EssenObject> hauptspeise = new ArrayList<>();
    private ArrayList<EssenObject> beilage1 = new ArrayList<>();
    private ArrayList<EssenObject> beilage2 = new ArrayList<>();
    private ArrayList<EssenObject> nachspeise = new ArrayList<>();

    public MahlzeitObject(String label, double[] price, int id) {
        this.label = label;
        this.price = price;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public double[] getPrice() {
        return price;
    }

    public void addVorspeise(EssenObject obj){
        vorspeise.add(obj);
    }

    public void addHauptspeise(EssenObject obj){
        hauptspeise.add(obj);
    }

    public void addBeilage1(EssenObject obj){
        beilage1.add(obj);
    }

    public void addBeilage2(EssenObject obj) {
        beilage2.add(obj);
    }

    public void addNachspeise(EssenObject obj){
        nachspeise.add(obj);
    }

    public void setVorspeise(ArrayList<EssenObject> vorspeise) {
        this.vorspeise = vorspeise;
    }

    public void setHauptspeise(ArrayList<EssenObject> hauptspeise) {
        this.hauptspeise = hauptspeise;
    }

    public void setBeilage1(ArrayList<EssenObject> beilage1) {
        this.beilage1 = beilage1;
    }

    public void setBeilage2(ArrayList<EssenObject> beilage2) {
        this.beilage2 = beilage2;
    }

    public void setNachspeise(ArrayList<EssenObject> nachspeise) {
        this.nachspeise = nachspeise;
    }

    public ArrayList<EssenObject> getVorspeise() {
        return vorspeise;
    }

    public ArrayList<EssenObject> getHauptspeise() {
        return hauptspeise;
    }

    public ArrayList<EssenObject> getBeilage1() {
        return beilage1;
    }

    public ArrayList<EssenObject> getBeilage2() {
        return beilage2;
    }

    public ArrayList<EssenObject> getNachspeise() {
        return nachspeise;
    }

    public String getThekenLabel() {
        return thekenLabel;
    }

    public void setThekenLabel(String thekenLabel) {
        this.thekenLabel = thekenLabel;
    }

    public int getId() {
        return id;
    }


}
