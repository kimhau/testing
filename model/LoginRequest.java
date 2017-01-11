package com.example.wong.testing.model;

/**
 * Created by Wong on 5/1/17.
 */

public class LoginRequest {
    final String email;
    final String password;

    public LoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
