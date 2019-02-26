package com.gameex.dw.hospital.appStart;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.gameex.dw.hospital.login.LoginActivity;

public class StartActivity extends AppCompatActivity {
    private Handler mHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start);
        //2s后前往主页
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoLogin();
            }
        },2000);
    }

    private void gotoLogin(){
        Intent intent=new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
//        overridePendingTransition(0,0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
