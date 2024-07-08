package com.studycompanion.listapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class AboutFragment extends AppCompatActivity {


   @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.about_fragment);



       TextView question1 =findViewById(R.id.question1);
        TextView question2 =findViewById(R.id.question2);
        TextView question3 =findViewById(R.id.question3);
        TextView question4 =findViewById(R.id.question4);

        question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnswer(v);
            }
        });

        question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnswer(v);
            }
        });

        question3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnswer(v);
            }
        });

        question4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAnswer(v);
            }
        });


    }

    public void toggleAnswer(View view) {
        int answerId = 0;

        if (view.getId() == R.id.question1) {
            answerId = R.id.answer1;
        } else if (view.getId() == R.id.question2) {
            answerId = R.id.answer2;
        } else if (view.getId() == R.id.question3) {
            answerId = R.id.answer3;
        } else if (view.getId() == R.id.question4) {
            answerId = R.id.answer4;
        }

        if (answerId != 0) {
            TextView answerView = findViewById(answerId);

            if (answerView != null) {
                int visibility = answerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                answerView.setVisibility(visibility);
            }
        }
    }
}
