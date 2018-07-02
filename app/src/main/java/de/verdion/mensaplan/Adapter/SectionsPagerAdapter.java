package de.verdion.mensaplan.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import de.verdion.mensaplan.Fragments.BurgergeneratorFragment;
import de.verdion.mensaplan.Fragments.MensaTarforstFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //if (position == 3){
        //    return BurgergeneratorFragment.newInstance();
        //} else {
            return MensaTarforstFragment.newInstance(position);
        //}


    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Mensa Tarforst";
            case 1:
                return "Bistro A/B";
            case 2:
                return "Mensa Petrisberg";
        }
        return null;
    }
}