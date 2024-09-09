package com.khedma.salahny.data

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("workerPhone")
    val workerPhone: String = "",
    @SerializedName("imageRes")
    val imageRes: String = "",


)
