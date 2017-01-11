package com.example.wong.testing.activity;

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

import com.example.wong.testing.R;
import com.example.wong.testing.rest.Client;
import com.example.wong.testing.rest.RegisterService;
import com.google.gson.JsonObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wong on 6/1/17.
 */

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button registerButton;
    private View mProgressView;
    private View mRegisterFormView;
    private TextView failedRegisterMessage;
    private TextInputEditText nameText, emailText, passwordText, repeatPasswordText;
    private String name, email, password, repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        nameText = (TextInputEditText) findViewById(R.id.name);
        emailText = (TextInputEditText) findViewById(R.id.email);
        passwordText = (TextInputEditText) findViewById(R.id.password);
        repeatPasswordText = (TextInputEditText) findViewById(R.id.repeatPassword);
        failedRegisterMessage = (TextView) findViewById(R.id.failed_register);

        registerButton = (Button) findViewById(R.id.registration_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                failedRegisterMessage.setText("");
                verifyRegister();
            }
        });

    }

    private boolean isValidEmaillId(String email){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void verifyRegister() {
        name = nameText.getText().toString();
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        repeatPassword = repeatPasswordText.getText().toString();

        if(name.trim().equals("")){
            failedRegisterMessage.setText(getString(R.string.error_name_empty));
            nameText.requestFocus();
        }
        else if(!isValidEmaillId(email.trim())){
            failedRegisterMessage.setText(getString(R.string.error_email_invalid));
            emailText.requestFocus();
        }
        else if(password.trim().equals("")){
            failedRegisterMessage.setText(getString(R.string.error_invalid_email));
            passwordText.requestFocus();
        }
        else if(!password.equals(repeatPassword)){
            failedRegisterMessage.setText(getString(R.string.error_password_duplicate));
        }
        else{
            showProgress(true);
            RegisterService registerService = null;
           registerService = Client.createAdapter(this) .create(RegisterService.class);

            Call<JsonObject> call = registerService.performRegister(email, password, name);

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
                        CharSequence text = res.get("message").toString();
                        int duration = Toast.LENGTH_SHORT;

                        showProgress(false);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        launchLoginScreen();
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

    private void launchLoginScreen() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

}

