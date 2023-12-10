package com.example.nodaji;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FacialExpressionRecognition extends AppCompatActivity implements SurfaceHolder.Callback {
    private Button recordButton;
    private SurfaceView surfaceView;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private SurfaceHolder surfaceHolder;
    private boolean recording = false;
    private String TAG = "FacialExpressionRecognition.java";
    String file_name;
    File tempSelectFile;
    String currentTime;
    String action;
    int video_cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // surfaceView 초기화
        surfaceView = findViewById(R.id.surfaceView);

        TedPermission.with(this)
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용할 수 있습니다.")
                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO)
                .check();

        TextView emotionTextView = findViewById(R.id.emotionTextView);
        Button exitButton = findViewById(R.id.exitButton);

        recordButton = findViewById(R.id.recordButton);
        recordButton.setOnClickListener(v -> {
            if (recording) {
                mediaRecorder.stop();
                VideoUpload.send2Server(tempSelectFile);
                mediaRecorder.release();
                camera.lock();
                recording = false;
                recordButton.setText("녹화하기");
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(FacialExpressionRecognition.this, "녹화가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        mediaRecorder = new MediaRecorder();
                        camera = Camera.open(1);
                        camera.unlock();
                        mediaRecorder.setCamera(camera);
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
                        mediaRecorder.setOrientationHint(90);
                        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
                        currentTime = mFormat.format(new Date(System.currentTimeMillis()));
                        file_name = "/data/user/0/com.example.nodaji/cache/" + currentTime + ".mp4";
                        mediaRecorder.setOutputFile(file_name);
                        tempSelectFile = new File(file_name);
                        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        recording = true;
                        recordButton.setText("녹화 종료");
                    } catch (IOException e) {
                        Log.e(TAG, "Error in 79" + e.getMessage());
                        e.printStackTrace();
                        mediaRecorder.release();
                    }
                });
            }
        });

        exitButton.setOnClickListener(v -> {
            // 게임 종료
            finish();
        });

    }

    // PermissionListener 초기화
    PermissionListener permission = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(FacialExpressionRecognition.this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            Toast.makeText(FacialExpressionRecognition.this, "권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(FacialExpressionRecognition.this, "권한 거부", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
    }

    private void refreshCamera(Camera camera) {

    }

    private void setCamera(Camera cam) {
        camera = cam;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        refreshCamera(camera);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }
}
