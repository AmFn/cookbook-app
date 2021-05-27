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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.xuexiang.xui.widget.popupwindow.status.Status;
import com.xuexiang.xui.widget.popupwindow.status.StatusView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment implements Validator.ValidationListener{


    @BindView(R.id.logo)
    ImageView mLogo;
    @Order(1)
    @NotEmpty(message = "用户名不能为空")
    @BindView(R.id.et_account)
    EditText mEtAccount;

    @Order(2)
    @NotEmpty(message = "密码不能为空")
    @Password(min = 1, scheme = Password.Scheme.ANY,message = "密码不能少于3位")
    @BindView(R.id.et_password)
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

    private  User loginUser;
    QMUITipDialog tipDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login_page, null);
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


    @OnClick({R.id.iv_clean_account, R.id.clean_password, R.id.iv_show_pwd,R.id.register, R.id.btn_login,R.id.tv_forget})
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

            case R.id.tv_forget:
                ToastUtil.show("密码忘了我也没办法哦~");
                break;
            case R.id.register:
                toRegister();
                break;
            case R.id.btn_login:

                validator.validate();

                validator.isValidating();

                break;
        }
    }



    private void toRegister() {
//        ToastUtil.show("跳转注册");
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.login_container,new RegisterFragment(),null)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .commit();

        final LoginActivity loginActivity= (LoginActivity) getActivity();
        loginActivity.setFragment2Fragment(new LoginActivity.Fragment2Fragment() {
            @Override
            public void gotoFragment(QMUIViewPager viewPager) {
                viewPager.setCurrentItem(1);
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


    private void attemptLogin() {

        final String userAccount = mEtAccount.getText().toString();
        final String password = mEtPassword.getText().toString();
        login(userAccount,password);
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        tipDialog.show();
        this.requireView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginUser!=null) {
                    ActivityLifecycleManager.get().finishAllActivity();
                    PreferenceUtils.setInt("userId", loginUser.getUserId());
                    Intent intent = new Intent(getContext(), MainActivity.class);

                    intent.putExtra("user", loginUser);
                    startActivity(intent);
                    tipDialog.dismiss();
                    tipDialog = new QMUITipDialog.Builder(getContext())
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                            .setTipWord("登录成功")
                            .create();
                    tipDialog.show();

                }else {
                    ToastUtil.show("用户名或密码错误");
                    tipDialog.dismiss();
                }

            }
        }, 2000);



    }

    private void login(String userAccount, String password) {
        User user = new User();
        user.setNickName(userAccount);
        user.setPassword(password);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CookBookApi api = retrofit.create(CookBookApi.class);
        Call<BaseResponse> repos = api.login(user);
        repos.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                Log.e("baseResponse  ",response.body().getData().toString());
                if(baseResponse.getSuccess() ) {
                    loginUser =  baseResponse.getData().getUserInfo();
                    Log.e("loginUser ",loginUser.toString());


                }else {
                    loginUser = null;
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("errMsg", String.valueOf(t.getCause()));

            }
        });
    }



    @Override
    public void onValidationSucceeded() {
        attemptLogin();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ToastUtil.show("请检查用户名或密码");
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



}
