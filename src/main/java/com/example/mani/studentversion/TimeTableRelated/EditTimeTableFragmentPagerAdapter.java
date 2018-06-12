package com.example.mani.studentversion.TimeTableRelated;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class EditTimeTableFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList< Fragment> fr;
    public EditTimeTableFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fr){
        super(fm);
        this.fr=fr;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return fr.get(0);
            case 1: return fr.get(1);
            case 2: return fr.get(2);
            case 3: return fr.get(3);
            case 4: return fr.get(4);

        }

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "Monday";
            case 1: return "Tuesday";
            case 2: return "Wednesday";
            case 3: return "Thrusday";
            case 4: return "Friday";

        }

        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
