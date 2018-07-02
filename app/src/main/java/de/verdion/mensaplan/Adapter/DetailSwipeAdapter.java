package de.verdion.mensaplan.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import de.verdion.mensaplan.DataHolder.MensaBistroDataHolder;
import de.verdion.mensaplan.DataHolder.MensaPetrisbergDataHolder;
import de.verdion.mensaplan.DataHolder.MensaTarforstDataHolder;
import de.verdion.mensaplan.Fragments.DetailFragment;
import de.verdion.mensaplan.HelperClasses.IdShare;

/**
 * Created by Lucas on 01.04.2016.
 */
public class DetailSwipeAdapter extends FragmentPagerAdapter {

    public DetailSwipeAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        int count = 0;
        switch (IdShare.getInstance().pageId){
            case 0:
                count = MensaTarforstDataHolder.getInstance().getAllInOneList().size();
                break;
            case 1:
                count = MensaBistroDataHolder.getInstance().getAllInOneList().size();
                break;
            case 2:
                count = MensaPetrisbergDataHolder.getInstance().getAllInOneList().size();
                break;
        }
        return count;
    }
}
