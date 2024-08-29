package com.khedma.salahny.data

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PushNotificationService : FirebaseMessagingService() {
    private var apiService:SalahlyApiService

    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://irregular-worker-connections-default-rtdb.firebaseio.com/")
            .build()
        apiService=retrofit.create(SalahlyApiService::class.java)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveFCMTokenToFirebase(token)
        Log.i("FCM Token","Refreshed token: $token")

    }

    private fun saveFCMTokenToFirebase(token: String) {
        val workerPhone = SharedPreferencesManager.phone
        val userToken=userToken(workerPhone.toString(),token)
        val call: Call<Void> = apiService.pushToken(userToken)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // Handle successful registration
                if (response.isSuccessful) {

                    Log.i("respo", "ok")
                } else {
                    Log.i("respo", "Error: ${response.code()}")
                    response.errorBody()
                    // You can extract error information from response.errorBody()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("respo", t.message.toString())


            }

        })

    }



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("MyFirebaseMessagingService", "Message received from: ${remoteMessage.from}")
        remoteMessage.notification?.let {
            Log.d("MyFirebaseMessagingService", "Message Notification Body: ${it.body}")
        }
        // Respond to received message
    }
}