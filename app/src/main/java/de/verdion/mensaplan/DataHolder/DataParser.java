package de.verdion.mensaplan.DataHolder;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.verdion.mensaplan.HelperClasses.EscapeUtils;
import de.verdion.mensaplan.Logger.CLog;

/**
 * Created by Lucas on 26.03.2016.
 */
public class DataParser {

    public static boolean parse(String json, int location, Context context){
        try {
            JSONObject main = new JSONObject(json);
            JSONArray days = main.getJSONArray("days");

            ArrayList<TagesTheken> tagesTheken = new ArrayList<>();

            for (int i = 0; i < days.length(); i++) {
                CLog.p(days.toString());
                tagesTheken.add(parseTheken(days.getJSONArray(i)));
            }

            switch (location){
                case 0:
                    MensaTarforstDataHolder.getInstance().init(context);
                    MensaTarforstDataHolder.getInstance().setTagesTheken(tagesTheken);
                    //MensaTarforstDataHolder.getInstance().getAllInOneList();
                    break;
                case 1:
                    MensaBistroDataHolder.getInstance().init(context);
                    MensaBistroDataHolder.getInstance().setTagesTheken(tagesTheken);
                    //MensaBistroDataHolder.getInstance().getAllInOneList();
                    break;
                case 2:
                    MensaPetrisbergDataHolder.getInstance().init(context);
                    MensaPetrisbergDataHolder.getInstance().setTagesTheken(tagesTheken);
                    //MensaPetrisbergDataHolder.getInstance().getAllInOneList();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
            CLog.p(e.toString());
            return false;
        }

        return true;
    }

    private static TagesTheken parseTheken(JSONArray theken) throws JSONException {
        TagesTheken tagesTheken = new TagesTheken();

        for (int i = 0; i < theken.length(); i++) {
            JSONObject thekenObj = theken.getJSONObject(i);
            int date = thekenObj.getInt("date");
            String label = thekenObj.getString("label");
            int geschlossen = thekenObj.getInt("geschlossen");
            tagesTheken.addTheke(new ThekenObject(date, label, geschlossen, parseMahlzeiten(thekenObj.getJSONArray("mahlzeitObj"))));
        }

        return tagesTheken;
    }

    private static ArrayList<MahlzeitObject> parseMahlzeiten(JSONArray mahlzeiten) throws JSONException {

        ArrayList<MahlzeitObject> mahlzeitList = new ArrayList<>();

        for (int i = 0; i < mahlzeiten.length(); i++) {
            JSONObject mahlzeitObjJson = mahlzeiten.getJSONObject(i);
            String label = mahlzeitObjJson.getString("label");
            double[] price = parsePrice(mahlzeitObjJson.getJSONObject("preis"));
            int id = mahlzeitObjJson.getInt("id");

            MahlzeitObject mahlzeitObj = new MahlzeitObject(label,price,id);
            mahlzeitObj.setVorspeise(parseBeilage(mahlzeitObjJson.getJSONArray("vorspeise")));
            mahlzeitObj.setHauptspeise(parseHauptspeise(mahlzeitObjJson.getJSONArray("hauptspeise")));
            mahlzeitObj.setBeilage1(parseBeilage(mahlzeitObjJson.getJSONArray("beilage1")));
            mahlzeitObj.setBeilage2(parseBeilage(mahlzeitObjJson.getJSONArray("beilage2")));
            mahlzeitObj.setNachspeise(parseBeilage(mahlzeitObjJson.getJSONArray("nachspeise")));
            mahlzeitList.add(mahlzeitObj);
        }

        return mahlzeitList;
    }

    private static double[] parsePrice(JSONObject priceObj) throws JSONException {
        double[] price = new double[3];
        String p1 = priceObj.getString("S");
        String p2 = priceObj.getString("B");
        String p3 = priceObj.getString("G");

        if (!p1.equals(""))
            price[0] = priceObj.getDouble("S");
        if (!p2.equals(""))
            price[1] = priceObj.getDouble("B");
        if (!p3.equals(""))
            price[2] = priceObj.getDouble("G");

        return price;
    }

    private static ArrayList<EssenObject> parseHauptspeise(JSONArray array) throws JSONException {
        ArrayList<EssenObject> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String label = obj.getString("label");
            int id = obj.getInt("id");
            String url = obj.getString("url");
            JSONArray zusatz = obj.getJSONArray("zusatzstoffe");
            boolean schwein = false, rind = false, huhn = false, fisch = false, vegan = false, vegetarisch = false, laktosefrei = false, gluten = false, glutenWeizen = false, glutenRoggen = false, glutenGerste = false, alc = false;
            for (int j = 0; j < zusatz.length(); j++) {
                if (zusatz.getString(j).contains("Schwein")){
                    schwein = true;
                } else if (zusatz.getString(j).contains("Rind")){
                    rind = true;
                } else if (EscapeUtils.unescape(zusatz.getString(j)).contains("Geflügel")){
                    huhn = true;
                } else if (zusatz.getString(j).contains("Fisch")){
                    fisch = true;
                } else if (zusatz.getString(j).contains("Vegan")){
                    vegan = true;
                } else if (zusatz.getString(j).contains("Vegetarisch")){
                    vegetarisch = true;
                } else if (zusatz.getString(j).contains("Laktose")){
                    laktosefrei = true;
                } else if (zusatz.getString(j).contains("Gluten: Weizen")){
                    glutenWeizen = true;
                } else if (zusatz.getString(j).contains("Gluten: Roggrn")){
                    glutenRoggen = true;
                } else if (zusatz.getString(j).contains("Gluten: Gerste")){
                    glutenGerste = true;
                } else if (zusatz.getString(j).contains("Alkohol")){
                    alc = true;
                }
            }

            EssenObject eobj = new EssenObject(label,url,id,schwein,rind,huhn,fisch,vegetarisch,laktosefrei,vegan,gluten,glutenWeizen,glutenRoggen,glutenGerste,alc);
            list.add(eobj);

        }
        return list;
    }

    private static ArrayList<EssenObject> parseBeilage(JSONArray array) throws JSONException {
        ArrayList<EssenObject> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String label = obj.getString("label");
            int id = obj.getInt("id");
            String url = obj.getString("url");

            EssenObject eobj = new EssenObject(label,url,id,false,false,false,false,false,false,false,false,false,false,false,false);
            list.add(eobj);
        }

        return list;
    }

    public static void parseRating(String ratingJson){
        try {
            JSONObject mainObject = new JSONObject(ratingJson);
            JSONArray all = mainObject.getJSONArray("all");
            JSONArray user = mainObject.getJSONArray("user");
            JSONArray day = mainObject.getJSONArray("day");

            for (int i = 0; i < all.length(); i++) {
                JSONObject tmp = all.getJSONObject(i);
                RatingDataHolder.getInstance().addRatingObjectAll(new RatingObject(tmp.getInt("id"), tmp.getInt("count"), true));
            }

            for (int i = 0; i < user.length(); i++) {
                JSONObject tmp = user.getJSONObject(i);
                RatingDataHolder.getInstance().addRatingObjectUser(new RatingObject(tmp.getInt("id"), tmp.getInt("date"), false));
            }

            for (int i = 0; i < day.length(); i++) {
                JSONObject tmp = day.getJSONObject(i);
                RatingDataHolder.getInstance().addRatingObjectDay(new RatingObject(tmp.getInt("id"), tmp.getInt("count"), true));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            CLog.p(e.toString());
        }
    }

}
