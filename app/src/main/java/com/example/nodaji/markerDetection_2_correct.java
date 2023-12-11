package com.example.nodaji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class markerDetection_2_correct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_detection2_correct);
        //set text로 1번에서의 정답을 셋팅하기



        //30초동안 화면 보여주고 3번으로 넘기기
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
            }
            public void onFinish() {
                //인식이 되었다면, 화면 1로 넘어가기
                Intent intent = new Intent(getApplicationContext(), marker3.class);
                intent.putExtra("tester","출제자에 대한 정보");
                startActivityForResult(intent, 1);
            }
        }.start();
    }
}