package com.example.gukcaptu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.opencv.core.Mat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

//public class markerDetection_3 extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{
public class marker3 extends AppCompatActivity{
    private boolean recording = false;
    private String TAG = "MainActivity.java";
    String file_name;
    File tempSelectFile;
    String currentTime;
    private Socket socket;

    private DataOutputStream outstream;
    private DataInputStream instream;
    String ip = "125.128.35.230";
    private int port = 8081;
    private SurfaceView surfaceView;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker3);
        TextView tv = findViewById(R.id.textView1);

        //권한 얻기 위한 코드
        TedPermission.create() //권한을 얻기 위한 코드이다.
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용할 수 있습니다.")
                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO)
                .check();
        if (recording) { //녹화 중일 때 버튼을 누르면 녹화가 종료하도록 한다.
            mediaRecorder.stop();
            videoUpload.send2Server(tempSelectFile);
            mediaRecorder.release();
            camera.lock();
            recording = false;
        } else { //녹화 중이 아닐 때 버튼을 누르면 녹화가 시작하게 한다.
            runOnUiThread(new Runnable() { //녹화를 하는 것은 백그라운드로 하는 것이 좋다.
                @Override
                public void run() {
                    Toast.makeText(marker3.this, "녹화가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                   if(camera!=null){ try {
                        mediaRecorder = new MediaRecorder();
                        camera = Camera.open(findFrontSideCamera());
                        camera.setDisplayOrientation(90);
                        camera.unlock();
                        mediaRecorder.setCamera(camera);

                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
                        mediaRecorder.setOrientationHint(90);
                        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
                        currentTime = mFormat.format(new Date(System.currentTimeMillis()));
                        mediaRecorder.setOutputFile("/storage/emulated/0/Download/test/"+currentTime+".mp4");
                        Log.w("connect","파일 저장됨");
//                        file_name = "/data/user/0/com.example.gukcaptu/cache/" + currentTime + ".mp4";
//                        mediaRecorder.setOutputFile(file_name);
                        tempSelectFile = new File(file_name);
                        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        recording = true;
                        Log.d("종료: ","카메라 녹화 종료");
                    } catch (IOException e) {
                        Log.e(TAG, "Error in 79" + e.getMessage());
                        e.printStackTrace();
                        mediaRecorder.release();
                    }
                }
                }

            });
        }

        //text view에는 set text를 통해 시간초를 넣고,
        tv.setText("15");
        //나중에 60000으로 수정해야댐 여기!
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                tv.setText(Integer.toString(num + 1));
            }
            public void onFinish() {
                tv.setText("끝");
                connect();               //연결한 후
                //시간초 내에 마커가 인식되면 화면1로 전환
                if(true){                    // 넘어온 값이 유효하다면,
                Intent intent = new Intent(getApplicationContext(), markerDetection_1.class);
                intent.putExtra("tester","출제자에 대한 정보"); //마커 이름, 파일 경로 넘기기
                startActivityForResult(intent, 1);

                //마커가 인식이 되지 않았다면, 다시 카운트다운 실행
            }
            }
        }.start();

        AppCompatButton exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivityForResult(intent, 1);           }
        });
    }




    PermissionListener permission = new PermissionListener() {
        @Override
        public void onPermissionGranted() { //권한을 허용받았을 때 camera와 surfaceView에 대한 설정을 해준다.
            camera = Camera.open(findFrontSideCamera());
            camera.setDisplayOrientation(90);
            surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
            surfaceHolder = surfaceView.getHolder();
            Log.d("permission: ","surface get holder 후 ");
            surfaceHolder.addCallback(surfaceListener);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            Toast.makeText(marker3.this, "권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(marker3.this, "권한 거부", Toast.LENGTH_SHORT).show();
        }
    };


    private SurfaceHolder.Callback surfaceListener = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            camera.release();

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            camera = Camera.open(findFrontSideCamera());
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // TODO Auto-generated method stub
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(width, height);
            camera.startPreview();

        }
    };

    private int findFrontSideCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo cmInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cmInfo);

            if (cmInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }

        return cameraId;
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


    void connect(){
        Log.w("connect","연결 하는중");

        Thread checkUpdate = new Thread(){
            public void run(){
                // Access server
                try{
                    Log.w("connect","소켓 연결 하는중");

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


}
