package com.developer.flashlightadnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    private boolean a = true;
    private int b=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Validate();
    }

    private void Validate() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (a){
                    b++;
                    onetime();
                }
            }
        },1000);
    }

    private void onetime() {
        if (b==1){
            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        a=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        a=true;
        Validate();
    }
}