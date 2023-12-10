package com.example.nodaji;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class Login extends Application {
    private static Login instance;
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "9da0c5155b77cc28baeeedc7ed396099");
    }
}

