package com.khedma.salahny.prsentation.Categories

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.khedma.salahny.data.Request
import com.khedma.salahny.data.SalahlyApiService
import com.khedma.salahny.data.userToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class CdetailsVM : ViewModel(){

    private var apiService: SalahlyApiService
    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://irregular-worker-connections-default-rtdb.firebaseio.com/")
            .build()
        apiService=retrofit.create(SalahlyApiService::class.java)
    }
    fun getWorkerFCMToken(workerPhone: String, onTokenReceived: (String?) -> Unit) {
        val database = FirebaseDatabase.getInstance().getReference("userToken")
        database.child(workerPhone).get().addOnSuccessListener {
            val token = "ejzoQTOUQmyHWC-p-EnsQK:APA91bHEgnx2YrGPG1kPhMvtujQwFoF8MVCRag-6zG0E0kwt7GmeJIp0d1O7mgb4Osg9ULicIelaf-cjnIbPBPrUqegKX5tuNgv8e5_sFBKrvTu0YDQNAQT9ZNDV0Qmm4vO3JTCYPmrK"
            Log.i("tooooken","$token")
            onTokenReceived(token)
        }.addOnFailureListener {
            onTokenReceived(null)
        }
    }
    fun sendRequestToWorker(workerPhone: String ,userPhone:String,userName:String ) {
        getWorkerFCMToken(workerPhone) { workerFCMToken ->
            workerFCMToken?.let {
                // Build the notification payload
                val notificationPayload = mapOf(
                    "title" to "New Service Request",
                    "body" to "You have a new service request From $userName.",
                    "phoneNumber" to userPhone
                )

                // Send the notification using Firebase Messaging
                FirebaseMessaging.getInstance().send(
                    RemoteMessage.Builder(it)
                        .setMessageId(UUID.randomUUID().toString())
                        .setData(notificationPayload)
                        .build()

                )
                Log.i("sendRequestToWorker","$workerFCMToken")
            } ?: run {
                Log.e("sendRequestToWorker", "FCM token not found for worker: $workerPhone")
            }
        }
    }

    fun OnRequest(workerPhone: String,userPhone:String,userName:String,imgRes:String,timeStamp:Long){
        sendRequestToWorker(workerPhone,userPhone,userName)
        val request=Request(userPhone,userName,workerPhone,imgRes,timeStamp)
        val call: Call<Void> = apiService.pushRequest(request)
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

}