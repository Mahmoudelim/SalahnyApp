package com.khedma.salahny.data

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("FCM Token","Refreshed token: $token")

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Respond to received message
    }
}