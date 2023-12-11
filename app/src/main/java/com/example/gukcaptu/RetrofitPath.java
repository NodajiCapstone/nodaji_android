package com.example.gukcaptu;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H'¨\u0006\u0007"},
        d2 = {"LRetrofitPath;", "", "profileSend", "Lretrofit2/Call;", "", "imageFile", "Lokhttp3/MultipartBody$Part;", "gukcaptu.app.main"}
)
public interface RetrofitPath {
    @Multipart
    @POST("서버주소")
    @NotNull
    Call profileSend(@Part @NotNull okhttp3.MultipartBody.Part var1);
}
