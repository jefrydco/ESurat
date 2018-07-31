package com.example.esurat.main;

import com.example.esurat.model.SuratList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SuratService {
    @GET("listSurat")
    Call<SuratList> getListSurat();

    @POST("setStatus")
    Call<String> setStatus(@Field("status") String status);

    @Multipart
    @POST("uploadSurat")
    Call<String> uploadSurat(@Part MultipartBody.Part file);
}
