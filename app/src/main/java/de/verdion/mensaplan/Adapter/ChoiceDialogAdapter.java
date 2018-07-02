package de.verdion.mensaplan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.Interfaces.OnFilterChange;
import de.verdion.mensaplan.Logger.CLog;
import de.verdion.mensaplan.R;

/**
 * Created by Lucas on 13.07.2016.
 *
 */
public class ChoiceDialogAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String[] choices = new String[11];
    private String[] internalNames = new String[11];
    private OnFilterChange onFilterChange;

    public ChoiceDialogAdapter(Context context, OnFilterChange onFilterChange) {
        this.context = context;
        this.onFilterChange = onFilterChange;
        sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        inflater = ((Activity) context).getLayoutInflater();
        fillChoices();
        fillInternalNames();
    }

    @Override
    public int getCount() {
        return 11;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.dialog_choice_listview_row,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = convertView.findViewById(R.id.dialog_choice_checkbox);

            convertView.setTag(viewHolder);
            convertView.setTag(R.id.dialog_choice_checkbox,viewHolder.checkBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editor.putBoolean(internalNames[position], true);
                onFilterChange.settingChanged();
            } else {
                editor.putBoolean(internalNames[position], false);
                onFilterChange.settingChanged();
            }
            editor.commit();
        });

        viewHolder.checkBox.setTag(position);
        viewHolder.checkBox.setText(choices[position]);

        if (sharedPreferences.getBoolean(internalNames[position], false)){
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }

        return convertView;
    }

    public static class ViewHolder{
        CheckBox checkBox;
    }

    private void fillChoices(){
        choices[0] = context.getString(R.string.choice_beilagen);
        choices[1] = context.getString(R.string.choice_tagessuppe);
        choices[2] = context.getString(R.string.choice_desert);
        choices[3] = context.getString(R.string.choice_obst);
        choices[4] = context.getString(R.string.choice_untergeschoss);
        choices[5] = context.getString(R.string.choice_Theke_1);
        choices[6] = context.getString(R.string.choice_Theke_2);
        choices[7] = context.getString(R.string.choice_wok);
        choices[8] = context.getString(R.string.choice_kleine_karte_for_u);
        choices[9] = context.getString(R.string.choice_kleine_karte);
        choices[10] = context.getString(R.string.choice_abendmensa);
    }

    private void fillInternalNames() {
        internalNames[0] = "BEILAGEN";
        internalNames[1] = "Tagessuppe";
        internalNames[2] = "Dessert- Auswahl";
        internalNames[3] = "Obst";
        internalNames[4] = "UNTERGESCHOSS";
        internalNames[5] = "THEKE 1";
        internalNames[6] = "THEKE 2";
        internalNames[7] = "WOK forU";
        internalNames[8] = "KLEINE KARTE forU";
        internalNames[9] = "KLEINE KARTE";
        internalNames[10] = "ABENDMENSA";
    }
}
