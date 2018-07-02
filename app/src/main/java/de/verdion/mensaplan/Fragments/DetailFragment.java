package de.verdion.mensaplan.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.verdion.mensaplan.Adapter.ListViewAdapterBeilagen;
import de.verdion.mensaplan.Adapter.ListViewAdapterZusatz;
import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.DataHolder.EssenObject;
import de.verdion.mensaplan.DataHolder.MahlzeitObject;
import de.verdion.mensaplan.DataHolder.MensaBistroDataHolder;
import de.verdion.mensaplan.DataHolder.MensaPetrisbergDataHolder;
import de.verdion.mensaplan.DataHolder.MensaTarforstDataHolder;
import de.verdion.mensaplan.DataHolder.ZusatzObject;
import de.verdion.mensaplan.HelperClasses.EscapeUtils;
import de.verdion.mensaplan.HelperClasses.IdShare;
import de.verdion.mensaplan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageButton icon;
    private TextView title;
    private ListView zusatzListView, beilagenListView;
    private Bundle bundle;
    private ListViewAdapterZusatz adapter;
    private ListViewAdapterBeilagen adapterBeilagen;
    private RelativeLayout beilagenLayout, zusatzLayout;
    private Context context;
    private ArrayList<ZusatzObject> zusatzList;
    private ImageButton ratinIcon;
    private TextView ratingTextview;
    private Animation animation;
    private ArrayList<MahlzeitObject> list;
    private int id,today,all;
    private boolean liked;

    public DetailFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        list = getAllinOneList(IdShare.getInstance().pageId);
        context = getActivity();
        icon = v.findViewById(R.id.detailIcon2SwipeDetail);

        if (Config.imageLoad){
            Picasso.get().load("https://studiwerk.de/eo/scale?s=mensa_detail&p=" + list.get(getArguments().getInt("position")).getHauptspeise().get(0).getUrl()).into(icon);
        } else {
            Picasso.get().load("https://www.studiwerk.de/eo/scale?s=mensa_detail&p=/kiosk/mensa/img/food_default.png").into(icon);
        }

        title = v.findViewById(R.id.detailTitleSwipeDetail);
        zusatzListView = v.findViewById(R.id.listViewZusatzSwipeDetail);
        beilagenListView = v.findViewById(R.id.listViewBeilagenSwipeDetail);
        beilagenLayout = v.findViewById(R.id.layoutBeilagenSwipeDetail);
        zusatzLayout = v.findViewById(R.id.layoutZusatzSwipeDetail);
        ratinIcon = v.findViewById(R.id.ratingIconSwipeDetail);
        ratingTextview = v.findViewById(R.id.ratingTextviewSwipeDetail);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.like_animation);

        zusatzListView.setDivider(null);
        beilagenListView.setDivider(null);

        zusatzList = generateZusatzList(IdShare.getInstance().pageId, getArguments().getInt("position"));
        setLayoutWeight();

        title.setText(EscapeUtils.unescape(list.get(getArguments().getInt("position")).getLabel()));
        initListViews();


        return v;
    }


    private void initListViews(){
        adapterBeilagen = new ListViewAdapterBeilagen(context,list, getArguments().getInt("position"));
        adapter = new ListViewAdapterZusatz(context,zusatzList);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        ScaleInAnimationAdapter animationAdapter2 = new ScaleInAnimationAdapter(adapterBeilagen);
        animationAdapter2.setAbsListView(beilagenListView);
        animationAdapter.setAbsListView(zusatzListView);
        zusatzListView.setAdapter(animationAdapter);
        beilagenListView.setAdapter(animationAdapter2);
    }

    private void setLayoutWeight(){
        float weight = 0.05f;
        switch (zusatzList.size()){
            case 1:
                weight+=0.06;
                break;
            case 2:
                weight+=0.15;
                break;
            case 3:
                weight+=0.26;
                break;
            case 4:
                weight+=0.35;
                break;
            case 5:
                weight+=0.35;
                break;
            case 6:
                weight+=0.35;
                break;
            case 7:
                weight+=0.35;
                break;
        }

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params1.weight = weight;
        params2.weight = 1-weight;

        zusatzLayout.setLayoutParams(params1);
        beilagenLayout.setLayoutParams(params2);

    }

    private ArrayList<ZusatzObject> generateZusatzList(int location,int position){

        EssenObject obj = list.get(position).getHauptspeise().get(0);
        ArrayList<ZusatzObject> zusatz = new ArrayList<>();

        if (obj.isRind()){
            zusatz.add(new ZusatzObject("enthält Rindfleisch", R.mipmap.beef));
        }
        if (obj.isSchwein()){
            zusatz.add(new ZusatzObject("enthält Schweinefleisch", R.mipmap.pig));
        }
        if (obj.isHuhn()){
            zusatz.add(new ZusatzObject("enthält Geflügelfleisch", R.mipmap.chicken));
        }
        if (obj.isFisch()){
            zusatz.add(new ZusatzObject("enthält Fisch", R.mipmap.fish));
        }
        if (obj.isVegetarisch()){
            zusatz.add(new ZusatzObject("Vegetarisch", R.mipmap.veggie));
        }
        if (obj.isVegan()){
            zusatz.add(new ZusatzObject("Vegan", R.mipmap.vegan));
        }
        if (obj.isLaktosefrei()){
            zusatz.add(new ZusatzObject("enthält Laktose", R.mipmap.lactose));
        }

        if (obj.isGlutenWeizen() || obj.isGlutenRoggen() || obj.isGlutenGerste()){
            zusatz.add(new ZusatzObject("enthält Gluten", R.mipmap.gluten));
        }

        if (obj.isGlutenWeizen()){
            zusatz.add(new ZusatzObject("enthält Gluten: Weizen", R.mipmap.gluten));
        }
        if (obj.isGlutenRoggen()){
            zusatz.add(new ZusatzObject("enthält Gluten: Roggen", R.mipmap.gluten));
        }
        if (obj.isGlutenGerste()){
            zusatz.add(new ZusatzObject("enthält Gluten: Gerste", R.mipmap.gluten));
        }
        if (obj.isAlc()){
            zusatz.add(new ZusatzObject("enthält Alkohol", R.mipmap.alc));
        }

        return zusatz;
    }

    private ArrayList<MahlzeitObject> getAllinOneList(int location){
        ArrayList<MahlzeitObject> list = null;
        switch (location){
            case 0:
                list = MensaTarforstDataHolder.getInstance().getAllInOneList();
                break;
            case 1:
                list = MensaBistroDataHolder.getInstance().getAllInOneList();
                break;
            case 2:
                list = MensaPetrisbergDataHolder.getInstance().getAllInOneList();
                break;
        }

        return list;
    }

}
