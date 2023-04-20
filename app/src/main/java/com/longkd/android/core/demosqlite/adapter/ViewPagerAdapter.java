package com.longkd.android.core.demosqlite.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.longkd.android.core.demosqlite.fragment.HistoryFragment;
import com.longkd.android.core.demosqlite.fragment.HomeFragment;
import com.longkd.android.core.demosqlite.fragment.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: fragment = new HomeFragment();
                break;
            case 1: fragment = new HistoryFragment();
                break;
            case 2: fragment = new SearchFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
