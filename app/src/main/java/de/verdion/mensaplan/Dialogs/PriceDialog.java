package de.verdion.mensaplan.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RadioButton;

import de.verdion.mensaplan.Interfaces.CDialog;
import de.verdion.mensaplan.Interfaces.OnPriceChanged;
import de.verdion.mensaplan.R;

public class PriceDialog implements CDialog, View.OnClickListener {

    private OnPriceChanged onPriceChanged;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Dialog priceDialog;

    public PriceDialog(OnPriceChanged onPriceChanged) {
        this.onPriceChanged = onPriceChanged;
    }

    @Override
    public void show(Context context) {
        sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        priceDialog = new Dialog(context);
        priceDialog.setContentView(R.layout.dialog_price);

        priceDialog.setTitle(context.getString(R.string.dialog_price));

        RadioButton r1 = priceDialog.findViewById(R.id.radioButton);
        RadioButton r2 = priceDialog.findViewById(R.id.radioButton2);
        RadioButton r3 = priceDialog.findViewById(R.id.radioButton3);

        switch (sharedPreferences.getInt("PriceOption", 0)) {
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

        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);

        priceDialog.show();
    }


    @Override
    public void onClick(View view) {

        int priceOption = 0;
        switch (view.getId()) {
            case R.id.radioButton:
                editor.putInt("PriceOption", 0);
                editor.putString("PriceOptionText", "Studenten");
                priceOption = 0;
                break;
            case R.id.radioButton2:
                editor.putInt("PriceOption", 1);
                editor.putString("PriceOptionText", "Bedienstete");
                priceOption = 1;
                break;
            case R.id.radioButton3:
                editor.putInt("PriceOption", 2);
                editor.putString("PriceOptionText", "Gäste");
                priceOption = 2;
                break;
        }
        editor.commit();
        onPriceChanged.OnPriceOptionChanged(priceOption);
        priceDialog.dismiss();
    }
}
