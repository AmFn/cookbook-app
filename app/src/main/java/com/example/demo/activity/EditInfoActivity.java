package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.demo.R;
import com.example.demo.entity.User;
import com.example.demo.utils.ToastUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectChangeListener;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.demo.utils.AlertDialogUtils.mCurrentDialogStyle;

public class EditInfoActivity extends AppCompatActivity {
    @BindView(R.id.setting_groupListView)
    QMUIGroupListView mGroupListView;

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    @BindView(R.id.iv_edit_info_avatar)
    ImageView imageView;
    private Context context = this;
    User user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");
        if (user!= null) {
            user1 = user;
            initList(user1);
        }
        initTopBar();

    }
    private void initTopBar() {
        QMUIStatusBarHelper.translucent(this);
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.cook_app_yellow));
        mTopBar.setTitle("编辑资料").setTextColor(getResources().getColor(R.color.white));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void initList( User user) {
        Log.e("settingInfoPage",user.toString());
        QMUICommonListItemView avatar = mGroupListView.createItemView("头像");
        avatar.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
//        Glide.with(context)
//                .load(user.getAvatar())
//
//                .listener(new RequestListener<Drawable>() {
//
//                    @Override
//                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        Toast.makeText(context, "头像加载失败", Toast.LENGTH_SHORT).show();
//                        Log.v("glide", "Error loading image", e);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    return false;
//
//                    }
//                })
//                .into(imageView);
//        Log.e("glied","加载成功");
//        avatar.addAccessoryCustomView(imageView);




        QMUICommonListItemView username= mGroupListView.createItemView("用户名");
        username.setDetailText(user.getNickName());
        username.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView setPwd = mGroupListView.createItemView("密码设置");
        setPwd.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);


        QMUICommonListItemView  desc = mGroupListView.createItemView("简介");
        desc.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);




        QMUICommonListItemView gender = mGroupListView.createItemView("性别");
        gender.setDetailText("男");
        gender.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView setting = mGroupListView.createItemView("出生日期");
        setting.setDetailText("请设置出生日期");
        setting.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);



        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    if ("用户名".equals(text)){
                       editUserNamr();

                    }else if("简介".equals(text)){

                    }else if("性别".equals(text)){
                        editGender();

                    }else if("出生日期".equals(text)){
                        editBirthday();
                    }

                }
            }




        };

        QMUIGroupListView.newSection(this)
                .addItemView(avatar, onClickListener)
                .addItemView(username, onClickListener)
                .addItemView(setPwd, onClickListener)
                .addItemView(desc, onClickListener)
                .addItemView(gender, onClickListener)
                .addItemView(setting, onClickListener)
                .addTo(mGroupListView);


    }

    private void editBirthday() {
        TimePickerView mDatePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelected(Date date, View v) {

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setTitleText("日期选择")
                .build();




    }
    private void editGender() {
        final  String strs  []=new String[]{"男","女"};
        new QMUIDialog.MenuDialogBuilder(context)
                .addItems(strs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"我点第"+which+"个",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).create(mCurrentDialogStyle).show();
    }
    private void editUserNamr() {
        final  QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
        builder.setTitle("用户名")
                .setPlaceholder("请在此输入新的用户名")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                . addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        //获得出入的ID
                        String name = text.toString().trim();
                        if (!name.equals("")&&name!=null) {
                            user1.setNickName(name);
                            mGroupListView.removeAllViews();
                            initList(user1);
                            dialog.dismiss();

                        } else {
                            Toast.makeText(context, "请填入合法用户名", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
    }


}