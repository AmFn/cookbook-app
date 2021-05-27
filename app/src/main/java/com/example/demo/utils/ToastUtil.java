package com.example.demo.utils;

import android.widget.Toast;

public class ToastUtil {
    private static Toast makeText;
    public static void showDev(){
        String text = "该功能开发中";
        if(null==makeText){
            makeText = Toast.makeText(ContextUtil.getContext(), text, Toast.LENGTH_SHORT);
        }else{
            makeText.setText(text);
        }
        makeText.show();
    }
    public static void show(String text){

        if(null==makeText){
            makeText = Toast.makeText(ContextUtil.getContext(), text, Toast.LENGTH_SHORT);
        }else{
            makeText.setText(text);
        }
        makeText.show();
    }

}
