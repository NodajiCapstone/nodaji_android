package com.example.nodaji;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoGradeFer {

    public interface Callback {
        void onResponse(String emotion);
        void onError(Exception e);
    }

    public void sendFromServer(String file, Callback callback) {
        Request request = new Request.Builder()
                .url("http://10.50.102.5:5000/grade?file=" + file) // Server URL 은 본인 IP를 입력
                .get().build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.d("EMOTION : ", result);

                    // JSON 파싱
                    JSONObject jsonResult = new JSONObject(result);

                    // "results" 키의 존재 여부 확인
                    if (jsonResult.has("results")) {
                        JSONArray resultsArray = jsonResult.getJSONArray("results");

                        // 결과 배열이 비어 있지 않은지 확인
                        if (resultsArray.length() > 0) {
                            // 여기에서는 첫 번째 결과만 가져오지만 필요에 따라 반복문 등을 사용하여 다수의 결과를 처리할 수 있습니다.
                            JSONObject firstResult = resultsArray.getJSONObject(0);
                            String emotion = firstResult.getString("emotion");

                            // 가져온 감정 값 사용
                            Log.d("EMOTION : ", emotion);

                            // 결과를 콜백으로 전달
                            callback.onResponse(emotion);
                        } else {
                            Log.d("EMOTION : ", "No results in the array");
                            callback.onError(new Exception("No results in the array"));
                        }
                    } else {
                        Log.d("EMOTION : ", "No 'results' key in the JSON");
                        callback.onError(new Exception("No 'results' key in the JSON"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
            }
        });
    }
}
