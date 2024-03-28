package com.khedma.salahny.data

import com.google.gson.annotations.SerializedName


data class Client(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("password")
    val password:String,
    @SerializedName("state")
    val state:String,
    @SerializedName("location")
    val location:Location,
    @SerializedName("neghborhood")
    val neghborhood:String


)
