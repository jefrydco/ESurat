package com.example.esurat.auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface LoginService {

    @POST("api/login")
    Call<String> login(@Field("username") String username, @Field("password") String password);
}
