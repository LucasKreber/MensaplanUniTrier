package de.verdion.mensaplan.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

import de.verdion.mensaplan.Adapter.ChoiceDialogAdapter;
import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.Interfaces.OnCloseFilterDialog;
import de.verdion.mensaplan.Interfaces.OnFilterChange;
import de.verdion.mensaplan.R;

public class FilterDialog implements CompoundButton.OnCheckedChangeListener, DialogInterface.OnDismissListener {

    private Context context;
    private Dialog filter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean showMeet, showFish, showVegie, showVegan;
    private OnCloseFilterDialog onCloseFilterDialog;
    private boolean isChanged = false;
    private Switch switchVegan;
    private Switch switchVegie;

    public FilterDialog(Context context, OnCloseFilterDialog onCloseFilterDialog) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
        this.onCloseFilterDialog = onCloseFilterDialog;
        editor = sharedPreferences.edit();
        showMeet = sharedPreferences.getBoolean("meet", true);
        showFish = sharedPreferences.getBoolean("fish", true);
        showVegie = sharedPreferences.getBoolean("vegie", true);
        showVegan = sharedPreferences.getBoolean("vegan", true);
    }

    public void show(){

        filter = new Dialog(context);
        filter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filter.setContentView(R.layout.dialog_filter);
        filter.setCancelable(true);
        filter.setOnDismissListener(this);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        lp.copyFrom(filter.getWindow().getAttributes());
        lp.height = (int) (metrics.heightPixels * 0.95);
        //lp.width = metrics.widthPixels;
        filter.getWindow().setAttributes(lp);


        ListView listView = filter.findViewById(R.id.dialog_filter_listview);
        ImageButton cancel = filter.findViewById(R.id.dialog_filter_cancel);
        Switch switchMeet = filter.findViewById(R.id.dialog_filter_switch_meet);
        Switch switchFish = filter.findViewById(R.id.dialog_filter_switch_fish);
        switchVegie = filter.findViewById(R.id.dialog_filter_switch_vegie);
        switchVegan = filter.findViewById(R.id.dialog_filter_switch_vegan);

        switchMeet.setOnCheckedChangeListener(this);
        switchFish.setOnCheckedChangeListener(this);
        switchVegie.setOnCheckedChangeListener(this);
        switchVegan.setOnCheckedChangeListener(this);

        switchMeet.setChecked(showMeet);
        switchFish.setChecked(showFish);
        switchVegie.setChecked(showVegie);
        switchVegan.setChecked(showVegan);



        ChoiceDialogAdapter adapter = new ChoiceDialogAdapter(context, () -> {
            //isChanged = true;
        });
        listView.setAdapter(adapter);
        Config.RELOAD_TARFORST = true;
        Config.RELOAD_BISTROAB = true;
        Config.RELOAD_PETRISBERG = true;


        cancel.setOnClickListener(v -> {
            filter.dismiss();
        });

        filter.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.dialog_filter_switch_meet:
                editor.putBoolean("meet", b);
                editor.commit();
                isChanged = true;
                break;
            case R.id.dialog_filter_switch_fish:
                editor.putBoolean("fish", b);
                editor.commit();
                isChanged = true;
                break;
            case R.id.dialog_filter_switch_vegie:
                editor.putBoolean("vegie", b);
                editor.commit();
                isChanged = true;
                if (switchVegie.isChecked()) {
                    switchVegan.setChecked(true);
                    editor.putBoolean("vegan", b);
                    editor.commit();
                }
                break;
            case R.id.dialog_filter_switch_vegan:
                editor.putBoolean("vegan", b);
                editor.commit();
                isChanged = true;
                break;
        }

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        onCloseFilterDialog.isClosed(isChanged);
    }
}
