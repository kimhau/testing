package com.example.wong.testing.rest;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Wong on 6/1/17.
 */

public interface RegisterService {
    @FormUrlEncoded
    @POST("register")
    Call<JsonObject> performRegister(@Field("email")String  email, @Field("password")String  password, @Field("name")String  name);
}
