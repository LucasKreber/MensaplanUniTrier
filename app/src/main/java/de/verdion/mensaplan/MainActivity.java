package de.verdion.mensaplan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codebutler.farebot.NfcOffFragment;
import com.codebutler.farebot.card.desfire.DesfireException;

import de.verdion.mensaplan.Adapter.SectionsPagerAdapter;
import de.verdion.mensaplan.DataHolder.Config;
import de.verdion.mensaplan.DataHolder.FilterConfig;
import de.verdion.mensaplan.Dialogs.FilterDialog;
import de.verdion.mensaplan.Dialogs.RatingDialog;
import de.verdion.mensaplan.Dialogs.VersionDialog;
import de.verdion.mensaplan.Fragments.MensaTarforstFragment;import de.verdion.mensaplan.HelperClasses.IdShare;
import de.verdion.mensaplan.Interfaces.InterfaceSemesterLoad;
import de.verdion.mensaplan.Logger.CLog;
import de.verdion.mensaplan.Network.LoadMessage;
import de.verdion.mensaplan.cardreader.Readers;
import de.verdion.mensaplan.cardreader.ValueData;

public class MainActivity extends AppCompatActivity implements InterfaceSemesterLoad {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Dialog dialog;
    private static MainActivity mainActivity;

    public static String TAG = "Test";
    public static final String ACTION_FULLSCREEN = "de.verdion.mensaapp";
    public static final String EXTRA_VALUE = "valueData";
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private IntentFilter mIntentFilter;
    private boolean isNfcworking = false;
    private AlertDialog dialogNewVersion;
    private boolean firstStart;
    private boolean blockMessage = false;
    private static String nfcError, nfcError2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        mainActivity = this;
        nfcError = getResources().getString(R.string.nfc_error);
        nfcError2 = getResources().getString(R.string.nfc_error2);
        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (adapter != null){
            isNfcworking = true;
            Config.NFC_ACTIVE = true;
        }

