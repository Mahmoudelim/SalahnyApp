package com.khedma.salahny.WorkerRequests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khedma.salahny.data.Request
import java.util.concurrent.TimeUnit

class requestViewModel : ViewModel() {

    private val _requests = MutableLiveData<List<Request>>()
    val requests: LiveData<List<Request>> get() = _requests

    fun fetchRequests(workerPhone: String) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Requests")

        reference.orderByChild("workerPhone").equalTo(workerPhone)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val requestList = mutableListOf<Request>()
                    for (data in snapshot.children) {
                        val request = data.getValue(Request::class.java)
                        Log.i("req", "$request")  // Log each request
                        if (request != null) {
                            requestList.add(request)
                        }
                    }
                    _requests.value = requestList
                    Log.i("RequestViewModel", "Fetched requests: $requestList")  // Log the full list after updating LiveData
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors
                }
            })
    }
    fun timeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)

        return when {
            seconds < 60 -> "$seconds seconds ago"
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hours ago"
            else -> "$days days ago"
        }
    }
}