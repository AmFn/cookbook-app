package com.example.demo.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.fragment.LoginFragment;

import java.util.ArrayList;
import java.util.List;


public class FindPageAdapter  extends FragmentPagerAdapter {
    private static final String[] PAGE_TITLE = {"关注", "广场"};
    private List<Fragment> fragments = new ArrayList<Fragment>();
    public void addFragment(Fragment fragment){
        if(fragment!=null){
            this.fragments.add(fragment);
        }
    }

    public FindPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       return   fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        {
            return PAGE_TITLE[position];
        }
    }
}
