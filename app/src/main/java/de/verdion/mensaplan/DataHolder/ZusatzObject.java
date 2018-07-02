package de.verdion.mensaplan.DataHolder;

/**
 * Created by Lucas on 04.04.2016.
 */
public class ZusatzObject {
    private String label;
    private int imageRessource;

    public ZusatzObject(String label, int imageRessource) {
        this.label = label;
        this.imageRessource = imageRessource;
    }

    public String getLabel() {
        return label;
    }

    public int getImageRessource() {
        return imageRessource;
    }
}
