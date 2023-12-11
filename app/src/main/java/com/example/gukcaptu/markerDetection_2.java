package com.example.gukcaptu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class markerDetection_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_detection2);
        AppCompatButton answerButton = findViewById(R.id.answerButton);
        ListView lv_correct = findViewById(R.id.lv_correct);
        ListView lv_wrong = findViewById(R.id.lv_wrong);
        //correct, wrong list view에는 배열을 만들어서 맞춘 사람의 목록을 넣고,


        //answer button은 set text를 통해 정답의 이름을 넣음.



        AppCompatButton exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}