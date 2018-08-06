package com.example.esurat.auth;

import com.example.esurat.model.Login;
import com.example.esurat.model.Status;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("index.php?action=login")
    Call<Login> login(@Field("username") String username, @Field("password") String password);
}
