package com.example.nodaji;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView name = findViewById(R.id.name);
        AppCompatButton hand_gymnastics = findViewById(R.id.hand_gymnastics);
        AppCompatButton facial_expression = findViewById(R.id.facial_expression);

        Intent intent = getIntent();
        String strUserName = intent.getStringExtra("name");
        System.out.println("main " + strUserName);
        name.setText(strUserName);

        hand_gymnastics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GymnasticsVideoActivity.class);
                startActivity(intent);
            }
        });

        facial_expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FacialExpressionRecognition.class);
                startActivity(intent);
            }
        });

    }
}
