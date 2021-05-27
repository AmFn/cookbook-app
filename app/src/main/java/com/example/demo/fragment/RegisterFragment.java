package com.example.demo.fragment;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.activity.LoginActivity;
import com.example.demo.api.CookBookApi;
import com.example.demo.entity.BaseResponse;
import com.example.demo.entity.User;
import com.example.demo.manager.ActivityLifecycleManager;
import com.example.demo.utils.ConstantUtil;
import com.example.demo.utils.PreferenceUtils;
import com.example.demo.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment implements Validator.ValidationListener{

    @BindView(R.id.logo)
    ImageView mLogo;
    @Order(1)
    @NotEmpty(message = "用户名不能为空")
    @BindView(R.id.et_account_register)
    EditText mEtAccount;

    @Order(2)
    @NotEmpty(message = "密码不能为空")
    @Password(min = 1, scheme = Password.Scheme.ANY,message = "密码不能少于3位")
    @BindView(R.id.et_password_register)
    EditText mEtPassword;

    @BindView(R.id.iv_clean_account)
    ImageView mIvCleanAccount;
    @BindView(R.id.clean_password)
    ImageView mCleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView mIvShowPwd;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;

    private Validator validator;
    User regUser;
    QMUITipDialog tipDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QMUIStatusBarHelper.translucent((Activity) getContext());
        validator = new Validator(this);
        validator.setValidationListener(this);

    }


    @OnClick({R.id.iv_clean_account, R.id.clean_password, R.id.iv_show_pwd,R.id.login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clean_account:
                mEtAccount.getText().clear();
                mEtPassword.getText().clear();
                break;
            case R.id.clean_password:
                mEtPassword.getText().clear();
                break;
            case R.id.iv_show_pwd:
                changePasswordEye();
                break;
            case R.id.login:
                toLogin();
                break;
            case R.id.btn_register:

                validator.validate();

                validator.isValidating();

                break;
        }
    }

    private void toLogin() {
        final LoginActivity loginActivity= (LoginActivity) getActivity();
        loginActivity.setFragment2Fragment(new LoginActivity.Fragment2Fragment() {
            @Override
            public void gotoFragment(QMUIViewPager viewPager) {
                viewPager.setCurrentItem(0);
            }
        });
        loginActivity.forSkip();
    }

    private void changePasswordEye() {
        if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mIvShowPwd.setImageResource(R.drawable.icon_pass_visuable);
        } else {
            mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mIvShowPwd.setImageResource(R.drawable.icon_pass_gone);
        }


        String pwd = mEtPassword.getText().toString();
        if (!TextUtils.isEmpty(pwd))
            mEtPassword.setSelection(pwd.length());
    }


    private void attemptRegister() {

        final String userAccount = mEtAccount.getText().toString();
        final String password = mEtPassword.getText().toString();
        ceateAccount(userAccount,password);
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        tipDialog.show();
        this.requireView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (regUser!=null) {
                    ActivityLifecycleManager.get().finishAllActivity();
                    PreferenceUtils.setInt("userId", regUser.getUserId());
                    Intent intent = new Intent(getContext(), MainActivity.class);

                    intent.putExtra("user", regUser);
                    startActivity(intent);

                }else {
                    ToastUtil.show("注册失败");
                }
                tipDialog.dismiss();
                tipDialog = new QMUITipDialog.Builder(getContext())
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord("注册成功")
                        .create();
                tipDialog.show();
            }
        }, 2000);

    }

    @Override
    public void onValidationSucceeded() {
     attemptRegister();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ToastUtil.show("请重新输入");
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                ToastUtil.show(message);
            }
        }

    }





    private void ceateAccount(String userAccount, String password) {

        User user = new User();
        user.setNickName(userAccount);
        user.setPassword(password);
        user.setAvatar("https://cyf-file.oss-cn-shenzhen.aliyuncs.com/avatar/1617029247754.jpg?versionId=CAEQHBiBgMDYs.j5wxciIDI2ZTYxODIzYTJhNjRlM2ZiYzkzN2E4ZGE0MzQxOTIy");
        user.setDescription("这个人很懒，什么都没有留下");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.register(user);
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                Log.e("response_body",response.body().toString());
                if(baseResponse.getSuccess() ) {
                    regUser =  baseResponse.getData().getUserInfo();


                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("errMsg", String.valueOf(t.getCause()));

            }
        });
    }






}
