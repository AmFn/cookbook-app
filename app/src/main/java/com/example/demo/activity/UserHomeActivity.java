package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.adapter.FoodClassAdapter;
import com.example.demo.api.CookBookApi;
import com.example.demo.entity.BaseResponse;
import com.example.demo.entity.CookBook;
import com.example.demo.entity.User;
import com.example.demo.utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.xuexiang.xui.widget.imageview.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserHomeActivity extends AppCompatActivity {
    @BindView(R.id.rv_user_create)
    RecyclerView recyclerView;
    @BindView(R.id.user_desc)
    TextView desc;

    @BindView(R.id.count_fllow)
    TextView fllow;
    @BindView(R.id.count_fans)
    TextView fans;
    @BindView(R.id.user_avatar)
        ImageView imageView;
//    @BindView(R.id.topbar)
//    QMUITopBar mTopBar;
    @BindView(R.id.user_username)
    TextView username;
    FoodClassAdapter foodClassAdapter;
    private Context context = this;
    private  User pageUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
//        initTopBar();
        int userId = getIntent().getIntExtra("userId",0);
        foodClassAdapter = new FoodClassAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodClassAdapter);
        if(userId!=0){
            loadData(userId);
            loadUserInfo(userId);
        }

        foodClassAdapter.setClassFoodOnClickListener(new FoodClassAdapter.FoodClassOnClickListener() {
            @Override
            public void onItemClick(CookBook cookBook, int position) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("cookBook",cookBook);
                startActivity(intent);

            }
        });
    }

//    private void initTopBar() {
//        QMUIStatusBarHelper.translucent(this);
//        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
//        mTopBar.setTitle("用户名").setTextColor(getResources().getColor(R.color.black));
//        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//    }

    public void loadData(int uid) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.getClassFood(uid);
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse body = response.body();
                Log.e("BODY",body.getData().getFoodList().toString());
                List<CookBook> foodList = body.getData().getFoodList();
                foodClassAdapter.setData(foodList);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("error",t.getCause().toString());
            }
        });



    }


    public void loadUserInfo(int uid) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.getUserDetailById(uid);
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse body = response.body();
                Log.e("BODY",body.getData().getUser().toString());
                User user = body.getData().getUser();
                if (user!=null){
                    pageUser = user;
                    username.setText( user.getNickName());
                    ImageLoader.get().loadImage(imageView, user.getAvatar());
                    desc.setText(user.getDescription());
                    fllow.setText(String.valueOf(user.getFollowCount()));
                    fans.setText(String.valueOf(user.getFansCount()));
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("error",t.getCause().toString());
            }
        });



    }

}