package com.khedma.salahny.data

data class Worker(
    val name:String,
    val email:String,
    val phone:String,
    val password:String,
    val profession:String,
    val state:String,
    val neghborhood:String,
    val location:Location,
    val isAvailable:Boolean,
    val rating: Double,
    val imageRes:Int

)
{
    constructor() : this("", "", "", "", "", "","",Location(0.0,0.0),true ,0.0,0)
}
