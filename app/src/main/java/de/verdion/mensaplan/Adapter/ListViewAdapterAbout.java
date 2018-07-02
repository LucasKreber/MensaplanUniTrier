package de.verdion.mensaplan.Adapter;

/**
 * Created by Lucas on 18.01.2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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
import de.verdion.mensaplan.DataHolder.EssenObject;
import de.verdion.mensaplan.DataHolder.MahlzeitObject;
import de.verdion.mensaplan.DataHolder.SettingObject;
import de.verdion.mensaplan.HelperClasses.EscapeUtils;
import de.verdion.mensaplan.HelperClasses.FileUtils;
import de.verdion.mensaplan.MainActivity;
import de.verdion.mensaplan.R;



/**
 * Created by Lucas on 30.03.2016.
 */
public class ListViewAdapterAbout extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SettingObject> list;

    public ListViewAdapterAbout(Context context,ArrayList<SettingObject> list) {
        this.context = context;
        this.list = list;
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

        holder.click.setOnClickListener(view -> {
            if (position == 0) {
                showData();
            } else if (position == 1) {
                showLicense();
            }
        });


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



        return convertView;
    }

    @Override
    public void onClick(View view) {

    }


    public static class ViewHolder{
        TextView title;
        TextView subtitle;
        Switch aSwitch;
        RelativeLayout click;
    }


    private void showLicense(){
        ((TextView) new AlertDialog.Builder(context)
                .setTitle("Lizenzen und verwendeter Code")
                .setMessage(Html.fromHtml(context.getString(R.string.settings2_message) + "<br><br>" + "Die App verwendet Code von farebot (<a href=\"https://github.com/codebutler/farebot\" title=\"farebot\">GitHub</a>) und Jakob Wenzel (<a href=\"https://github.com/jakobwenzel/MensaGuthaben\" title=\"JakobWenzel\">GitHub</a>). Beides unter GPL.<br><br>" +
                        "Icons der Zusatzstoffe und Appicon:<div>Icons made by <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> from <a href=\"http://www.flaticon.com\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>" +
                        "<br>Herzicon:<div>Icons made by <a href=\"http://www.flaticon.com/authors/google\" title=\"Google\">Google</a> from <a href=\"http://www.flaticon.com\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>" +
                        "<br>Spendenicon:<div>Icons made by <a href=\"http://www.flaticon.com/authors/freepik\" title=\"Freepik\">Freepik</a> from <a href=\"http://www.flaticon.com\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://file000.flaticon.com/downloads/license/license.pdf\" title=\"Flaticon Basic License\" target=\"_blank\">Flaticon Basic License</a></div>" +
                        "<br>CircularImageView:<div>CircularImageView <a href=\"https://github.com/lopspower/CircularImageView\" title=\"GitHub\">(GitHub)</a> is licensed by <a href=\"http://www.apache.org/licenses/LICENSE-2.0\" title=\"Apache License 2.0.\" target=\"_blank\">Apache License 2.0.</a></div>" +
                        "<br>Picasso:<div>Picasso <a href=\"https://github.com/square/picasso\" title=\"GitHub\">(GitHub)</a> is licensed by <a href=\"http://www.apache.org/licenses/LICENSE-2.0\" title=\"Apache License 2.0.\" target=\"_blank\">Apache License 2.0.</a></div>" +
                        "<br>ListViewAnimations:<div>ListViewAnimations <a href=\"https://github.com/nhaarman/ListViewAnimations\" title=\"GitHub\">(GitHub)</a> is licensed by <a href=\"http://www.apache.org/licenses/LICENSE-2.0\" title=\"Apache License 2.0.\" target=\"_blank\">Apache License 2.0.</a></div>" +
                        "<br>StickyListHeaders:<div>StickyListHeaders <a href=\"https://github.com/emilsjolander/StickyListHeaders\" title=\"GitHub\">(GitHub)</a> is licensed by <a href=\"http://www.apache.org/licenses/LICENSE-2.0\" title=\"Apache License 2.0.\" target=\"_blank\">Apache License 2.0.</a></div>" +
                        "<br>Nine Old Androids:<div>Nine Old Androids <a href=\"https://github.com/JakeWharton/NineOldAndroids\" title=\"GitHub\">(GitHub)</a> is licensed by <a href=\"http://www.apache.org/licenses/LICENSE-2.0\" title=\"Apache License 2.0.\" target=\"_blank\">Apache License 2.0.</a></div>" +
                        "<br>Programmcode:<div><a href=\"https://gitlab.com/Verdion/MensaplanTrier/tree/master\" title=\"Code\">GitLab</a></div>"))
                .show()
                .findViewById(android.R.id.message))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showData(){
        ((TextView) new AlertDialog.Builder(context)
                .setTitle("Bereitgestellte Daten")
                .setMessage(Html.fromHtml(context.getString(R.string.settings1_message1) + "<br><br><a href=\"http://studiwerk.de/\">Studierendenwerk Trier</a>"))
                .show()
                .findViewById(android.R.id.message))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }
}
