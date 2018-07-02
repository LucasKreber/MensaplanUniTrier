package de.verdion.mensaplan.DataHolder;

import android.app.Dialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.verdion.mensaplan.HelperClasses.EscapeUtils;

/**
 * Created by Lucas on 04.04.2016.
 */
public class DataParserWidget {

    public static ArrayList<TagesTheken> parse(String json){
        ArrayList<TagesTheken> tagesTheken = new ArrayList<>();
        try {
            JSONObject main = new JSONObject(json);
            JSONArray days = main.getJSONArray("days");

            for (int i = 0; i < days.length(); i++) {
                tagesTheken.add(parseTheken(days.getJSONArray(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tagesTheken;
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
}
