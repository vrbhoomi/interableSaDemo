package com.example.iterableapplication1;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iterable.iterableapi.IterableFirebaseMessagingService;

public class MyIterableFbMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("FCM Message", "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData().size()>0) {
            Log.i("FCM Message", "Message data payload: " + remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i("FCM Notification", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        IterableFirebaseMessagingService.handleMessageReceived(this, remoteMessage);

    }



    @Override
    public void onNewToken(String mToken) {
        super.onNewToken(mToken);
        Log.i("TOKEN", mToken);
        IterableFirebaseMessagingService.handleTokenRefresh();
    }


}


