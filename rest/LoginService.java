package com.example.wong.testing.rest;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Wong on 5/1/17.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("login")
    Call<JsonObject> performLogin(@Field("email")String  email, @Field("password")String  password);
}
