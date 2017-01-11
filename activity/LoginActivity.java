package com.example.wong.testing.activity;

/**
 * Created by Wong on 5/1/17.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import com.example.wong.testing.R;
import com.example.wong.testing.rest.Client;
import com.example.wong.testing.rest.LoginService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button loginButton, registrationButton;
    private String email, password;
    private View mProgressView;
    private View mLoginFormView;
    private TextView failedLoginMessage;
    private TextInputEditText emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        emailText = (TextInputEditText) findViewById(R.id.email);
        passwordText = (TextInputEditText) findViewById(R.id.password);
        failedLoginMessage = (TextView) findViewById(R.id.failed_login);

        loginButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failedLoginMessage.setText("");
                verifyLogin();
            }
        });

        registrationButton = (Button) findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterScreen();
            }
        });
    }

    private void verifyLogin() {

        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        if(!isValidEmaillId(email.trim())){
            failedLoginMessage.setText("Invalid Email Address");
            emailText.requestFocus();
        }
        else if(password.trim().equals("")){
            failedLoginMessage.setText("Password is required");
            passwordText.requestFocus();
        }else{
            showProgress(true);
            LoginService loginService = null;
            loginService = Client.createAdapter(this).create(LoginService.class);

            Call<JsonObject> call = loginService.performLogin(email, password);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    JsonObject res = response.body();
                    if (res.get("error").toString().equals("true")) {
                        Context context = getApplicationContext();
                        CharSequence text = res.get("message").toString();
                        int duration = Toast.LENGTH_SHORT;

                        showProgress(false);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "You have succesfully Login.";
                        int duration = Toast.LENGTH_SHORT;

                        showProgress(false);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        launchHomeScreen();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    Context context = getApplicationContext();
                    CharSequence text = "Please check your network connection and internet permission.";
                    int duration = Toast.LENGTH_SHORT;

                    showProgress(false);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isValidEmaillId(String email){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private void launchRegisterScreen() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    private void launchHomeScreen() {
        PrefManager prefManager = new PrefManager(LoginActivity.this);
        prefManager.setFirstTimeLaunch(true);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void launchNotificationView() {
        startActivity(new Intent(LoginActivity.this, NotificationView.class));
        finish();
    }

}
