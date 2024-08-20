package com.khedma.salahny.data

import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {

    @POST("/send")

    suspend fun sendMessage(
        @Body body: SendMessageDTO
    )

    @POST("/broadcast")
    suspend fun broadcast(
        @Body body: SendMessageDTO
    )


}