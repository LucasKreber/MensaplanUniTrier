package de.verdion.mensaplan.DataHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lucas on 03.04.2016.
 */
public class RatingDataHolder {

    private static RatingDataHolder ourInstance = new RatingDataHolder();
    private ArrayList<RatingObject> ratingListAll = new ArrayList<>();
    private ArrayList<RatingObject> ratingListUser = new ArrayList<>();
    private ArrayList<RatingObject> ratingListDay = new ArrayList<>();

    public static RatingDataHolder getInstance() {
        return ourInstance;
    }

    private RatingDataHolder() {
    }

    public int getRatingForId(int id){
        int rating = -1;
        for (int i = 0; i < ratingListAll.size(); i++) {
            if (ratingListAll.get(i).getId() == id){
                rating = ratingListAll.get(i).getCount();
                break;
            }
        }

        return rating;
    }

    public int getRatingDayForId(int id){
        int rating = 0;
        for (int i = 0; i < ratingListDay.size(); i++) {
            if (ratingListDay.get(i).getId() == id){
                rating = ratingListDay.get(i).getCount();
                break;
            }
        }

        return rating;
    }

    public boolean hasUserRating(int id){
        boolean rating = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < ratingListUser.size(); i++) {
            if (ratingListUser.get(i).getId() == id && ratingListUser.get(i).getDate() == Integer.parseInt(sdf.format(new Date()))){
                rating = true;
                break;
            }
        }

        return rating;
    }

    public void addLike(int id){
        for (int i = 0; i < ratingListAll.size(); i++) {
            if (ratingListAll.get(i).getId() == id){
                ratingListAll.get(i).count++;
                break;
            }
        }

        for (int i = 0; i < ratingListDay.size(); i++) {
            if (ratingListDay.get(i).getId() == id){
                ratingListDay.get(i).count++;
                break;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        ratingListUser.add(new RatingObject(id,Integer.parseInt(sdf.format(new Date())),false));
    }

    public void removeLike(int id){
        for (int i = 0; i < ratingListAll.size(); i++) {
            if (ratingListAll.get(i).getId() == id){
                ratingListAll.get(i).count--;
                break;
            }
        }

        for (int i = 0; i < ratingListDay.size(); i++) {
            if (ratingListDay.get(i).getId() == id){
                ratingListDay.get(i).count--;
                break;
            }
        }

        for (int i = 0; i < ratingListUser.size(); i++) {
            if (ratingListUser.get(i).getId() == id){
                ratingListUser.remove(i);
                break;
            }
        }
    }

    public void addRatingObjectAll(RatingObject obj){
        ratingListAll.add(obj);
    }

    public void addRatingObjectUser(RatingObject obj){
        ratingListUser.add(obj);
    }

    public void addRatingObjectDay(RatingObject obj){
        ratingListDay.add(obj);
    }
}