        sharedPreferences = this.getSharedPreferences("de.verdion.mensaplan", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Config.imageLoad = sharedPreferences.getBoolean("image_load", true);
        Config.SHOWOPENINGTIME = sharedPreferences.getBoolean("openingtime", true);

        int ratingOptions = sharedPreferences.getInt("ratingOption", 0);
        if (ratingOptions != -1) {
            if (ratingOptions > 10) {
                RatingDialog ratingDialog = new RatingDialog();
                ratingDialog.show(this);
            } else {
                editor.putInt("ratingOption", ++ratingOptions);
                editor.commit();
            }
        }

        if (!isNfcworking && !sharedPreferences.getBoolean("nfcErrorShowed", false)){
            editor.putBoolean("nfcErrorShowed", true);
            editor.commit();
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.no_nfc_title))
                    .setMessage(getString(R.string.no_nfc_message))
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).show();
        }

        if (!sharedPreferences.getBoolean("isFirstStart", false)){
            boolean showVersionDialog = false;

            int lastVersionCode = sharedPreferences.getInt("lastVersionCode", 1);
            int versionCode;
            try {
                PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                versionCode = pInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                versionCode = -1;
            }

            if (versionCode != -1 && versionCode > lastVersionCode) {
                showVersionDialog = true;
            }

            if (showVersionDialog){
                blockMessage = true;
                editor.putInt("lastVersionCode", versionCode);
                editor.commit();
                VersionDialog versionDialog = new VersionDialog();
                versionDialog.show(this);
            }

            new LoadMessage(MainActivity.this,this, blockMessage).execute();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (isNfcworking){
            mAdapter = NfcAdapter.getDefaultAdapter(this);
            mIntentFilter = new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED");

            mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                    getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
            mFilters = new IntentFilter[] { tech, };
            mTechLists = new String[][] { new String[] { IsoDep.class.getName(),
                    NfcA.class.getName() } };

            if (getIntent().getAction() != null && getIntent().getAction().equals(ACTION_FULLSCREEN)) {
                ValueData valueData = (ValueData) getIntent().getSerializableExtra(EXTRA_VALUE);
                Log.w(TAG,"restoring data for fullscreen");
                Log.d("Test", valueData.value + "(ACTION_FULLSCREEN");

            }
        } else {
            CLog.p("Kein NFC");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            finish();
            startActivity(intent);
            return true;
        } else if (id == R.id.action_burger){
            String url = "https://burgenerator.de/";
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.enableUrlBarHiding();
            builder.setShowTitle(true);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(url));
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_filter) {
            FilterDialog filterDialog = new FilterDialog(this, filterChanged -> {
                if (filterChanged) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }

            });
            filterDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }



    /*private void showVersionDialog(){
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(this);
        alerDialogBuilder.setTitle(getString(R.string.new_in_this_version_title))
                .setMessage(getString(R.string.new_in_this_version_content))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtils.writeFileToStorage(MainActivity.this,"6","version");
                        dialog.dismiss();
                    }
                });

        dialogNewVersion = alerDialogBuilder.create();
        dialogNewVersion.show();
    }*/

    /*private void showRatingDialog(){
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(this);
        alerDialogBuilder.setTitle(getString(R.string.rating_title))
                .setMessage(getString(R.string.rating_message))
                .setCancelable(false)
                .setNeutralButton("bewerten", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtils.writeFileToStorage(MainActivity.this,"1:0", "rating");
                        Uri uri = Uri.parse("market://details?id=" + MainActivity.this.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                        }
                    }
                })
                .setNegativeButton("Später", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtils.writeFileToStorage(MainActivity.this,"0:0", "rating");
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtils.writeFileToStorage(MainActivity.this, "1:0", "rating");
                    }
                });

        AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();
    }*/

    /*private boolean checkConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }*/


    /*@Override
    public void onBackPressed(){
        if (dialogChoice != null){
            dialogChoice.dismiss();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                finish();
            }
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        if (isNfcworking){
            getApplicationContext().registerReceiver(mReceiver, mIntentFilter);
            updateNfcState();
            mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
                    mTechLists);
        }

    }

    @Override
    public void onNewIntent(Intent intent) {
        if (isNfcworking){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(50);
            Log.i(TAG, "Foreground dispatch");
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
                Log.i(TAG,"Discovered tag with intent: " + intent);
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);


                try {
                    ValueData val = Readers.getInstance().readTag(tag);
                    Log.w(TAG,"Setting read data");

                } catch (DesfireException e) {
                    Toast.makeText(this,"Communication failed",Toast.LENGTH_SHORT).show();
                }
            } else if (getIntent().getAction().equals(ACTION_FULLSCREEN)) {
                ValueData valueData = (ValueData) getIntent().getSerializableExtra(EXTRA_VALUE);
                Log.d("Test", valueData+"");

            }
        }

    }

    public void updateNfcState() {

        try {
            if (!mAdapter.isEnabled()) {
                SharedPreferences prefs = this.getSharedPreferences("showNFCOff", Context.MODE_PRIVATE);
                Config.NFC_OFF = true;
                if (prefs.getInt("showNFCOff", 0) == 0){
                    NfcOffFragment f = new NfcOffFragment();
                    f.show(getSupportFragmentManager(), NfcOffFragment.TAG);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("showNFCOff", 1);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Dieser Hinweis wird in Zukunft nicht mehr angezeigt", Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e){

        }

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (isNfcworking){
                if (NfcAdapter.ACTION_ADAPTER_STATE_CHANGED.equals(action)) {
                    updateNfcState();
                }
            }
        }
    };

    public static void showValue(String value){
        MensaTarforstFragment fragment = (MensaTarforstFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        if (fragment == null) {
            Toast.makeText(mainActivity,nfcError,
                    Toast.LENGTH_SHORT).show();
        } else if (value == null){
            fragment.showCardValue(nfcError2);
        } else {
            fragment.showCardValue(value);
        }
    }

    @Override
    public void isSemesterLoaded() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(sharedPreferences.getInt("location", 0));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                IdShare.getInstance().pageId = mViewPager.getCurrentItem();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
}


