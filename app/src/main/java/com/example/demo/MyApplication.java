package com.example.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.demo.manager.ActivityLifecycleManager;
import com.example.demo.utils.AppUtils;
import com.example.demo.utils.PreferenceUtils;
import com.xuexiang.xui.XUI;

public class MyApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        XUI.init(this);
        XUI.debug(true);

        context = getApplicationContext();


        ActivityLifecycleManager.get().init(this);

        PreferenceUtils.initPreference(this, AppUtils.getAppName(this), Activity.MODE_PRIVATE);

    }
}
