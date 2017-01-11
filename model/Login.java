package com.example.wong.testing.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wong on 5/1/17.
 */

public class Login {
    @SerializedName("error")
    private boolean error;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("apiKey")
    private String apiKey;
    @SerializedName("createdAt")
    private String createdAt;

    public Login(boolean error, String name, String email, String apikey, String createdAt) {
        this.error = error;
        this.name = name;
        this.email = email;
        this.apiKey = apikey;
        this.createdAt = createdAt;
    }

    public boolean getError() { return error; }
    public void setError(boolean error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
