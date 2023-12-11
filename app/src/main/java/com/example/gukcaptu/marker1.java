package com.example.gukcaptu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SimpleCursorAdapter;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class marker1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker1);
    }
}



public class markerDetection_1 extends AppCompatActivity  {
    private List<String> columdata;
    ParticipantListOpenHelper Phelper;
    MarkerOptionsListOpenHelper Mhelper;
    SQLiteDatabase db1;
    AppCompatButton button1;
    AppCompatButton button2;
    AppCompatButton button3;
    AppCompatButton button4;
    VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_detection1);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        video = findViewById(R.id.videoView);
        //비디오에는 출제자의 화면이 보임.
        Intent receive_intent = getIntent();
        String File = receive_intent.getStringExtra("File");
        if(File!=null){
            Log.w("file: ","파일 넘어옴");
            video.setMediaController(new MediaController(this));
            video.setVideoPath(File);
            video.requestFocus();
            //video.setRotation(90);        //녹화된 동영상 90도 회전 시키기

            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    video.start();
                }
            });
        }

        //마커 번호가 넘어오면 할 일: 정답 setting
        String MarkerNum = receive_intent.getStringExtra("Marker");
        int mk = Integer.parseInt(MarkerNum);
        Log.i("marker num in marker detection 1: ", MarkerNum);



        //btn 1-4에는 정답을 포함한 무작위 선택지가 주어짐. : db의 것들을 무작위로 버튼에 넣음.
        Mhelper = new MarkerOptionsListOpenHelper(this);
        db1 = Mhelper.getReadableDatabase();
        Cursor c1 = db1.rawQuery("select _id, name from options", null);


        List<String> optionsList = new ArrayList<>();
        if (c1.moveToFirst()) {
            do {
                optionsList.add(c1.getString(1));
            } while (c1.moveToNext());
        }


        // 이전 화면에서 받은 정답 정보를 가지고 버튼 세팅
        // 지금은 모두 랜덤으로 했는데, 하나는 정답 버튼으로 수정.
        button1.setText(getRandomOption(optionsList));
        button2.setText(getRandomOption(optionsList));
        button3.setText(getRandomOption(optionsList));
        button4.setText(getRandomOption(optionsList));



        //정답은 정답 페이지로, 오답은 오답 페이지로 이동. 버튼을 누르면 인텐트 호출할 때 정답 text를 함께 넘겨주기
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), markerDetection_2_correct.class);
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), markerDetecion_2_wrong.class);
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), marker3.class);
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //리스트에는 참여하는 사람들의 모든 목록이 배열로 해서 들어감.
        Phelper = new ParticipantListOpenHelper(this);
        SQLiteDatabase db = Phelper.getReadableDatabase();
        Cursor c = db.rawQuery("select _id, name, sex from participant", null);
        SimpleCursorAdapter ca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c, new String[]{"sex", "name"}, new int[]{android.R.id.text1, android.R.id.text2},0);
        ListView list = findViewById(R.id.lv_participant);
        list.setAdapter(ca);


        AppCompatButton exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    private String getRandomOption(List<String> optionsList) {
        if (optionsList.isEmpty()) {
            // 리스트가 비어있으면 기본값이나 예외 처리를 해야 할 것 같아요.
            return "No Options";
        }
        int randomIndex = (int) (Math.random() * optionsList.size());
        String randomOption = optionsList.get(randomIndex);
        optionsList.remove(randomIndex); // 이제 선택된 옵션은 리스트에서 제거
        return randomOption;
    }

    //화면에 안보일때...
    @Override
    protected void onPause() {
        super.onPause();

        //비디오 일시 정지
        if(video!=null && video.isPlaying()) video.pause();
    }
    //액티비티가 메모리에서 사라질때..
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        if(video!=null) video.stopPlayback();
    }

}