package com.example.nodaji;

import kotlin.Metadata;
import okhttp3.OkHttpClient.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.CallAdapter.Factory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u00112\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u0002H\u0011\u0018\u00010\u0013¢\u0006\u0002\u0010\u0014R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"},
        d2 = {"LRetrofitSetting;", "", "()V", "API_BASE_URL", "", "getAPI_BASE_URL", "()Ljava/lang/String;", "baseBuilder", "Lretrofit2/Retrofit$Builder;", "kotlin.jvm.PlatformType", "getBaseBuilder", "()Lretrofit2/Retrofit$Builder;", "httpClient", "Lokhttp3/OkHttpClient$Builder;", "getHttpClient", "()Lokhttp3/OkHttpClient$Builder;", "createBaseService", "S", "serviceClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "gukcaptu.app.main"}
)
public class retrofitSetting {
    @NotNull
    private static final String API_BASE_URL;
    @NotNull
    private static final Builder httpClient;
    private static final retrofit2.Retrofit.Builder baseBuilder;
    @NotNull
    public static final retrofitSetting INSTANCE;

    @NotNull
    public final String getAPI_BASE_URL() {
        return API_BASE_URL;
    }

    @NotNull
    public final Builder getHttpClient() {
        return httpClient;
    }

    public final retrofit2.Retrofit.Builder getBaseBuilder() {
        return baseBuilder;
    }

    public final Object createBaseService(@Nullable Class serviceClass) {
        Retrofit retrofit = baseBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private retrofitSetting() {
    }

    static {
        retrofitSetting var0 = new retrofitSetting();
        INSTANCE = var0;
        API_BASE_URL = "http://192.168.0.253:8080/";
        httpClient = new Builder();
        baseBuilder = (new retrofit2.Retrofit.Builder()).baseUrl(API_BASE_URL).addCallAdapterFactory((Factory)RxJavaCallAdapterFactory.create()).addConverterFactory((retrofit2.Converter.Factory)GsonConverterFactory.create());
    }
}
