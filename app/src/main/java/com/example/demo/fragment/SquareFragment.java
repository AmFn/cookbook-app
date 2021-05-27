package com.example.demo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.activity.FoodDetailActivity;
import com.example.demo.adapter.GridSpacingItemDecoration;
import com.example.demo.adapter.HomeFoodAdapter;
import com.example.demo.adapter.SquareFoodAdapter;
import com.example.demo.api.CookBookApi;
import com.example.demo.entity.BaseResponse;
import com.example.demo.entity.CookBook;
import com.example.demo.utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SquareFragment extends Fragment {
    @BindView(R.id.rv_square)
    RecyclerView foodRecyclerView;
    SquareFoodAdapter foodAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_square, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QMUIStatusBarHelper.translucent((Activity) getContext());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);

        foodRecyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        foodRecyclerView.setLayoutManager(layoutManager);


        foodAdapter  = new SquareFoodAdapter();

        loadCookBookData();
        foodAdapter.setSquareFoodOnClickListener((cookBook, position) -> {
            Log.e("test","点击了食物");
            Intent intent = new Intent(getContext(), FoodDetailActivity.class);
            intent.putExtra("cookBook",cookBook);
            startActivity(intent);





        });
        foodRecyclerView.setAdapter(foodAdapter);
    }

    private void  loadCookBookData() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.listNewFood(30);
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                Log.e("response_body",response.body().toString());
                if(baseResponse.getSuccess() ) {
                    Log.e("res",baseResponse.toString());
                    List<CookBook> result =  baseResponse.getData().getFoodList();


                    Log.e("listSize", String.valueOf(result.size()));

                    foodAdapter.setData(result);
                }
                Log.e("cookbook",baseResponse.toString());


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("errMsg", String.valueOf(t.getCause()));

            }
        });

    }

}
