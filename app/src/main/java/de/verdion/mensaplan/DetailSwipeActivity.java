package de.verdion.mensaplan;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.verdion.mensaplan.Adapter.DetailSwipeAdapter;
import de.verdion.mensaplan.DataHolder.MensaBistroDataHolder;
import de.verdion.mensaplan.DataHolder.MensaPetrisbergDataHolder;
import de.verdion.mensaplan.DataHolder.MensaTarforstDataHolder;
import de.verdion.mensaplan.HelperClasses.BitmapShare;
import de.verdion.mensaplan.Logger.CLog;

public class DetailSwipeActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    private CircularImageView icon;
    private ImageButton icon2;
    private TextView title;
    private RelativeLayout transitionContainer;
    private ArrayList<String> datelist;
    private ArrayList<Integer> indexList;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_swipe);

        bundle = getIntent().getExtras();
        setDatelist(bundle.getInt("location"));
        setActionbarDate(bundle.getInt("position"));

        viewPager = (ViewPager) findViewById(R.id.pagerContainer);
        icon = (CircularImageView) findViewById(R.id.detailIconSwipe);
        icon.setImageDrawable(BitmapShare.getInstance().bitmapDrawable);
        icon2 = (ImageButton) findViewById(R.id.detailIcon2Swipe);
        title = (TextView) findViewById(R.id.detailTitleSwipe);
        transitionContainer = (RelativeLayout) findViewById(R.id.TransitionContainer);

        title.setText(bundle.getString("title"));

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

                        }
                    }.start();
                }
            });
        } else {
            transitionContainer.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        }


        DetailSwipeAdapter adapter = new DetailSwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(bundle.getInt("position"));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setActionbarDate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void reveal(){
        if (Build.VERSION.SDK_INT >= 21){
            final int cx = icon.getWidth() / 2;
            final int cy = icon.getHeight() / 2;

            final float initialRadius = (float) Math.hypot(cx,cy);
            final Animator anim = ViewAnimationUtils.createCircularReveal(icon, cx, cy, initialRadius, 0);
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
            icon2.setImageDrawable(BitmapShare.getInstance().bitmapDrawable);
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
                    transitionContainer.setVisibility(View.GONE);
                    viewPager.setVisibility(View.VISIBLE);
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

    private void setDatelist(int location){
        switch (location){
            case 0:
                datelist = MensaTarforstDataHolder.getInstance().getDateList();
                indexList = MensaTarforstDataHolder.getInstance().getDateIndex();
                break;
            case 1:
                datelist = MensaBistroDataHolder.getInstance().getDateList();
                indexList = MensaBistroDataHolder.getInstance().getDateIndex();
                break;
            case 2:
                datelist = MensaPetrisbergDataHolder.getInstance().getDateList();
                indexList = MensaPetrisbergDataHolder.getInstance().getDateIndex();
                break;
        }
    }

    private void setActionbarDate(int position){
        String date = datelist.get(indexList.get(position));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date displayDate = null;
        try {
            displayDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE', den' dd.MM");
        String newDate = formatter.format(displayDate);

        getSupportActionBar().setTitle(newDate);
    }

    @Override
    public void onBackPressed() {
        viewPager.setVisibility(View.GONE);
        transitionContainer.setVisibility(View.VISIBLE);
        super.onBackPressed();

    }
}
