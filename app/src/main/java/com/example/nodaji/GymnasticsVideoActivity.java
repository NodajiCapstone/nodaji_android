package com.example.nodaji;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class GymnasticsVideoActivity extends AppCompatActivity {
    VideoView video;
    Button title;
    Button exitButton;
    String[] action = {"shaking_hands", "count_number", "finger_clap", "moving_fingers", "opp_rock_paper",
            "rock_clap", "rock_paper"};
    Integer cnt;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnastics_video);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
        try {
            cnt = intent.getExtras().getInt("video_cnt");
        } catch (Exception e) {
            cnt = 1;
        }
        System.out.println("cnt = " + cnt);

        video = findViewById(R.id.video);
        title = findViewById(R.id.title);
        exitButton = findViewById(R.id.exitButton);
        //Video Uri
        if(cnt == 1) {
            videoUri = Uri.parse("https://kr.object.ncloudstorage.com/hand.gymnastics.video/shaking_hands.mov");
        } else if (cnt == 2) {
            title.setText("2. 숫자 세기 운동");
            videoUri = Uri.parse("https://kr.object.ncloudstorage.com/hand.gymnastics.video/count_number.mov");
        } else if (cnt == 3) {
            title.setText("3. 손가락 박수 운동");
            videoUri = Uri.parse("https://kr.object.ncloudstorage.com/hand.gymnastics.video/finger_clap.mov");
        } else if (cnt == 4) {
            title.setText("4. 손가락 움직이기 운동");
            videoUri = Uri.parse("https://kr.object.ncloudstorage.com/hand.gymnastics.video/moving_fingers.mov");
        } else if (cnt == 5) {
            title.setText("5. 주먹 보자기 뒤집기 운동");
            videoUri = Uri.parse("https://kr.object.ncloudstorage.com/hand.gymnastics.video/opp_rock_paper.mov");
        } else if (cnt == 6) {
            title.setText("6. 주먹 박수 운동");
            videoUri = Uri.parse("https://kr.object.ncloudstorage.com/hand.gymnastics.video/rock_clap.mov");
        } else if (cnt == 7) {
            title.setText("7. 주먹 보자기 운동");
            videoUri = Uri.parse("https://kr.object.ncloudstorage.com/hand.gymnastics.video/rock_paper.mov");
        }

        //비디오뷰의 재생, 일시정지 등을 할 수 있는 '컨트롤바'를 붙여주는 작업
        video.setMediaController(new MediaController(this));

        //VideoView가 보여줄 동영상의 경로 주소(Uri) 설정하기
        video.setVideoURI(videoUri);

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //비디오 시작
                video.start();
            }
        });

        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(getApplicationContext(), GymnasticsTrainActivity.class);
                intent.putExtra("action", action[cnt-1]);
                intent.putExtra("video_cnt", cnt);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
