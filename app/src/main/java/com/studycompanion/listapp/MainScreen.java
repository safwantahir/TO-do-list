package com.studycompanion.listapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class MainScreen extends AppCompatActivity {
    ImageView cardActivity,faqActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_layout);

        cardActivity=findViewById(R.id.cards);
        faqActivity=findViewById(R.id.faq);

        cardActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainScreen.this,MainActivity.class);
                startActivity(intent);
            }
        });
        faqActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this,AboutFragment.class);
                startActivity(intent);
            }
        });

}

}
