package com.example.gukcaptu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;
import android.os.CountDownTimer;

import org.opencv.R;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.Collections;
import java.util.List;

//public class markerDetection_3 extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{
public class markerDetection_3 extends AppCompatActivity{
    private static final int CAMERA_PERMISSION_REQUEST_CODE=200;
    private Mat mRgba;
//    private CameraBridgeViewBase mOpenCvCameraView;
//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status){
//            switch (status){
//                case LoaderCallbackInterface.SUCCESS:{
//                    Log.d("Tag_Log: ", "openCV loaded");
//                    mOpenCvCameraView.enableView();
//                }
//                break;
//                default:{
//                    super.onManagerConnected(status);
//                }
//                break;
//            }
//        }
//    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_detection3);

        TextView tv = findViewById(R.id.textView);
        VideoView vv = findViewById(R.id.videoView);
        //text view에는 set text를 통해 시간초를 넣고, 시간초 내에 마커가 인식되면 화면1로 전환
        tv.setText("15");
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                tv.setText(Integer.toString(num + 1));
            }

            public void onFinish() {
                tv.setText("끝");
                //인식이 되었다면, 화면 1로 넘어가기
                Intent intent = new Intent(getApplicationContext(), markerDetection_1.class);
                intent.putExtra("tester","출제자에 대한 정보");
                startActivityForResult(intent, 1);

                //마커가 인식이 되지 않았다면, 다시 카운트다운 실행
            }
        }.start();


        //비디오에는 출제자의 화면이 보임.
//        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.videoView);
//        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
//        mOpenCvCameraView.setCvCameraViewListener(this);
//        mOpenCvCameraView.setCameraIndex(0);



        AppCompatButton exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivityForResult(intent, 1);           }
        });
}
//@Override
//    public void onCameraViewStarted(int width, int height) {
//    }
//
//    @Override
//    public void onCameraViewStopped() {
//    }
//
//    @Override
//    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//        mRgba = inputFrame.rgba();
//
//
//        //카메라로 가져온 이미지를 작업하는 부분.
//        if(mRgba==null)
//            Log.d("opencv: ", "onCameraFrame error");
//
//        Mat edges = new Mat();
//        Imgproc.Canny(mRgba, edges, 80, 200);
//        return edges;
//
//
//}

//
//
//    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
//        return Collections.singletonList(mOpenCvCameraView);
//    }

//    @Override
//    @TargetApi(Build.VERSION_CODES.M)
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.length > 0
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            onCameraPermissionGranted();
//        }else{
//
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    protected void onCameraPermissionGranted() {
//        List<? extends CameraBridgeViewBase> cameraViews = getCameraViewList();
//        if (cameraViews == null) {
//            return;
//        }
//        for (CameraBridgeViewBase cameraBridgeViewBase: cameraViews) {
//            if (cameraBridgeViewBase != null) {
//                cameraBridgeViewBase.setCameraPermissionGranted();
//            }
//        }
//    }

}