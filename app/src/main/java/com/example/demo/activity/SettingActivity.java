package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.manager.ActivityLifecycleManager;
import com.example.demo.utils.CacheUtil;
import com.example.demo.utils.PreferenceUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.setting_groupListView)
    QMUIGroupListView mGroupListView;

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    Context context =  this ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initTopBar();
        initList();
    }
    private void initTopBar() {
        QMUIStatusBarHelper.translucent(this);
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.cook_app_yellow));
        mTopBar.setTitle("设置").setTextColor(getResources().getColor(R.color.white));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @OnClick(R.id.btn_logout)
    public  void logout(){
        PreferenceUtils.remove("userId");
        ActivityLifecycleManager.get().finishAllActivity();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void initList() {


        QMUICommonListItemView bind = mGroupListView.createItemView("账号绑定");
        bind.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView setPwd = mGroupListView.createItemView("密码设置");
        setPwd.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);


        QMUICommonListItemView  message = mGroupListView.createItemView("推送通知");

        message.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        message.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(context, "已打开消息推送", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "已关闭消息推送", Toast.LENGTH_SHORT).show();
                }

            }
        });



        QMUICommonListItemView setting = mGroupListView.createItemView("清除缓存");
        setting.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        try {
            String totalCacheSize = CacheUtil.getTotalCacheSize(this);
            setting.setDetailText(totalCacheSize);

        } catch (Exception e) {
            e.printStackTrace();
        }





        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    if("清除缓存".equals(text)){
                        cleanCache();

                    }

                }
            }
        };

        QMUIGroupListView.newSection(this)
                .addItemView(bind, onClickListener)
                .addItemView(setPwd, onClickListener)
                .addTo(mGroupListView);

        QMUIGroupListView.newSection(this).addItemView(message,onClickListener)
                .addItemView(setting, onClickListener).addTo(mGroupListView);
    }

    public void cleanCache() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setMessage("清除缓存")
                .setTitle("确认要清除吗?")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                })
                .addAction("确认", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {

                        CacheUtil.clearAllCache(context);
                        Toast.makeText(getApplicationContext(), "清除成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                })
                .show();
    }


}