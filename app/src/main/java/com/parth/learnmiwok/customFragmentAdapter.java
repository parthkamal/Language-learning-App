package com.parth.learnmiwok;


import android.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.parth.learnmiwok.fragments.ColorsFragment;
import com.parth.learnmiwok.fragments.FamilyFragment;
import com.parth.learnmiwok.fragments.NumbersFragment;
import com.parth.learnmiwok.fragments.PhrasesFragment;

public class customFragmentAdapter extends FragmentPagerAdapter {


    public customFragmentAdapter(androidx.fragment.app.FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0)
            fragment = new NumbersFragment();
        else if (position == 1)
            fragment = new ColorsFragment();
        else if (position == 2)
            fragment = new FamilyFragment();
        else if (position ==3)
            fragment = new PhrasesFragment();

        return fragment;
    }

    @Override
    public int getCount()
    {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "NUMBERS";
        else if (position == 1)
            title = "COLORS";
        else if (position == 2)
            title = "FAMILY";
        else if (position ==3)
            title ="PHRASES";
        return title;
    }

}
