package com.example.esurat.home;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeService {
    @GET("input-api-list-disini")
    Call<String> getHomeList();
}
