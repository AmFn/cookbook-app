package com.example.demo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.R;
import com.example.demo.adapter.LoginViewPagerAdapter;
import com.example.demo.fragment.LoginFragment;
import com.example.demo.fragment.RegisterFragment;
import com.example.demo.manager.ActivityLifecycleManager;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    Fragment2Fragment fragment2Fragment;
    @BindView(R.id.vp_login)
    QMUIViewPager viewPager;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        LoginViewPagerAdapter adapter = new LoginViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(adapter);
        viewPager.setSwipeable(false);

        viewPager.setOffscreenPageLimit(2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    public interface Fragment2Fragment{
        void gotoFragment(QMUIViewPager viewPager);
    }

    public void setFragment2Fragment(Fragment2Fragment fragment2Fragment) {
        this.fragment2Fragment = fragment2Fragment;
    }
    public void forSkip(){
        if(fragment2Fragment != null){
            fragment2Fragment.gotoFragment(viewPager);
        }
    }


}
