package com.example.wong.testing.model;

/**
 * Created by Wong on 4/1/17.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskResponse {

    @SerializedName("error")
    private boolean error;
    @SerializedName("tasks")
    private List<Task> results;


    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Task> getResults() {
        return results;
    }

    public void setResults(List<Task> results) {
        this.results = results;
    }
}
