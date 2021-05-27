package com.example.demo.api;

import com.example.demo.entity.BaseResponse;
import com.example.demo.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CookBookApi {

    @POST("user/register")
    Call<BaseResponse> register(@Body User user);


    @POST("user/login")
    Call<BaseResponse> login(@Body User user);
    @POST("user/update")
    Call<BaseResponse> update(@Body User user);

    @GET("cook-book/getHotFood")
    Call<BaseResponse> listHotFood();
    @GET("cook-book/getHotFood/{num}")
    Call<BaseResponse> listHotFood(@Path("num") int num);
    @GET("cook-book/getNewFood")
    Call<BaseResponse> listNewFood();
    @GET("cook-book/getNewFood/{num}")
    Call<BaseResponse> listNewFood(@Path("num") int num);

    @GET("cook-book/getUserFollowCookBook/{uid}")
    Call<BaseResponse> listUserFollowCookBook(@Path("uid") int uid);

    @GET("cook-book/getById/{id}")
    Call<BaseResponse> getFoodDetail(@Path("id") int id);

    @GET("cook-book/getFood/{classId}")
    Call<BaseResponse> getClassFood(@Path("classId") int classid);

    @GET("user/getUserInfo/{uid}")
    Call<BaseResponse> getUserById(@Path("uid") int uid);

    @GET("user/getUserInfo/{uid}")
    Call<BaseResponse> getUserCreate(@Path("uid") int uid);
    @GET("user/getUserAllInfo/{uid}")
    Call<BaseResponse> getUserDetailById(@Path("uid") int uid);

    @GET("cook-book/search/{key}")
    Call<BaseResponse> search(@Path("key") String key);


}
