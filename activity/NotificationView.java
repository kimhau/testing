package com.example.wong.testing.activity;

/**
 * Created by Wong on 11/1/17.
 */
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.example.wong.testing.R;

public class NotificationView extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }
}