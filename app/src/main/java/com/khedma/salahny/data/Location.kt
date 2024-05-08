package com.khedma.salahny.data

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("long")
    val long:Double = 0.0,
    @SerializedName("lat")
    val lat:Double = 0.0,
)
{
    constructor() : this(0.0, 0.0)
}
