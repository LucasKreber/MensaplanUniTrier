package de.verdion.mensaplan.DataHolder;

/**
 * Created by Lucas on 03.04.2016.
 */
public class RatingObject {

    private int id;
    public int count;
    private int date;

    public RatingObject(int id, int value, boolean isCount) {
        this.id = id;
        if (isCount){
            count = value;
        } else {
            date = value;
        }
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int getDate() {
        return date;
    }
}
