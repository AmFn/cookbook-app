package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import com.example.demo.R;
import com.just.agentweb.core.AgentWeb;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateWebActivity extends AppCompatActivity {

    class AndroidInterface {

        private Handler deliver = new Handler(Looper.getMainLooper());
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }



        @JavascriptInterface
        public  void toUserDetail(){


        }


    }
    AgentWeb mAgentWeb;
    private Context context = this;

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_web);
        ButterKnife.bind(this);
   initTopBar();

            Intent intent =getIntent();
        View view = findViewById(R.id.webView);
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
//                .go("https://www.baidu.com");
                    .go("http://app2.chenfengkk.com/");

            mAgentWeb.getJsInterfaceHolder().addJavaObject("android",new AndroidInterface(mAgentWeb,this));








    }


    private void initTopBar() {
        QMUIStatusBarHelper.translucent(this);
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.cook_app_yellow));
        mTopBar.setTitle("创建菜谱").setTextColor(getResources().getColor(R.color.white));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}