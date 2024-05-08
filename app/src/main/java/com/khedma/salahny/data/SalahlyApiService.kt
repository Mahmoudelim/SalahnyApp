package com.khedma.salahny.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SalahlyApiService {
    @POST("client.json")
    fun registerClient(@Body client:Client): Call<Void>

    @POST("signInWithPassword")
    fun loginWithEmailPassword(@Query("email") email: String, @Query("password") password: String): Call<AuthResponse>

    @POST("worker.json") // Replace with your actual endpoint
    fun registerWorker(@Body worker: Worker): Call<Void>


    @GET("client.gson")
    suspend fun getNearestClient(state: String,neighborhood:String):Call<List<Client>>
    @GET("client/{email}.json")
    suspend fun getUserData(@Query("email") email: String): Client

    @GET("worker.json")
    suspend fun getAllWorkers(): Map<String, Worker>
    @GET("client.json")
    suspend fun getClientByEmail(@Query("email") email: String): Call<Client>


}