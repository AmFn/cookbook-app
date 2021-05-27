package com.example.demo.utils;
import android.content.Context;

import java.lang.reflect.Method;

public final class ContextUtil {

    private static Context CONTEXT_INSTANCE;


    public static Context getContext() {
        if (CONTEXT_INSTANCE == null) {
            synchronized (ContextUtil.class) {
                if (CONTEXT_INSTANCE == null) {
                    try {
                        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");

                        Method method = ActivityThread.getMethod("currentActivityThread");
                        Object currentActivityThread = method.invoke(ActivityThread);//获取currentActivityThread 对象

                        Method method2 = currentActivityThread.getClass().getMethod("getApplication");
                        CONTEXT_INSTANCE =(Context)method2.invoke(currentActivityThread);//获取 Context对象

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CONTEXT_INSTANCE;
    }
}
