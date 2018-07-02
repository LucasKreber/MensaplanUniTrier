package de.verdion.mensaplan;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.verdion.mensaplan.Adapter.ListViewAdapterBeilagen;
import de.verdion.mensaplan.Adapter.ListViewAdapterZusatz;
import de.verdion.mensaplan.DataHolder.EssenObject;
import de.verdion.mensaplan.DataHolder.MahlzeitObject;
import de.verdion.mensaplan.DataHolder.MensaBistroDataHolder;
import de.verdion.mensaplan.DataHolder.MensaPetrisbergDataHolder;
import de.verdion.mensaplan.DataHolder.MensaTarforstDataHolder;
import de.verdion.mensaplan.DataHolder.ZusatzObject;
import de.verdion.mensaplan.HelperClasses.AverageColor;
import de.verdion.mensaplan.HelperClasses.BitmapShare;
import de.verdion.mensaplan.Logger.CLog;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CircularImageView icon;
    private ImageButton icon2;
    private RelativeLayout iconBackground;
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
    private int clickIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bundle = getIntent().getExtras();

        icon = findViewById(R.id.detailIcon);
        icon2 = findViewById(R.id.detailIcon2);
        iconBackground = findViewById(R.id.iconBackground);
        title = findViewById(R.id.detailTitle);
        zusatzListView = findViewById(R.id.listViewZusatz);
        beilagenListView = findViewById(R.id.listViewBeilagen);
        beilagenLayout = findViewById(R.id.layoutBeilagen);
        zusatzLayout = findViewById(R.id.layoutZusatz);
        ratinIcon = findViewById(R.id.ratingIcon);
        ratingTextview = findViewById(R.id.ratingTextview);
        animation = AnimationUtils.loadAnimation(this,R.anim.like_animation);

        ratinIcon.setOnClickListener(this);
        icon2.setOnClickListener(this);


        zusatzListView.setDivider(null);
        beilagenListView.setDivider(null);
        context = this;
        zusatzList = generateZusatzList(bundle.getInt("location"),bundle.getInt("position"));
        setLayoutWeight();

        title.setText(bundle.getString("title"));
        title.setMaxWidth(bundle.getInt("titleSize"));
        //icon.setImageDrawable(BitmapShare.getInstance().bitmapDrawable);
        if (Build.VERSION.SDK_INT >= 21){
            icon.post(new Runnable() {
                @Override
                public void run() {
                    reveal();
                    new CountDownTimer(500,500){

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            initListViews();
                        }
                    }.start();
                }
            });
        } else {
            initListViews();
            //icon2.setImageDrawable(BitmapShare.getInstance().bitmapDrawable);
            icon2.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    private void reveal(){
        if (Build.VERSION.SDK_INT >= 21){
            final int cx = icon.getWidth() / 2;
            final int cy = icon.getHeight() / 2;

            final float initialRadius = (float) Math.hypot(cx,cy);
            final Animator anim = ViewAnimationUtils.createCircularReveal(icon,cx,cy,initialRadius,0);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    animation2();
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            new CountDownTimer(500,500){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    anim.start();
                }
            }.start();

        }

    }

    private void animation2(){
        if (Build.VERSION.SDK_INT >= 21){
            //icon2.setImageDrawable(BitmapShare.getInstance().bitmapDrawable);
            final int cx = icon2.getWidth() / 2;
            final int cy = icon2.getHeight() / 2;

            final float initialRadius = (float) Math.hypot(cx,cy);

            final Animator anim2 = ViewAnimationUtils.createCircularReveal(icon2,cx,cy,0,initialRadius);
            anim2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    //icon.setVisibility(View.INVISIBLE);
                    icon2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim2.start();
        }

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

        CLog.p("LISTE", list);
        return list;
    }

    private ArrayList<ZusatzObject> generateZusatzList(int location,int position){
        ArrayList<MahlzeitObject> list = getAllinOneList(location);
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
        if (obj.isGluten()){
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

    private void setLayoutWeight(){
        float weight = 0.05f;
        switch (zusatzList.size()){
            case 1:
                weight+=0.08;
                break;
            case 2:
                weight+=0.15;
                break;
            case 3:
                weight+=0.3;
                break;
            case 4:
                weight+=0.4;
                break;
            case 5:
                weight+=0.4;
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

    private void initListViews(){
        adapterBeilagen = new ListViewAdapterBeilagen(context,getAllinOneList(bundle.getInt("location")), bundle.getInt("position"));
        adapter = new ListViewAdapterZusatz(context,zusatzList);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        ScaleInAnimationAdapter animationAdapter2 = new ScaleInAnimationAdapter(adapterBeilagen);
        animationAdapter2.setAbsListView(beilagenListView);
        animationAdapter.setAbsListView(zusatzListView);
        zusatzListView.setAdapter(animationAdapter);
        beilagenListView.setAdapter(animationAdapter2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        title.setGravity(Gravity.NO_GRAVITY);
        title.setTextSize(15);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ratingIcon:

                break;
            case R.id.detailIcon2:
                detectDoubleClick();
                break;
        }
    }

    private void like(){
        ratinIcon.setImageResource(R.mipmap.ic_favorite_black_24dp);
        ratinIcon.startAnimation(animation);
    }

    private void detectDoubleClick(){
        clickIndex++;
        Handler handler = new Handler();
        Runnable r = new Runnable() {

            @Override
            public void run() {
                clickIndex = 0;
            }
        };

        if (clickIndex == 1) {
            //Single click
            handler.postDelayed(r, 250);
        } else if (clickIndex == 2) {
            //Double click
            clickIndex = 0;
            like();
        }
    }
}
