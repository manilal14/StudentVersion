package com.example.mani.studentversion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.mani.studentversion.NewsRelaled.NewsFeed;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Next two lines help in getting rid off status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        splashScreenLauncher splashScreenLauncher = new splashScreenLauncher();
        splashScreenLauncher.start();
    }

    private class splashScreenLauncher extends Thread {
        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(SplashScreen.this, NewsFeed.class));
            SplashScreen.this.finish();
        }

    }
}
