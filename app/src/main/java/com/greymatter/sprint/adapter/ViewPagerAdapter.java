package com.greymatter.sprint.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public ViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        /*switch (position) {
            case 0:
                ChallengesFragment challengesFragment = new ChallengesFragment();
                return challengesFragment;
            case 1:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 2:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            default:
                return null;
        }*/
        return null;
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
