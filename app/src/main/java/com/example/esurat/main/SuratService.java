package com.example.esurat.main;

import com.example.esurat.model.Status;
import com.example.esurat.model.SuratList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SuratService {
    @GET("listSurat")
    Call<SuratList> getListSurat();

    @FormUrlEncoded
    @POST("setStatus")
    Call<SuratList> setStatus(@Field("id") String id, @Field("status") String status);

    @Multipart
    @POST("uploadSurat")
    Call<Status> uploadSurat(@Part MultipartBody.Part body);
}
