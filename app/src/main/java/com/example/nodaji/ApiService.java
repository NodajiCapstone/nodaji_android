package com.example.nodaji;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/endpoint")
    Call<String> getServerData();
}
