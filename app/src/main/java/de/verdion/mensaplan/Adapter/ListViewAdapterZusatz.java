package de.verdion.mensaplan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.verdion.mensaplan.DataHolder.EssenObject;
import de.verdion.mensaplan.DataHolder.ZusatzObject;
import de.verdion.mensaplan.R;

/**
 * Created by Lucas on 29.03.2016.
 */
public class ListViewAdapterZusatz extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ZusatzObject> zusatz;

    public ListViewAdapterZusatz(Context context, ArrayList<ZusatzObject> zusatz) {
        this.context = context;
        this.zusatz = zusatz;
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return zusatz.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_zusatz,parent,false);
            holder.title = (TextView) convertView.findViewById(R.id.zusatzTitle);
            holder.icon = (ImageView) convertView.findViewById(R.id.zusatzIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(zusatz.get(position).getLabel());
        holder.icon.setImageResource(zusatz.get(position).getImageRessource());

        return convertView;
    }

    public static class ViewHolder{
        TextView title;
        ImageView icon;
    }



}
