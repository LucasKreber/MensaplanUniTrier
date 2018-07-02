package de.verdion.mensaplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import de.verdion.mensaplan.Adapter.ListViewAdapterAbout;
import de.verdion.mensaplan.Adapter.ListViewAdapterSettings;
import de.verdion.mensaplan.DataHolder.SettingObject;

public class AboutActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        listView = findViewById(R.id.about_listview);

        ArrayList<SettingObject> list = new ArrayList<>();
        list.add(new SettingObject(getString(R.string.settings1), "Dateninhaber anzeigen", false));
        list.add(new SettingObject(getString(R.string.action_setting2), "Lizenzen anzeigen", false));
        ListViewAdapterAbout adapter = new ListViewAdapterAbout(this, list);
        listView.setAdapter(adapter);

    }
}
