package com.greymatter.sprint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.greymatter.sprint.R;
import com.greymatter.sprint.MainActivity;
import com.greymatter.sprint.utils.Constant;
import com.greymatter.sprint.utils.MyFunction;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (MyFunction.getSharedPrefs(getApplicationContext(),Constant.isLoggedIn,false)){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), SigninActivity.class));
                }
                finish();
            }
        }, Constant.SPLASH_DURATION);
    }
}