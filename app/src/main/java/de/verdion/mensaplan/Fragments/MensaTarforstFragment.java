package de.verdion.mensaplan.Fragments;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.DataHolder.MensaBistroDataHolder;
import de.verdion.mensaplan.DataHolder.MensaPetrisbergDataHolder;
import de.verdion.mensaplan.DetailSwipeActivity;
import de.verdion.mensaplan.HelperClasses.BitmapShare;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import de.verdion.mensaplan.Adapter.ListViewAdapter;
import de.verdion.mensaplan.DataHolder.MensaTarforstDataHolder;
import de.verdion.mensaplan.Logger.CLog;
import de.verdion.mensaplan.Network.LoadMensaData;
import de.verdion.mensaplan.R;

/**
 * Created by Lucas on 24.03.2016.
 */
public class MensaTarforstFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_SECTION_NUMBER = "location";
    private StickyListHeadersListView listView;
    private ProgressBar progressBar;
    private int location;
    public ListViewAdapter adapter;
    private RelativeLayout valueLayout;
    private TextView valueTextview;
    private TextView valueHeader;
    private Animation animationUp,animationDown;
    private int firstHeight = -1;
    private boolean blockAnimation;
    private int startHeight;
    private boolean isPositionFixed = false;


    public MensaTarforstFragment() {
    }


    public static MensaTarforstFragment newInstance(int sectionNumber) {
        MensaTarforstFragment fragment = new MensaTarforstFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (StickyListHeadersListView) rootView.findViewById(R.id.listView);
        valueLayout = (RelativeLayout) rootView.findViewById(R.id.priceValueRelative);
        valueTextview = (TextView) rootView.findViewById(R.id.priceValue);
        valueHeader = (TextView) rootView.findViewById(R.id.value_textview);
        animationUp = AnimationUtils.loadAnimation(getContext(),R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getContext(),R.anim.slide_down);
        listView.setDivider(null);
        listView.setOnItemClickListener(this);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarMain);
        boolean loadData = false;
        location = this.getArguments().getInt(ARG_SECTION_NUMBER);


        switch (location){
            case 0:
                if (MensaTarforstDataHolder.getInstance().getTagesTheken().size() == 0)
                    loadData = true;
                break;
            case 1:
                if (MensaBistroDataHolder.getInstance().getTagesTheken().size() == 0)
                    loadData = true;
                break;
            case 2:
                if (MensaPetrisbergDataHolder.getInstance().getTagesTheken().size() == 0)
                    loadData = true;
                break;
        }


        if (loadData){
            LoadMensaData loadMensaData = new LoadMensaData(getActivity(),listView,progressBar,location);
            loadMensaData.execute(this.getArguments().getInt(ARG_SECTION_NUMBER)+"");
        } else {
            adapter = new ListViewAdapter(getActivity(),location);
            ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
            StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
            stickyListHeadersAdapterDecorator.setStickyListHeadersListView(listView);
            listView.setAdapter(stickyListHeadersAdapterDecorator);
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            CLog.p("Loading Data from memory");
        }

        valueLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isPositionFixed){
                    startHeight=valueLayout.getMeasuredHeight();
                    //valueLayout.setY(valueLayout.getY()-getNavBarHeight(getContext()) + px);
                    //valueLayout.setY(1180);
                    isPositionFixed = true;
                }

            }
        });


        if (!Config.NFC_ACTIVE){
            valueLayout.setVisibility(View.GONE);
        }

        if (Config.NFC_OFF)
            valueHeader.setText("NFC einschalten, um Tunika-Guthaben zu lesen");

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CircularImageView icon = (CircularImageView) view.findViewById(R.id.icon);
        TextView title = (TextView) view.findViewById(R.id.title);
        BitmapShare.getInstance().bitmapDrawable = (BitmapDrawable) icon.getDrawable();

        Pair<View,String> p1 = Pair.create((View) icon, "icon");
        Pair<View,String> p2 = Pair.create((View) title, "title");

        Bundle bundle = new Bundle();
        bundle.putString("title", title.getText().toString());
        bundle.putInt("titleSize", title.getWidth());
        bundle.putInt("position", position);
        bundle.putInt("location", location);

        Intent intent = new Intent(getActivity(), DetailSwipeActivity.class);
        intent.putExtras(bundle);
        if (Config.imageLoad){
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),p1,p2);
            startActivity(intent,optionsCompat.toBundle());
        } else {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),p2);
            startActivity(intent,optionsCompat.toBundle());
        }

    }

    public void showCardValue(String value){
        if (!blockAnimation){
            blockAnimation = true;
            valueTextview.setVisibility(View.VISIBLE);
            valueTextview.setText(value);
            valueHeader.setTextSize(18);
            valueHeader.setText(getString(R.string.value_title));
            expand(valueLayout);

            new CountDownTimer(3500,3500){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    collapse(valueLayout);
                }
            }.start();
        }

    }


    public void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1){
                    v.getLayoutParams().height = startHeight;
                    v.requestLayout();

                }else{
                    if (interpolatedTime < 0.16){
                        valueHeader.setTextSize(17);
                    } else if (interpolatedTime > 0.16 && interpolatedTime < 0.32){
                        valueHeader.setTextSize(16);
                    }else if (interpolatedTime > 0.32 && interpolatedTime < 0.48){
                        valueHeader.setTextSize(15);
                    }else if (interpolatedTime > 0.48 && interpolatedTime < 0.64){
                        valueHeader.setTextSize(14);
                    }else if (interpolatedTime > 0.64 && interpolatedTime < 0.80){
                        valueHeader.setTextSize(13);
                    } else if (interpolatedTime > 0.80){
                        valueHeader.setTextSize(12);
                    }
                    if (initialHeight - (int)((initialHeight-startHeight) * interpolatedTime) >=
                            startHeight){
                        v.getLayoutParams().height = initialHeight - (int)((initialHeight-startHeight) * interpolatedTime);
                        v.requestLayout();
                    }
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density *2.5));
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                valueHeader.setText(getString(R.string.value_idle));
                valueTextview.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //valueHeader.setTextSize(12);
                valueTextview.setVisibility(View.GONE);
                blockAnimation = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    public void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        boolean removeNetworkCard = false;
        final float targetHeight = v.getMeasuredHeight();


        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? (int) (targetHeight)
                        : (int)((targetHeight-startHeight) * interpolatedTime + startHeight);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        final boolean finalRemoveNetworkCard = removeNetworkCard;
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density * 2.5));
        v.startAnimation(a);
    }
}
