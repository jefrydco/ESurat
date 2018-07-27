package com.example.esurat.home;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeService {
    @GET("api/listSurat")
    Call<String> getHomeList();
}
