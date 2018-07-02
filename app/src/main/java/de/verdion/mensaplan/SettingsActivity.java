package de.verdion.mensaplan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import de.verdion.mensaplan.Adapter.ListViewAdapterSettings;
import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.DataHolder.SettingObject;

public class SettingsActivity extends AppCompatActivity {

    private ListView listView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setSubtitle("Einstellungen");
        setContentView(R.layout.activity_settings);

        listView = (ListView) findViewById(R.id.setting_listview);
        sharedPreferences = getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);

        String showImages = "";
        String showOpeningtime = "";
        if (Config.imageLoad){
            showImages = "Aus";
        } else {
            showImages = "An";
        }

        if (Config.SHOWOPENINGTIME){
            showOpeningtime = "Aus";
        } else {
            showOpeningtime = "An";
        }

        ArrayList<SettingObject> list = new ArrayList<>();
        list.add(new SettingObject(getString(R.string.setting_price), "Student", false));
        //list.add(new SettingObject(getString(R.string.setting_choice), "3 Theken ausgeblendet", false));
        list.add(new SettingObject(getString(R.string.setting_location), sharedPreferences.getString("locationName", "Mensa Tarforst"), false));
        list.add(new SettingObject(getString(R.string.setting_image), showImages, true));
        list.add(new SettingObject("Keine Zeiten anzeigen", showOpeningtime, true));
        list.add(new SettingObject("","Die mit * gekennzeichneten Öffnungszeiten beziehen sich auf die Öffnungszeiten der jeweiligen Verkaufsstelle, nicht auf die Verfügbarkeit des Essen.",false));
        ListViewAdapterSettings adapter = new ListViewAdapterSettings(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
