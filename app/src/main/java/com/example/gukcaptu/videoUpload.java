package com.example.gukcaptu;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class videoUpload {
    static Context mContext;
    static String ip = "http://172.20.10.2:8080";
    static boolean okay=false;

    public boolean getResult(){
        return okay;
    }

    //동기 처리 함수//
    public static void send2Server(File file, String file_name) {
        try{
//            OkHttpClient client = new OkHttpClient();
            OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();
            RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", file.getName(), RequestBody.create(MultipartBody.FORM, file))
                .build();

            Request request = new Request.Builder()
                .url(ip+"/upload") // Server URL 은 본인 IP를 입력
                .post(requestBody)
                .build();

            // 동기 처리시 execute함수 사용
            Response response = client.newCall(request).execute();
            //출력
            Log.w("videoupload파일:","파일 업로드 request 이후");
            String message = response.body().string();
            System.out.println(message);
            Log.d("TEST:", message);
            okay=true;
        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }
    }


    //비동기 처리 함수//
//    public static void send2Server(File file, String file_name){
//
//        Log.w("videoupload 파일:","들어옴");
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("files", file.getName(), RequestBody.create(MultipartBody.FORM, file))
//                .build();
//        Request request = new Request.Builder()
//                .url(ip+"/upload") // Server URL 은 본인 IP를 입력
//                .post(requestBody)
//                .build();
//        Log.w("videoupload파일:","파일 업로드 request 이후");
////        OkHttpClient client = new OkHttpClient();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(1000, TimeUnit.SECONDS)
//                .readTimeout(300, TimeUnit.SECONDS)
//                .writeTimeout(300, TimeUnit.SECONDS)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("TEST : ", response.body().string());
//                okay=true;
//
//
//
////                videoReceive v = new videoReceive();
////                Log.d("current time:", file_name);
////                v.receiveFromServer(file_name);
////                markerNum=v.getResult();
////
//
//
//
//
//
////                marker3 mk = new marker3();
////                mk.markerNum = v.getResult();
//
////                Handler mHandler = new Handler(Looper.getMainLooper());
////                mHandler.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        // 내용
////                        mContext = context;
////
////                        Intent intent = new Intent(mContext, markerDetection_1.class);
////
////                        intent.putExtra("File", file_name);
////                        intent.putExtra("Marker", markerNum);
////                        String str = String.valueOf(markerNum);
////                        Log.i("markernum:", str);
////                        mContext.startActivity(intent);
////                    }
////                }, 0);
//
//            }
//
//        });
//    }
//
//}