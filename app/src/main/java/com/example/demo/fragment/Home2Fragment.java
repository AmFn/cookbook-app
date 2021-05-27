package com.example.demo.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.R;
import com.example.demo.adapter.ChildViewPager;
import com.example.demo.adapter.FindPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Home2Fragment extends Fragment {



    @BindView(R.id.view_pager_home2)
    ViewPager viewPager;
    @BindView(R.id.tabs_home2)
    TabLayout tabLayout;
    @BindView(R.id.bar_height)
    View view;
    public Home2Fragment() {
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home2, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        int statusBarHeight = QMUIStatusBarHelper.getStatusbarHeight(getActivity());
//        Log.e("height", String.valueOf(statusBarHeight));
//        if (statusBarHeight > 0) {
//            ViewGroup.LayoutParams sp_params = view.getLayoutParams();
//           sp_params.height = statusBarHeight;
//            view.setLayoutParams(sp_params);
//        }


        FindPageAdapter adpter = new  FindPageAdapter(getActivity().getSupportFragmentManager());
       adpter.addFragment(new MyFollowFragment());
       adpter.addFragment(new SquareFragment());
        viewPager.setAdapter(adpter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();





    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
