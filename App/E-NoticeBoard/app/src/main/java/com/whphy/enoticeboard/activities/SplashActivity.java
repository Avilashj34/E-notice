package com.whphy.enoticeboard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.whphy.enoticeboard.R;
import com.whphy.enoticeboard.utility.Utils;

/**
 * Created by Jaykishan on 26/7/2017.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Utils.getUserId(SplashActivity.this).equals("null")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);


    }

}
