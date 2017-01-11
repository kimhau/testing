package com.example.wong.testing.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.example.wong.testing.R;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static Button btnNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotification = (Button) findViewById(R.id.notificationButton);
        btnNotification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });

    }

    private void addNotification() {
        NotificationCompat.Builder mbuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_food)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        /* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(this, WelcomeActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                                            this,
                                            0,
                                            resultIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);
        mbuilder.setContentIntent(resultPendingIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mbuilder.build());
    }
}
