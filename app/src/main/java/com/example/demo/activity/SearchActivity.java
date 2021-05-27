package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.adapter.FoodClassAdapter;
import com.example.demo.api.CookBookApi;
import com.example.demo.entity.BaseResponse;
import com.example.demo.entity.CookBook;
import com.example.demo.utils.ConstantUtil;
import com.example.demo.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.edt_search)
    EditText editText;
    @BindView(R.id.rv_search)
    RecyclerView recyclerView;

    FoodClassAdapter foodClassAdapter;
    Context context = this;
    QMUITipDialog tipDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initTopBar();
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }, 50);

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


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i==IME_ACTION_SEARCH){
                    String content=editText.getText().toString();
                    if (TextUtils.isEmpty(content)){
                        ToastUtil.show("关键字为空！");
                    }else {
                        search();
                        return true;
                    }

                }
                return false;
            }
        });


    }

    private void initTopBar() {
        QMUIStatusBarHelper.translucent(this);
    }

    @OnClick(R.id.iv_icon_back)
    void back(){
        onBackPressed();
    }

    @OnClick(R.id.btn_search)
    void search(){
        foodClassAdapter.setData(null);
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        tipDialog.show();
        String text= "";
        text = editText.getText().toString();
        loadData(text);



    }

    private void loadData(String text) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.search(text);
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse body = response.body();
                Log.e("BODY",body.getData().getFoodList().toString());
                List<CookBook> foodList = body.getData().getFoodList();
                tipDialog.dismiss();

                foodClassAdapter.setData(foodList);
                if(foodList.size()==0){{
                    ToastUtil.show("没有搜索到相关菜谱！");
                }}

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("error",t.getCause().toString());
            }
        });


    }
}