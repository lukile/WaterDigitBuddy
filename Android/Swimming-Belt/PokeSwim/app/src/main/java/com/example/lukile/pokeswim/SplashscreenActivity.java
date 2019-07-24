package com.example.lukile.pokeswim;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lukile.pokeswim.profile.ProfileActivity;
import com.example.lukile.pokeswim.signin.SigninActivity;

public class SplashscreenActivity extends AppCompatActivity {

    private static int TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashscreenActivity.this, SigninActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }
}
