package com.example.nodaji;

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

public class GymnasticsTrainActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    private Button trainButton;
    private Button testButton;
    private SurfaceView surfaceView;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private SurfaceHolder surfaceHolder;
    private boolean recording = false;
    private String TAG = "MainActivity.java";
    String file_name;
    File tempSelectFile;
    String currentTime;
    private Socket socket;

    private DataOutputStream outstream;
    private DataInputStream instream;

    String ip = "192.168.35.149";
    private int port = 8081;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnastics_train);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        TedPermission.with(this) //권한을 얻기 위한 코드이다.
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용할 수 있습니다.")
                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO)
                .check();

        trainButton = findViewById(R.id.trainButton);
        trainButton.setOnClickListener(v -> {
            if (recording) { //녹화 중일 때 버튼을 누르면 녹화가 종료하도록 한다.
                mediaRecorder.stop();
                VideoUpload.send2Server(tempSelectFile);
                mediaRecorder.release();
                camera.lock();
                recording = false;
                trainButton.setText("체조하기");
            } else { //녹화 중이 아닐 때 버튼을 누르면 녹화가 시작하게 한다.
                runOnUiThread(new Runnable() { //녹화를 하는 것은 백그라운드로 하는 것이 좋다.
                    @Override
                    public void run() {
                        Toast.makeText(GymnasticsTrainActivity.this, "녹화가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                        try {
                            mediaRecorder = new MediaRecorder();
                            camera = Camera.open(1);
                            camera.setDisplayOrientation(90);
                            camera.unlock();
                            mediaRecorder.setCamera(camera);
                            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                            mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
                            mediaRecorder.setOrientationHint(90);
//                            mediaRecorder.setOutputFile("/storage/emulated/0/Download/test.mp4");
                            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
                            currentTime = mFormat.format(new Date(System.currentTimeMillis()));
                            file_name = "/data/user/0/com.example.nodaji/cache/" + currentTime + ".mp4";
                            mediaRecorder.setOutputFile(file_name);
                            tempSelectFile = new File(file_name);
                            mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                            recording = true;
                            trainButton.setText("체조 종료");
                        } catch (IOException e) {
                            Log.e(TAG, "Error in 79" + e.getMessage());
                            e.printStackTrace();
                            mediaRecorder.release();
                        }
                    }

                });
            }
        });

        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
            }
        });
    }

    void connect(){
        Log.w("connect","연결 하는중");

        Thread checkUpdate = new Thread(){
            public void run(){
                // Access server
                try{
                    socket = new Socket(ip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1){
                    Log.w("서버 접속 못함", "서버 접속 못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결 요청");

                try{
                    outstream = new DataOutputStream(socket.getOutputStream());
                    instream = new DataInputStream(socket.getInputStream());
                    outstream.writeUTF("안드로이드에서 서버로 연결 요청");
                }catch(IOException e){
                    e.printStackTrace();
                    Log.w("버퍼","버퍼 생성 잘못 됨");
                }
                Log.w("버퍼","버퍼 생성 잘 됨");

                try{
                    while(true){
//                        byte[] data = currentTime.getBytes();
//                        ByteBuffer b1 = ByteBuffer.allocate(4);
//                        b1.order(ByteOrder.LITTLE_ENDIAN);
//                        b1.putInt(data.length);
//                        outstream.write(b1.array(),0,4);
//                        outstream.write(data);

                        outstream.write(currentTime.getBytes());

                        byte[] data = new byte[15];
                        instream.read(data,0,15);
//                        ByteBuffer b2 = ByteBuffer.wrap(data);
//                        b2.order(ByteOrder.LITTLE_ENDIAN);
//                        int length = b2.getInt();
//                        data = new byte[length];
//                        instream.read(data,0,length);
                        }
                    } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        checkUpdate.start();
    }



    PermissionListener permission = new PermissionListener() {
        @Override
        public void onPermissionGranted() { //권한을 허용받았을 때 camera와 surfaceView에 대한 설정을 해준다.
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            surfaceView = (SurfaceView)findViewById(R.id.record);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(GymnasticsTrainActivity.this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            Toast.makeText(GymnasticsTrainActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(GymnasticsTrainActivity.this, "권한 거부", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
    }

    private void refreshCamera(Camera camera) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCamera(camera);
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
