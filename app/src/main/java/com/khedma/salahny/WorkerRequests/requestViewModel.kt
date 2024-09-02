package com.khedma.salahny.WorkerRequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khedma.salahny.data.Request

class requestViewModel : ViewModel() {

    private val _requests = MutableLiveData<List<Request>>()
    val requests: LiveData<List<Request>> get() = _requests

    fun fetchRequests(workerPhone: String) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("requests")

        reference.orderByChild("workerPhone").equalTo(workerPhone)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val requestList = mutableListOf<Request>()
                    for (data in snapshot.children) {
                        val request = data.getValue(Request::class.java)
                        if (request != null) {
                            requestList.add(request)
                        }
                    }
                    _requests.value = requestList
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors
                }
            })
    }
}