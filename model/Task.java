package com.example.wong.testing.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wong on 4/1/17.
 */

public class Task {
    @SerializedName("error")
    private boolean error;
    @SerializedName("id")
    private int taskId;
    @SerializedName("task")
    private String task;
    @SerializedName("status")
    private int status;
    @SerializedName("createdAt")
    private String createdAt;

    public Task(boolean error, int taskId, String task, int status, String createdAt) {
        this.error = error;
        this.taskId = taskId;
        this.task = task;
        this.createdAt = createdAt;
    }

    public boolean getError() {
        return error;
    }
    public void setError(boolean error) {
        this.error = error;
    }

    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
