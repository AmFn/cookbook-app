package com.example.demo.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.demo.activity.MyFoodActivity;
import com.example.demo.R;
import com.example.demo.activity.EditInfoActivity;
import com.example.demo.activity.MyFansOrFollowActivity;
import com.example.demo.activity.SettingActivity;
import com.example.demo.activity.UserHomeActivity;
import com.example.demo.api.CookBookApi;
import com.example.demo.entity.BaseResponse;
import com.example.demo.entity.User;
import com.example.demo.utils.ConstantUtil;
import com.example.demo.utils.PreferenceUtils;
import com.example.demo.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Home3Fragment extends Fragment {
    @BindView(R.id.my_mGroupListView)
    QMUIGroupListView mGroupListView;


    private  User user;

    @BindView(R.id.my_avatar)
    QMUIRadiusImageView avatar;
    @BindView(R.id.my_desc)
    TextView my_desc;

   @BindView(R.id.my_nickName)
    TextView textView;
   @BindView(R.id.tv_fans_count)
   TextView count_fans;
   @BindView(R.id.tv_focus_count)
   TextView count_focus;

   private Context context = getContext();
    public Home3Fragment() {
    }

    public  static  Home3Fragment newInstance(Bundle bundle){
        Home3Fragment fragment =  new Home3Fragment();
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home3, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QMUIStatusBarHelper.translucent((Activity) getContext());

        initList();
        Bundle bundle = getArguments();

        //已本地有登陆信息
        if (PreferenceUtils.getInt("userId",0) !=0){
            getUserByID(PreferenceUtils.getInt("userId",0));


        }

        //登录或注册跳转
        if(bundle != null&& bundle.containsKey("user")){
             user=bundle.getParcelable("user");
           loadInfo(user);

        }else {
            Log.e("error","没有用户信息");
        }




    }

    @OnClick(R.id.foucs_item)
    void  showMyFocus(){
        Intent intent = new Intent(getContext(), MyFansOrFollowActivity.class);

        intent.putExtra("type","1");
        intent.putExtra("user",user);
        startActivity(intent);
    }
    @OnClick(R.id.fan_item)
    void  showMyFans(){
        Intent intent = new Intent(getContext(), MyFansOrFollowActivity.class);
        intent.putExtra("type","2");
        intent.putExtra("user",user);
        startActivity(intent);
    }

    private void loadInfo(User user1) {
        if(user1.getNickName()!=null){
            textView.setText(user1.getNickName());
        }
        if(user1.getDescription()!=null){
            my_desc.setText(user1.getDescription());
        }
        if(user1.getAvatar()!=null){
            Glide.with(getContext())
                    .load(user1.getAvatar())

                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Toast.makeText(getContext(), "头像加载失败", Toast.LENGTH_SHORT).show();
                            Log.v("glide", "Error loading image", e);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(avatar);
        }

        count_fans.setText(String.valueOf(user1.getFansCount()));
        count_focus.setText(String.valueOf(user1.getFollowCount()));

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        Logger.d("第一页销毁");
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
        Call<BaseResponse> repos = api.getUserDetailById(id);
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                Log.e("detail",response.body().getData().getUser().toString());
                if(baseResponse.getSuccess() ) {
                    user = baseResponse.getData().getUser();
                    loadInfo(baseResponse.getData().getUser());

                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                getUserByID(id);
                Log.e("errMsg", String.valueOf(t.getCause()));

            }
        });
    }

    public void initList() {



        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.icon_edit_info);
        Drawable drawable = imageView.getDrawable();
        QMUICommonListItemView editUserInfo = mGroupListView.createItemView(drawable,"编辑资料",null, LinearLayout.VERTICAL,1);
       editUserInfo.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        ImageView imageView2 = new ImageView(getContext());
        imageView2.setImageResource(R.mipmap.icon_my_creater);
        Drawable drawable2 = imageView2.getDrawable();
        QMUICommonListItemView myPublish = mGroupListView.createItemView("我的发布");
        myPublish.setImageDrawable(drawable2);
        myPublish.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);


        ImageView imageView3 = new ImageView(getContext());
        imageView3.setImageResource(R.mipmap.icon_send_message);
        Drawable drawable3 = imageView3.getDrawable();
        QMUICommonListItemView  suggestion = mGroupListView.createItemView("意见反馈");
        suggestion.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        suggestion.setImageDrawable(drawable3);

        ImageView imageView4 = new ImageView(getContext());
        imageView4.setImageResource(R.mipmap.icon_my_setting_yellow);
        Drawable drawable4 = imageView4.getDrawable();
        QMUICommonListItemView setting = mGroupListView.createItemView("设置");
        setting.setImageDrawable(drawable4);
        setting.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);




        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    if ("设置".equals(text)) {
                        Intent intent = new Intent(getContext(), SettingActivity.class);
                        intent.putExtra( "user",user);
                        startActivity(intent);
                    } else if ("编辑资料".equals(text)) {
                        Intent intent = new Intent(getContext(), EditInfoActivity.class);
                        intent.putExtra( "user",user);
                        startActivity(intent);

                    }else if("我的发布".equals(text)) {
//                        Intent intent  = new Intent(context, UserHomeActivity.class);
//                        intent.putExtra("userId",user.getUserId());
                        int uid = 1;
                        Intent intent  = new Intent(getContext(), UserHomeActivity.class);
                        intent.putExtra("userId",uid);
                        startActivity(intent);
                    }
                    else {
                        ToastUtil.show("暂未开放");
                    }
                }
            }
        };

        QMUIGroupListView.newSection(getContext())
                .setOnlyShowMiddleSeparator(true)
                .setLeftIconSize(72,72)
                .addItemView(editUserInfo, onClickListener)
                .addItemView(myPublish, onClickListener)
                .addItemView(suggestion, onClickListener)
                .addItemView(setting, onClickListener)

                .addTo(mGroupListView);
    }

    

}


