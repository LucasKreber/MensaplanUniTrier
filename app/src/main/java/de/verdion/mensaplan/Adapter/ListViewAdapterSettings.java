package de.verdion.mensaplan.Adapter;

/**
 * Created by Lucas on 18.01.2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.DataHolder.SettingObject;
import de.verdion.mensaplan.Dialogs.LocationDialog;
import de.verdion.mensaplan.Dialogs.PriceDialog;
import de.verdion.mensaplan.HelperClasses.FileUtils;
import de.verdion.mensaplan.Interfaces.OnFilterChange;
import de.verdion.mensaplan.Interfaces.OnLocationChanged;
import de.verdion.mensaplan.Interfaces.OnPriceChanged;
import de.verdion.mensaplan.R;



/**
 * Created by Lucas on 30.03.2016.
 */
public class ListViewAdapterSettings extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SettingObject> list;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ListViewAdapterSettings(Context context,ArrayList<SettingObject> list) {
        this.context = context;
        this.list = list;
        sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return list.size();
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
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_row_settings,parent,false);
            holder.title = convertView.findViewById(R.id.item_row_setting_title);
            holder.subtitle = convertView.findViewById(R.id.item_row_setting_subtitle);
            holder.aSwitch = convertView.findViewById(R.id.item_row_setting_switch);
            holder.click = convertView.findViewById(R.id.item_row_setting_click);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (Config.imageLoad){
            if (position == 2)
                holder.aSwitch.setChecked(false);
        } else {
            if (position == 2)
                holder.aSwitch.setChecked(true);
        }

        if (Config.SHOWOPENINGTIME){
            if (position == 3) {
                holder.aSwitch.setChecked(false);
            }
        } else {
            if (position == 3){
                holder.aSwitch.setChecked(true);
            }
        }

        holder.title.setText(list.get(position).getTitle());
        if (list.get(position).getSubtitle() != null){
            holder.subtitle.setText(list.get(position).getSubtitle());
        } else {
            holder.subtitle.setText("");
        }

        if (list.get(position).isSwitch()){
            holder.aSwitch.setVisibility(View.VISIBLE);
        } else {
            holder.aSwitch.setVisibility(View.GONE);
        }

        if (position == 0){
            holder.subtitle.setText(sharedPreferences.getString("PriceOptionText", "Studenten"));
        } else if (position == 1) {
            holder.subtitle.setText(sharedPreferences.getString("locationName", "Mensa Tarforst"));
        }

        holder.click.setOnClickListener(v -> {

            if (position == 0){
                PriceDialog priceDialog = new PriceDialog(priceOption -> {
                    list.get(0).setSubtitle(sharedPreferences.getString("PriceOptionText", "Studenten"));
                    notifyDataSetChanged();
                });
                priceDialog.show(context);
            } else if(position == 1){
                LocationDialog locationDialog = new LocationDialog(priceOption -> notifyDataSetChanged());
                locationDialog.show(context);
            } else if(position == 2) {
                if (holder.aSwitch.isChecked()){
                    editor.putBoolean("image_load", true);
                    editor.commit();
                    Config.imageLoad = true;
                } else {
                    editor.putBoolean("image_load", false);
                    editor.commit();
                    Config.imageLoad = false;
                }

            } else if (position == 3){
                if (holder.aSwitch.isChecked()){
                    editor.putBoolean("openingtime", true);
                    editor.commit();
                    Config.SHOWOPENINGTIME = true;
                } else {
                    editor.putBoolean("openingtime", false);
                    editor.commit();
                    Config.SHOWOPENINGTIME = false;
                }
            }

            if (holder.aSwitch.getVisibility() == View.VISIBLE){
                if (holder.aSwitch.isChecked() && position == 2){
                    holder.aSwitch.setChecked(false);
                    holder.subtitle.setText("Aus");
                    editor.putBoolean("image_load", true);
                    editor.commit();
                    Config.imageLoad = true;
                } else if (!holder.aSwitch.isChecked() && position == 2){
                    holder.aSwitch.setChecked(true);
                    holder.subtitle.setText("An");
                    editor.putBoolean("image_load", false);
                    editor.commit();
                    Config.imageLoad = false;
                }

                if (holder.aSwitch.isChecked() && position == 3){
                    holder.aSwitch.setChecked(false);
                    holder.subtitle.setText("Aus");
                    editor.putBoolean("openingtime", true);
                    editor.commit();
                    Config.SHOWOPENINGTIME = true;
                } else if (!holder.aSwitch.isChecked() && position == 3){
                    holder.aSwitch.setChecked(true);
                    holder.subtitle.setText("An");
                    editor.putBoolean("openingtime", false);
                    editor.commit();
                    Config.SHOWOPENINGTIME = false;
                }
            }
        });

        if (holder.aSwitch.getVisibility() == View.VISIBLE){
            holder.aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked){
                    if (position == 2){
                        holder.subtitle.setText("An");
                        editor.putBoolean("image_load", false);
                        editor.commit();
                        Config.imageLoad = true;
                    } else {
                        holder.subtitle.setText("An");
                        editor.putBoolean("openingtime", false);
                        editor.commit();
                        Config.SHOWOPENINGTIME = false;
                    }

                } else {
                    if (position == 2){
                        holder.subtitle.setText("Aus");
                        editor.putBoolean("image_load", true);
                        editor.commit();
                        Config.imageLoad = false;
                    } else {
                        holder.subtitle.setText("Aus");
                        editor.putBoolean("openingtime", true);
                        editor.commit();
                        Config.SHOWOPENINGTIME = true;
                    }

                }
            });

        }

        return convertView;
    }

    public static class ViewHolder{
        TextView title;
        TextView subtitle;
        Switch aSwitch;
        RelativeLayout click;
    }
}
