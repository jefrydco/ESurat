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
    @FormUrlEncoded
    @POST("index.php?action=getSurat")
    Call<SuratList> getListSurat(@Field("id") String id);

    @FormUrlEncoded
    @POST("index.php?action=nextPage")
    Call<SuratList> getListSuratNextPage(@Field("id") String id, @Field("page") Long page);

    @FormUrlEncoded
    @POST("?action=suratDetail")
    Call<SuratList> setSuratStatusToProses(@Field("id") String id, @Field("idSurat") String idSurat);

    @FormUrlEncoded
    @POST("?action=selesai")
    Call<SuratList> setSuratStatusToSelesai(@Field("id") String id, @Field("idSurat") String idSurat);

    @FormUrlEncoded
    @POST("index.php?action=uploadBalasan")
    Call<SuratList> uploadSurat(@Field("id") String id, @Field("idSurat") String idSurat, @Field("file") MultipartBody.Part file);
}
