package com.khedma.salahny.prsentation.WorkerHome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkerHomeViewModel :ViewModel() {

    private val _availability = MutableLiveData<Boolean>()
    val availability: LiveData<Boolean> = _availability
    fun getAvailability(email: String) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("worker")
        reference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val isAvailable = data.child("isAvailable").getValue(Boolean::class.java) ?: true
                        _availability.value = isAvailable
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("WorkerHomeViewModel", "Failed to read availability: ${error.message}")
                }
            })
    }
    fun changeAvailability(email: String, newStatus: Boolean) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("worker")
        reference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        data.ref.child("isAvailable").setValue(newStatus)
                        _availability.value = newStatus
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("WorkerHomeViewModel", "Failed to update availability: ${error.message}")
                }
            })
    }
}