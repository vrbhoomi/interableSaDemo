package com.example.iterableapplication1;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.iterable.iterableapi.IterableApi;
import com.iterable.iterableapi.IterableConfig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;


import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private void sendUserInfoToIterable() {
        JSONObject userData = new JSONObject();
        try {
            userData.put("firstname", "varun");
            userData.put("isRegisteredUser", true);
            userData.put("SA_User_Test_Key", "completed");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        IterableApi.getInstance().updateUser(userData);
    }

    private void sendCustomEventToIterable() {
        JSONObject eventData = new JSONObject();
        try {
            eventData.put("platform", "Android");
            eventData.put("isTestEvent", true);
            eventData.put("url", "https://iterable.com/sa-test/varun");
            eventData.put("secret_code_key", "Code_123");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        IterableApi.getInstance().track("mobileSATestEvent", eventData);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IterableConfig config = new IterableConfig.Builder()
                .setPushIntegrationName("myPushIntegration")
                .build();
        IterableApi.initialize(this, BuildConfig.ITERABLE_KEY, config);

        IterableApi.getInstance().setEmail("varunbhoomi@gmail.com");
        IterableApi.getInstance().registerForPush();

        Button buttonIUser = findViewById(R.id.button_i_update_user);
        buttonIUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserInfoToIterable();
            }
        });

        Button buttonIEvent = findViewById(R.id.button_i_custom_event);
        buttonIEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCustomEventToIterable();
            }
        });


        //Token to test FCM message.
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {

                        if (!task.isSuccessful()) {
                            Log.i("Startup : ", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.i("FCM Startup Token: ", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}