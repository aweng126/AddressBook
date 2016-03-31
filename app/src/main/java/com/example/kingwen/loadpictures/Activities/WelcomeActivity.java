package com.example.kingwen.loadpictures.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.kingwen.loadpictures.Config.MyApplication;
import com.example.kingwen.loadpictures.R;

/**
 * 欢迎界面，同时完成网络数据的获取
 * Created by kingwen on 2016/3/26.
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * 全局变量对象
     */
    private MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);

        /*
        * 全局变量对象
        * */
        myApplication= (MyApplication) getApplication();


        /**
         * 延时启动的数据
         */
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Log.e("welcome", "实现延迟启动并启动跳转");
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);

    }

}
