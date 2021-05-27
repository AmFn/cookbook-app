package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.demo.activity.LoginActivity;
import com.example.demo.adapter.FragmentAdapter;
import com.example.demo.entity.User;
import com.example.demo.fragment.Home1Fragment;
import com.example.demo.fragment.Home2Fragment;
import com.example.demo.fragment.Home3Fragment;
import com.example.demo.fragment.NoLoginFragment;

import com.example.demo.manager.ActivityLifecycleManager;
import com.example.demo.utils.PreferenceUtils;
import com.example.demo.utils.ToastUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager)
    QMUIViewPager viewPager;

    private Context context = this;
    private MenuItem menuItem;

    Bundle bundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityLifecycleManager.get().finishAllActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.getParcelableExtra("user")!=null){
            Log.e("userID","有有用户信息");
            bundle =  new Bundle();
            bundle.putParcelable("user",intent.getParcelableExtra("user"));

        }
        initFragment();

        //设置viewpager可滑动
        viewPager.setSwipeable(false);



        viewPager.addOnPageChangeListener(pageChangeListener);
        bottomNavigationView.setItemIconTintList(null);
        viewPager.setOffscreenPageLimit(4);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
    private void initFragment() {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new Home1Fragment());
        fragmentAdapter.addFragment(new Home2Fragment());

        if( PreferenceUtils.getInt("userId",0)==0){
            fragmentAdapter.addFragment(new NoLoginFragment());
        }else {
            Home3Fragment home3Fragment;
            if(bundle!=null){
                home3Fragment = Home3Fragment.newInstance(bundle);
                home3Fragment.setArguments(bundle);
            }else {
                home3Fragment= new Home3Fragment();
            }
            fragmentAdapter.addFragment(home3Fragment);
        }



        viewPager.setAdapter(fragmentAdapter);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_1:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.bottom_navigation_2:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.bottom_navigation_3:


                        viewPager.setCurrentItem(2);

                    return true;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(position);
        }

        @Override
        public void onPageSelected(int position) {
            menuItem = bottomNavigationView.getMenu().getItem(position);
            menuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



}