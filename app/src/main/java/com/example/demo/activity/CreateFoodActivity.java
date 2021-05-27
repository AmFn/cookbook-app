package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.utils.ToastUtil;
import com.just.agentweb.core.AgentWeb;
import com.just.agentweb.core.client.DefaultWebClient;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static com.example.demo.utils.ContextUtil.getContext;

public class CreateFoodActivity extends AppCompatActivity {
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.myflowTagLayout)
    FlowTagLayout flowTagLayout;
    @BindView(R.id.view)
    View  view;

    List<String> flowadd=new ArrayList<>();

AgentWeb mAgentWeb;
    ArrayList<String> photoPaths;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);
        ButterKnife.bind(this);
        XUI.initTheme(this);
        QMUIStatusBarHelper.translucent(this);
        initTopBar();
        initTag();

    }


    private void initTopBar() {
        QMUIStatusBarHelper.translucent(this);
        mTopBar.setBackgroundColor(ContextCompat.getColor(context, R.color.cook_app_yellow));
        mTopBar.setTitle("发布").setTextColor(getResources().getColor(R.color.white));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

    }

    @OnClick(R.id.uploadPicture)
    void uploadPic(){

    }
    private  void initTag(){

        flowadd.add("主食");
        flowadd.add("热菜");
        flowadd.add("汤菜");
        flowadd.add("早餐");
        flowadd.add("烘焙");
        flowadd.add("凉菜");

        flowTagLayout.setItems(flowadd);


    }


}