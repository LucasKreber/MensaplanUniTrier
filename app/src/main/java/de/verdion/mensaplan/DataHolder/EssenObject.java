package de.verdion.mensaplan.DataHolder;

/**
 * Created by Lucas on 23.03.2016.
 */
public class EssenObject {

    private String label;
    private int id;
    private String url;
    private boolean schwein,rind,huhn,fisch,vegetarisch,vegan,laktosefrei,gluten,glutenWeizen,glutenRoggen,glutenGerste,alc;

    public EssenObject(String label, String url, int id, boolean schwein, boolean rind, boolean huhn, boolean fisch, boolean vegetarisch, boolean laktosefrei, boolean vegan,boolean gluten,boolean glutenWeizen,boolean glutenRoggen, boolean glutenGerste, boolean alc) {
        this.label = label;
        this.schwein = schwein;
        this.url = url;
        this.id = id;
        this.rind = rind;
        this.huhn = huhn;
        this.fisch = fisch;
        this.vegetarisch = vegetarisch;
        this.laktosefrei = laktosefrei;
        this.vegan = vegan;
        this.gluten = gluten;
        this.glutenWeizen = glutenWeizen;
        this.glutenRoggen = glutenRoggen;
        this.glutenGerste = glutenGerste;
        this.alc = alc;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public boolean isSchwein() {
        return schwein;
    }

    public boolean isRind() {
        return rind;
    }

    public boolean isHuhn() {
        return huhn;
    }

    public boolean isFisch() {
        return fisch;
    }

    public boolean isVegetarisch() {
        return vegetarisch;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isLaktosefrei() {
        return laktosefrei;
    }

    public boolean isGlutenWeizen() {
        return glutenWeizen;
    }

    public boolean isGlutenRoggen() {
        return glutenRoggen;
    }

    public boolean isGlutenGerste() {
        return glutenGerste;
    }

    public boolean isAlc() {
        return alc;
    }

    public boolean isGluten() {
        return gluten;
    }
}
