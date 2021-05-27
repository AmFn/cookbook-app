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

import com.example.demo.R;
import com.example.demo.adapter.FoodClassAdapter;
import com.example.demo.api.CookBookApi;
import com.example.demo.entity.BaseResponse;
import com.example.demo.entity.CookBook;
import com.example.demo.entity.MenuItem;
import com.example.demo.utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodClassActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.rv_class_food)
    RecyclerView recyclerView;
    FoodClassAdapter foodClassAdapter;

    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_class);
        ButterKnife.bind(this);
        Intent intent =getIntent();
        foodClassAdapter = new FoodClassAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodClassAdapter);
        foodClassAdapter.setClassFoodOnClickListener(new FoodClassAdapter.FoodClassOnClickListener() {
            @Override
            public void onItemClick(CookBook cookBook, int position) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("cookBook",cookBook);
                startActivity(intent);

            }
        });
        MenuItem menuItem = intent.getParcelableExtra("item");
        int classId = menuItem.getClassId();
        if (classId!=0){
            loadData(classId);
            Log.e("信息","有id信息");

        }else {
            Log.e("信息","没有id信息");
        }

        initTopBar(menuItem.getName());

    }

    public void loadData(int classId) {
            Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.getClassFood(classId);
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

        private void initTopBar(String text) {
        QMUIStatusBarHelper.translucent(this);
        mTopBar.setBackgroundColor(ContextCompat.getColor(context, R.color.cook_app_yellow));
        mTopBar.setTitle(text).setTextColor(getResources().getColor(R.color.white));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}