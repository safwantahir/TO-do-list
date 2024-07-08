package com.studycompanion.listapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_DURATION = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {

                        Intent mainIntent = new Intent(Splash.this, StartActivity.class);
                        Splash.this.startActivity(mainIntent);
                        Splash.this.finish();
                    }
                }, SPLASH_DISPLAY_DURATION);


                //ad code start

            }


        }
