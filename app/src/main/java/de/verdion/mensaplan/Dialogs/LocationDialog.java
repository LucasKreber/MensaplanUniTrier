package de.verdion.mensaplan.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RadioButton;

import de.verdion.mensaplan.HelperClasses.FileUtils;
import de.verdion.mensaplan.Interfaces.CDialog;
import de.verdion.mensaplan.Interfaces.OnLocationChanged;
import de.verdion.mensaplan.R;

public class LocationDialog implements CDialog, View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Dialog locationDialog;
    private OnLocationChanged onLocationChanged;

    public LocationDialog(OnLocationChanged onLocationChanged) {
        this.onLocationChanged = onLocationChanged;
    }

    @Override
    public void show(Context context) {
        sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        locationDialog = new Dialog(context);
        locationDialog.setContentView(R.layout.dialog_location);

        locationDialog.setTitle(context.getString(R.string.dialog_location));

        RadioButton r1 = locationDialog.findViewById(R.id.dialog_location_tarforst);
        RadioButton r2 = locationDialog.findViewById(R.id.dialog_location_bistro);
        RadioButton r3 = locationDialog.findViewById(R.id.dialog_location_petrisberg);

        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);

        switch (sharedPreferences.getInt("location", 0)){
            case 0:
                r1.setChecked(true);
                break;
            case 1:
                r2.setChecked(true);
                break;
            case 2:
                r3.setChecked(true);
                break;
        }

        locationDialog.show();
    }

    @Override
    public void onClick(View view) {
        int locationOption = 0;

        switch (view.getId()){
            case R.id.dialog_location_tarforst:
                locationOption = 0;
                editor.putInt("location", locationOption);
                editor.putString("locationName", "Mensa Tarforst");
                break;
            case R.id.dialog_location_bistro:
                locationOption = 1;
                editor.putInt("location", locationOption);
                editor.putString("locationName", "Bistro A/B");
                break;
            case R.id.dialog_location_petrisberg:
                locationOption = 2;
                editor.putInt("location", locationOption);
                editor.putString("locationName", "Mensa Petrisberg");
                break;
        }

        editor.commit();
        locationDialog.dismiss();
        onLocationChanged.OnLocationOptionChanged(locationOption);
    }
}
