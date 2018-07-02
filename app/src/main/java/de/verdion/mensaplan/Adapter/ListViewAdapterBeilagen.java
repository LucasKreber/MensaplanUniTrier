package de.verdion.mensaplan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.verdion.mensaplan.DataHolder.EssenObject;
import de.verdion.mensaplan.DataHolder.MahlzeitObject;
import de.verdion.mensaplan.HelperClasses.EscapeUtils;
import de.verdion.mensaplan.R;

/**
 * Created by Lucas on 30.03.2016.
 */
public class ListViewAdapterBeilagen extends BaseAdapter {

    private Context context;
    private int mahlzeitPos;
    private LayoutInflater inflater;
    private ArrayList<MahlzeitObject> list;
    private ArrayList<String> essenList = new ArrayList<>();

    public ListViewAdapterBeilagen(Context context,ArrayList<MahlzeitObject> list, int mahlzeitPos) {
        this.context = context;
        this.mahlzeitPos = mahlzeitPos;
        this.list = list;
        count();
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return essenList.size();
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
            convertView = inflater.inflate(R.layout.item_beilage,parent,false);
            holder.title = convertView.findViewById(R.id.beilageTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(essenList.get(position));

        return convertView;
    }

    public static class ViewHolder{
        TextView title;
    }


    private int count(){
        String v="mit ",b1="mit ",b2="mit ",n="mit ";
        for (EssenObject objV: list.get(mahlzeitPos).getVorspeise()) {
            v+=EscapeUtils.unescape(objV.getLabel()) + " oder ";
        }
        for (EssenObject objB1: list.get(mahlzeitPos).getBeilage1()) {
            b1+=EscapeUtils.unescape(objB1.getLabel()) + " oder ";
        }
        for (EssenObject objB2: list.get(mahlzeitPos).getBeilage2()) {
            b2+=EscapeUtils.unescape(objB2.getLabel()) + " oder ";
        }
        for (EssenObject objN: list.get(mahlzeitPos).getNachspeise()) {
            n+=EscapeUtils.unescape(objN.getLabel()) + " oder ";
        }

        if (v.length() > 4)
            essenList.add(v.substring(0,v.length()-6));

        if (b1.length() > 4)
            essenList.add(b1.substring(0,b1.length()-6));

        if (b2.length() > 4)
            essenList.add(b2.substring(0,b2.length()-6));

        if (n.length() > 4)
            essenList.add(n.substring(0,n.length()-6));

        for (int i = 0; i < essenList.size(); i++) {
            if (essenList.get(i).equals("mit "))
                essenList.remove(i);
        }

        return essenList.size();
    }
}
