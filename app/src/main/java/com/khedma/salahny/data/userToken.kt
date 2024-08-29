package com.khedma.salahny.data

import com.google.gson.annotations.SerializedName

data class userToken(
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("token")
    val token: String = "",
)
