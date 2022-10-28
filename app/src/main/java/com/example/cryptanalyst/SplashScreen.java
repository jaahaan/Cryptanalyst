package com.example.cryptanalyst;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.annotation.Annotation;

//import com.github.ybq.android.spinkit.SpinKitView;


public class SplashScreen extends AppCompatActivity {

    private ImageView image;
    private TextView text;
    float v =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        image = findViewById(R.id.imageView);
        text = findViewById(R.id.name);

        image.setTranslationY(-1600);
        text.setTranslationY(1400);
        image.setAlpha(v);
        text.setAlpha(v);
        image.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(0).start();

        text.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(0).start();

        new Handler().postDelayed(() -> {
            image.animate().translationY(-1600).setDuration(1500).setStartDelay(100);
            text.animate().translationY(1400).setDuration(1500).setStartDelay(100);
        }, 1500);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }, 2200);
    }

}