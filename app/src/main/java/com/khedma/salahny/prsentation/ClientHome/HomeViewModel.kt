package com.khedma.salahny.prsentation.ClientHome

import android.content.Context
import androidx.lifecycle.ViewModel
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.GetLocation

class HomeViewModel : ViewModel(){
    val context: Context = SalahlyApplication.getApplicationContext()
fun getNameOfUser(): String? {
    val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("name", "default_username")
    return username
}


}