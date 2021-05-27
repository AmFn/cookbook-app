package com.example.demo.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.demo.R;
import com.example.demo.adapter.UserListAdapter;
import com.example.demo.entity.User;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFansOrFollowActivity extends AppCompatActivity {
    @BindView(R.id.rv_user)
    RecyclerView recyclerView;

    UserListAdapter adapter ;

    Context context = this;
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans_or_follow);
        ButterKnife.bind(this);


        String type = getIntent().getStringExtra("type");
        Log.e("type",type);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListAdapter();
        adapter.setUserOnClickListener(new UserListAdapter.UserOnClickListener() {
            @Override
            public void onArtistClick(User user, int positon) {
                Intent intent = new Intent(context ,UserHomeActivity.class);

                intent.putExtra("userId",user.getUserId());
                startActivity(intent);

            }
        });
        User user = getIntent().getParcelableExtra("user");
        List<User> myFollows = user.getMyFollows();
        myFollows.forEach(item->Log.e("user",item.toString()));;

        if("1".equals(type)){
            initTopBar("关注");
            loadFcous(user);
        }else if ("2".equals(type)){
            initTopBar("粉丝");
            loadFans(user);
        }else {
            initTopBar("关注");
            Log.e("error","数据加载失败");
        }

    }

    private void loadFans(User user) {
        List<User> fans = user.getMyFans();

        adapter.setData(fans);
        recyclerView.setAdapter(adapter);

    }

    private void loadFcous(User user) {
        List<User> myFollows = user.getMyFollows();

        adapter.setData(myFollows);
        recyclerView.setAdapter(adapter);
    }

    private void initTopBar(String title) {
        QMUIStatusBarHelper.translucent(this);
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.cook_app_yellow));
        mTopBar.setTitle(title).setTextColor(getResources().getColor(R.color.white));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}