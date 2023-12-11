package com.example.nodaji;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.kakao.sdk.user.UserApiClient;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 사용자 이름 설정 */
        Intent intent = getIntent();
        String strUserName = intent.getStringExtra("name");
        TextView name = findViewById(R.id.name);
        name.setText(strUserName);

        /* 손 체조 학습하기 */
        AppCompatButton hand_gymnastics = findViewById(R.id.hand_gymnastics);
        hand_gymnastics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GymnasticsVideoActivity.class);
                startActivity(intent);
            }
        });

        /* 감정 분석하기 */
        AppCompatButton facial_expression = findViewById(R.id.facial_expression);
        facial_expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FacialExpressionRecognition.class);
                startActivity(intent);
            }
        });

        /* 감정 분석하기 */
        AppCompatButton marker_detection = findViewById(R.id.marker_detection);
        marker_detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, marker3.class);
                startActivity(intent);
            }
        });

        AppCompatButton logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(error -> {
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                    }else{
                        Log.e(TAG, "로그아웃 성공, SDK에서 토큰 삭제됨");
                    }
                    return null;
                });
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
