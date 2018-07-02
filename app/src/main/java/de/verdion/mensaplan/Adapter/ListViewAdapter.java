package de.verdion.mensaplan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.MaskFilter;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.DataHolder.MahlzeitObject;
import de.verdion.mensaplan.DataHolder.MensaBistroDataHolder;
import de.verdion.mensaplan.DataHolder.MensaPetrisbergDataHolder;
import de.verdion.mensaplan.DataHolder.MensaTarforstDataHolder;
import de.verdion.mensaplan.DataHolder.TagesTheken;
import de.verdion.mensaplan.DataHolder.ThekenObject;
import de.verdion.mensaplan.HelperClasses.EscapeUtils;
import de.verdion.mensaplan.HelperClasses.FileUtils;
import de.verdion.mensaplan.HelperClasses.OpeningTimes;
import de.verdion.mensaplan.Logger.CLog;
import de.verdion.mensaplan.MainActivity;
import de.verdion.mensaplan.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Lucas on 24.03.2016.
 */
public class ListViewAdapter extends BaseAdapter implements StickyListHeadersAdapter{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MahlzeitObject> list;
    private ArrayList<Integer> dateIndex;
    private ArrayList<String> dateList;
    //private int priceId;
    private int priceOption;
    private int location;
    private boolean isFriday;
    private SharedPreferences sharedPreferences;

    public ListViewAdapter(Context context, int location){
        this.context = context;
        inflater = ((Activity) context).getLayoutInflater();
        dateIndex = new ArrayList<>();
        dateList = new ArrayList<>();
        this.location = location;
        initLists(location);
        sharedPreferences = context.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
        priceOption = sharedPreferences.getInt("PriceOption", 0);
        //priceId = Integer.parseInt(FileUtils.readPriceConfig(context));
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_row2,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.extras = convertView.findViewById(R.id.extras);
            viewHolder.location = convertView.findViewById(R.id.location);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.icon = convertView.findViewById(R.id.icon);
            viewHolder.frame = convertView.findViewById(R.id.relativeLayout);
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.progressBar = convertView.findViewById(R.id.loadingImage);
            viewHolder.openingtime = convertView.findViewById(R.id.openingtime);
            convertView.setTag(viewHolder);
        } else {
          viewHolder = (ViewHolder) convertView.getTag();
        }


        MahlzeitObject obj = list.get(position);

        if (Config.SHOWOPENINGTIME){
            int dindex = dateIndex.get(position);
            String dateString = dateList.get(dindex);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                cal.setTime(sdf.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            isFriday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
        }




        String title = EscapeUtils.unescape(obj.getLabel());
        if (title.length() > 45 && title.length() < 60 && Config.imageLoad == true){
            viewHolder.title.setTextSize(13);
        } else if (title.length() > 60 && Config.imageLoad == true) {
            viewHolder.title.setTextSize(11);
        } else {
            viewHolder.title.setTextSize(15);
        }
        viewHolder.title.setText(title);
        viewHolder.location.setText(obj.getThekenLabel());
        if (Config.SHOWOPENINGTIME){
            viewHolder.openingtime.setText(OpeningTimes.getOpeningTimes(location, obj.getThekenLabel(), isFriday));
        } else {
            viewHolder.openingtime.setText("");
        }


        if (obj.getPrice().length == 3){
            if (String.valueOf(obj.getPrice()[priceOption]).length() == 3){
                viewHolder.price.setText(obj.getPrice()[priceOption]+"0€");
            } else {
                viewHolder.price.setText(obj.getPrice()[priceOption] + "€");
            }

        }


        final ViewHolder finalViewHolder = viewHolder;
        String url = null;
        if (obj.getHauptspeise().size() > 0){
            url = obj.getHauptspeise().get(0).getUrl();
        }

        if (Config.imageLoad){
            Picasso.get().load("https://studiwerk.de/eo/scale?s=mensa_detail&p=" + url).into(viewHolder.icon, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    finalViewHolder.icon.setVisibility(View.VISIBLE);
                    finalViewHolder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } else {
            finalViewHolder.progressBar.setVisibility(View.GONE);
            finalViewHolder.icon.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int px = dpToPixel(16);
            int px2 = dpToPixel(4);
            params.setMargins(px,px,px,px);
            params2.setMargins(0,0,0,px2);
            viewHolder.frame.setLayoutParams(params);
            viewHolder.title.setLayoutParams(params2);
        }


        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        final HeaderVewHolder holder;
        if (convertView == null){
            holder = new HeaderVewHolder();
            convertView = inflater.inflate(R.layout.listview_header,parent,false);
            holder.title = convertView.findViewById(R.id.headerDate);
            holder.headerContainer = convertView.findViewById(R.id.header_Layout);
            convertView.setTag(holder);
        } else {
            holder = (HeaderVewHolder) convertView.getTag();
        }

        String date = dateList.get(dateIndex.get(position));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        holder.headerContainer.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (Config.MARGIN == 0){
                Resources r = context.getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
                Config.MARGIN = (int) (holder.headerContainer.getMeasuredHeight() - px);
            }
        });

        Date displayDate = null;
        try {
            displayDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE '('dd.MM')'");
        String newDate = formatter.format(displayDate);
        holder.title.setText(newDate);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return dateIndex.get(position);
    }

    public static class ViewHolder{
        TextView title,extras,location,price,openingtime;
        CircularImageView icon;
        ProgressBar progressBar;
        RelativeLayout frame;
    }

    public static class HeaderVewHolder{
        TextView title;
        RelativeLayout headerContainer;
    }

    public void initLists(int location){
        switch (location){
            case 0:
                list = MensaTarforstDataHolder.getInstance().getAllInOneList();
                dateList = MensaTarforstDataHolder.getInstance().getDateList();
                dateIndex = MensaTarforstDataHolder.getInstance().getDateIndex();
                break;
            case 1:
                list = MensaBistroDataHolder.getInstance().getAllInOneList();
                dateList = MensaBistroDataHolder.getInstance().getDateList();
                dateIndex = MensaBistroDataHolder.getInstance().getDateIndex();
                break;
            case 2:
                list = MensaPetrisbergDataHolder.getInstance().getAllInOneList();
                dateList = MensaPetrisbergDataHolder.getInstance().getDateList();
                dateIndex = MensaPetrisbergDataHolder.getInstance().getDateIndex();
                break;
        }
    }

    private int dpToPixel(int dp){
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

}
