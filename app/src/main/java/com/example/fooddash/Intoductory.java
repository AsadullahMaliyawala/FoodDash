package com.example.fooddash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Intoductory extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;

    ImageView foodimg,back2;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intoductory);

        foodimg = findViewById(R.id.introbackimage);
        lottieAnimationView = findViewById(R.id.lottie1);

//        foodimg.animate().translationX(3000).setDuration(1000).setStartDelay(6100);
        lottieAnimationView.animate().translationX(3000).setDuration(1000).setStartDelay(6000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Intoductory.this, com.example.fooddash.LoginRegister.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}