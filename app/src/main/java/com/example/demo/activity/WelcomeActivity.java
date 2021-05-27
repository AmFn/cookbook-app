package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.manager.ActivityLifecycleManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    private Runnable  runnable= new Runnable() {
        @Override
        public void run() {
            skipToMainActivity();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        slowWelcome();

    }

    private void slowWelcome() {
        handler.postDelayed(runnable, 5000);
    }
    private void skipToMainActivity() {

        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        ActivityLifecycleManager.get().finishAllActivity();
    }


    @OnClick(R.id.btn_enter)
    public void onViewClicked() {
        handler.removeCallbacks(runnable);
        skipToMainActivity();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {

        if (handler != null) {

            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

}