package com.example.demo.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.demo.R;
import com.example.demo.activity.CreateFoodActivity;
import com.example.demo.activity.CreateWebActivity;
import com.example.demo.activity.FoodClassActivity;
import com.example.demo.activity.FoodDetailActivity;
import com.example.demo.activity.LoginActivity;
import com.example.demo.activity.SearchActivity;
import com.example.demo.activity.TestActivity;
import com.example.demo.adapter.GridSpacingItemDecoration;
import com.example.demo.adapter.HomeFoodAdapter;
import com.example.demo.adapter.MenuItemAdapter;
import com.example.demo.api.CookBookApi;
import com.example.demo.entity.BaseResponse;
import com.example.demo.entity.CookBook;
import com.example.demo.entity.MenuItem;
import com.example.demo.entity.User;
import com.example.demo.utils.AlertDialogUtils;
import com.example.demo.utils.ConstantUtil;
import com.example.demo.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Home1Fragment extends Fragment {
//
//    @BindView(R.id.topbar)
//    QMUITopBar mTopBar;


    private SimpleImageBanner mSimpleImageBanner;
    private List<BannerItem> mData;

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3996333838,1164261288&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1263169584,1377377652&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4272708747,1211353331&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1497219145,4051749339&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2161874664,1596730071&fm=26&gp=0.jpg",
    };



    @BindView(R.id.edt_search)
    EditText editText;

    @BindView(R.id.recyclerView_menu)
    RecyclerView menuRecyclerView;
    MenuItemAdapter menuItemAdapter;
    List<MenuItem> menuItems ;

    @BindView(R.id.rv_food_list)
    RecyclerView foodRecyclerView;

    @BindView(R.id.rv_food_list2)
    RecyclerView foodRecyclerView2;

    HomeFoodAdapter foodAdapter;
    HomeFoodAdapter foodAdapter2;
    List<CookBook> cookBooks;
    List<CookBook> cookBooks2;

    User user;
private Context context;

    @OnClick(R.id.add_recipe)
        public void onAddRecipeClicked(){
        int id =PreferenceUtils.getInt("userId",0);
        if (id !=0){
            getUserByID(id);
            Intent intent = new Intent(getContext(), CreateWebActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }else {
            AlertDialogUtils.showDialog(getContext(), "你必须登录才能发布", "是否跳转登录？", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

    private void getUserByID(int id) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.getUserById(id);
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                Log.e("response_body",response.body().toString());
                if(baseResponse.getSuccess() ) {
                    user = baseResponse.getData().getUserInfo();


                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("errMsg", String.valueOf(t.getCause()));

            }
        });
    }



    public Home1Fragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home1, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initTopBar();
          editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
              @Override
              public void onFocusChange(View v, boolean hasFocus) {
                  toSearch();
              }
          });
        //获取banner数据
        initData();
        mSimpleImageBanner = view.findViewById(R.id.sib_simple_banner);
        sib_simple_usage();

        //获取菜单数据
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        menuItemAdapter = new MenuItemAdapter();
        menuRecyclerView.setAdapter(menuItemAdapter);
        loadMenuData();
        menuItemAdapter.setMenuItemOnClickListener(new MenuItemAdapter.MenuItemOnClickListener() {
            @Override
            public void onItemClick(MenuItem menuItem, int position) {


                MenuItem item = menuItems.get(position);
                Intent intent = new Intent(getContext(), FoodClassActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);

            }
        });

        //获取菜谱信息
        foodRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        foodRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 40, true));
        foodRecyclerView.setHasFixedSize(true);
        foodAdapter  = new HomeFoodAdapter();
        foodRecyclerView.setAdapter(foodAdapter);
        loadCookBookData();
       foodAdapter.setHomeFoodOnClickListener(new HomeFoodAdapter.HomeFoodOnClickListener() {
            @Override
            public void onItemClick(CookBook cookBook, int position) {

                Intent intent = new Intent(getContext(), FoodDetailActivity.class);
                intent.putExtra("cookBook",cookBook);
                startActivity(intent);


            }
       });
        //获取最新菜谱信息
        foodRecyclerView2.setLayoutManager(new GridLayoutManager(getContext(),2));
        foodRecyclerView2.addItemDecoration(new GridSpacingItemDecoration(2, 40, true));
        foodRecyclerView2.setHasFixedSize(true);
        foodAdapter2  = new HomeFoodAdapter();
        foodRecyclerView2.setAdapter(foodAdapter2);
        loadNewCookBookData();
        foodAdapter2.setHomeFoodOnClickListener(new HomeFoodAdapter.HomeFoodOnClickListener() {
            @Override
            public void onItemClick(CookBook cookBook, int position) {

                Intent intent = new Intent(getContext(), FoodDetailActivity.class);
                intent.putExtra("cookBook",cookBook);
                startActivity(intent);


            }
        });

    }

    private void loadNewCookBookData() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.listNewFood();
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                Log.e("response_body",response.body().toString());
                if(baseResponse.getSuccess() ) {
                    Log.e("res",baseResponse.toString());
                    List<CookBook> result =  baseResponse.getData().getFoodList();


                    Log.e("listSize", String.valueOf(result.size()));
                    cookBooks2 =result;
                    foodAdapter2.setData(result);
                }
                Log.e("cookbook",baseResponse.toString());


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("errMsg", String.valueOf(t.getCause()));

            }
        });
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
        Call<BaseResponse> repos = api.listHotFood();
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                Log.e("response_body",response.body().toString());
               if(baseResponse.getSuccess() ) {
                   Log.e("res",baseResponse.toString());
                   List<CookBook> result =  baseResponse.getData().getFoodList();


                   Log.e("listSize", String.valueOf(result.size()));
                   cookBooks =result;
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
        private void loadMenuData() {
        List<MenuItem> menuItemsData = new ArrayList<>();

        MenuItem item1 = new MenuItem(R.mipmap.icon_staple_food,1,"主食");
        MenuItem item2 = new MenuItem(R.mipmap.icon_hot_food,2,"热菜");
        MenuItem item3 = new MenuItem(R.mipmap.icon_soup_food,3,"汤菜");
        MenuItem item4 = new MenuItem(R.mipmap.icon_breakfast_food,4,"早餐");
        MenuItem item5 = new MenuItem(R.mipmap.icon_baking_food,5,"烘焙");
        MenuItem item6 = new MenuItem(R.mipmap.icon_steamed_food,6,"凉菜");
        menuItemsData.add(item1);
        menuItemsData.add(item2);
        menuItemsData.add(item3);
        menuItemsData.add(item4);
        menuItemsData.add(item5);
        menuItemsData.add(item6);

        menuItems = menuItemsData;

        menuItemAdapter.setData(menuItemsData);
    }



    private void sib_simple_usage() {
        mSimpleImageBanner.setSource(mData)
                .setOnItemClickListener(new BaseBanner.OnItemClickListener<BannerItem>() {
                    @Override
                    public void onItemClick(View view, BannerItem item, int position) {
                        Intent intent = new Intent(getContext(), TestActivity.class);
                        startActivity(intent);
                    }
                })
                .setIsOnePageLoop(false)//设置当页面只有一条时，是否轮播
                .startScroll();//开始滚动

    }

    private void initData(){
        mData = new ArrayList<>(urls.length);

        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = urls[i];


            mData.add(item);
        }
    }




    void toSearch(){



        if(editText.hasFocus()){
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
            editText.clearFocus();
        }

    }

}
