package com.example.gukcaptu;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class videoReceive {
    String num = "0";
    static String ip = "http://172.20.10.2:8080";

    public int getResult(){
        int result = Integer.parseInt(num);
        return result;
    }

    //서버에서 작업한 마커 번호를 받아오는 함수
    public void receiveFromServer(String file){

        Request request = new Request.Builder()
                .url(ip+"/marker?file="+file) // Server URL 은 본인 IP를 입력
//                .url(ip+"/marker")
                .get().build();
//        OkHttpClient client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .readTimeout(20000, TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                num = response.body().string();
                Log.d("TEST : ", num);
            }
        });

    }

}
