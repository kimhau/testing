package com.example.wong.testing.rest;

/**
 * Created by Wong on 4/1/17.
 */
import com.example.wong.testing.model.Task;
import com.example.wong.testing.model.TaskResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TaskService {
    @GET("tasks")
    Call<TaskResponse> getAllTask(@Header("authorization") String apiKey);

}

