package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import com.example.demo.R;
import com.just.agentweb.core.AgentWeb;

public class MyFoodActivity extends AppCompatActivity {

    AgentWeb mAgentWeb;
    public int id = 14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food);
        View view = findViewById(R.id.webView);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
//                .go("https://www.baidu.com");
                .go("http://app.chenfengkk.com/");

        mAgentWeb.getJsInterfaceHolder().addJavaObject("android",new AndroidInterface(mAgentWeb,this));
    }



   class AndroidInterface {

        private Handler deliver = new Handler(Looper.getMainLooper());
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }




        @JavascriptInterface
        public int getID(){

            Log.d("uid:",""+id);
            return id;
        }
    }
}