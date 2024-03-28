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

class PlumberCatViewModel :ViewModel() {
    private var apiService: SalahlyApiService
    val context: Context = SalahlyApplication.getApplicationContext()


// Request location updates


    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://irregular-worker-connections-default-rtdb.firebaseio.com/")
            .build()
        apiService=retrofit.create(SalahlyApiService::class.java)
    }
    suspend fun getPlumbers(): List<Worker> {
        val response = apiService.getAllWorkers()
        Log.i("respon", response.toString())
        val plumberWorkers = response.values.filter { it.profession == "Plumber" && it.isAvailable }
        Log.i("respo", plumberWorkers.toString())
        return plumberWorkers
    }



    suspend fun getLocation(context: Context): Location? {
        var location: Location? = null

        // Initialize FusedLocationProviderClient
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        // Request last known location
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Handle permission not granted
            return null
        }

        // Get last known location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { locationData: android.location.Location? ->
                locationData?.let {
                    // Create a Location object with latitude and longitude
                    location = Location(lat = it.latitude, long = it.longitude)
                }
            }
            .addOnFailureListener { e ->
                // Handle failure case
                e.printStackTrace()
            }

        return location
    }


    fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val R = 6371e3 // Earth radius in meters
        val φ1 = Math.toRadians(lat1)
        val φ2 = Math.toRadians(lat2)
        val Δφ = Math.toRadians(lat2 - lat1)
        val Δλ = Math.toRadians(lon2 - lon1)

        val a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
                Math.cos(φ1) * Math.cos(φ2) *
                Math.sin(Δλ / 2) * Math.sin(Δλ / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return R * c
    }

    suspend fun getSortedWorker(context: Context): List<Worker> {
        // Get user's location
        val userLocation = getLocation(context)

        // Fetch plumbers
        val plumbers = getPlumbers()

        // Sort plumbers based on distance from user's location
        val sortedPlumbers = plumbers.sortedBy { plumber ->
            userLocation?.let { userLoc ->
                calculateDistance(userLoc.lat, userLoc.long, plumber.location.lat, plumber.location.long)
            } ?: 0.0 // Default distance if user's location is not available
        }

        return sortedPlumbers
    }







}




