package com.khedma.salahny.prsentation.WorkerHome

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkerHomeViewModel :ViewModel() {

    fun GetAvalibty(): Boolean{
        val email=""
        var availability:Boolean=true
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("worker")
        reference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Loop through the snapshots
                    for (data in snapshot.children) {
                        // Retrieve the "availability" attribute from the data snapshot
                         availability = data.child("availability").value as Boolean
                        // Use the retrieved availability
                        Log.i("Client Availability", "$availability")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors
                    println("Failed to read value: ${error.message}")
                }
            })

   return availability
    }
     fun changeAvalibality(){}
}