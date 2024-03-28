package com.khedma.salahny.prsentation.Login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khedma.salahny.data.AuthResponse
import com.khedma.salahny.data.Client
import com.khedma.salahny.data.SalahlyApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class loginViewModel: ViewModel() {
    private var apiService: SalahlyApiService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://irregular-worker-connections-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(SalahlyApiService::class.java)
    }
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        apiService.loginWithEmailPassword(email, password).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    // Authentication successful
                    callback(true, "Login successfully")
                } else {
                    // Authentication failed
                    callback(false, "Invalid email or password")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                // Handle failure
                callback(false, "Login failed. Please try again.")
            }
        })
    }




}