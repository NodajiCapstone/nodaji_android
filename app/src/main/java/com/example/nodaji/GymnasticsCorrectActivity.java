package com.example.nodaji;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GymnasticsCorrectActivity extends AppCompatActivity {

    int video_cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnastics_correct);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
        video_cnt = intent.getExtras().getInt("video_cnt");

        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GymnasticsVideoActivity.class);
                intent.putExtra("video_cnt", video_cnt);
                startActivity(intent);
            }
        });
    }
}
