package com.khedma.salahny.prsentation.Categories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.GetLocation
import com.khedma.salahny.data.Location
import com.khedma.salahny.data.SalahlyApiService
import com.khedma.salahny.data.Worker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlumberCatViewModel :BaseWorkerViewModel() {
    override suspend fun getWorkerByProfession(): List<Worker> {
        val response = apiService.getAllWorkers()
        Log.i("Response", response.toString())
        return response.values.filter { it.profession == "Plumber" && it.isAvailable }
    }

}




