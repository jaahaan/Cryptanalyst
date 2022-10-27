package com.example.cryptanalyst;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

//import com.github.ybq.android.spinkit.SpinKitView;


public class SplashScreen extends AppCompatActivity {

    //private SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //spinKitView = findViewById(R.id.progressBar);

        //firestore = FirebaseFirestore.getInstance();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
            }
        });
        thread.start();
    }


    public void doWork() {
        for (int progress = 50; progress <= 100; progress += 50) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startApp() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
    }

}