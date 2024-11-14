package com.khedma.salahny.prsentation.Categories
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.Location
import com.khedma.salahny.data.SalahlyApiService
import com.khedma.salahny.data.Worker
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseWorkerViewModel : ViewModel() {
    // API Service and context shared among all ViewModels
    protected val apiService: SalahlyApiService
    protected val context: Context = SalahlyApplication.getApplicationContext()

    init {
        // Retrofit setup
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://irregular-worker-connections-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(SalahlyApiService::class.java)
    }

    // Function to get user location
    suspend fun getLocation(context: Context): Location? {
        var location: Location? = null
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Check permissions
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null // Permissions not granted
        }

        // Get last known location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { locationData: android.location.Location? ->
                locationData?.let {
                    location = Location(lat = it.latitude, long = it.longitude)
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }

        return location
    }

    // Function to calculate distance between two coordinates
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
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

    // Abstract function to be implemented in child classes for fetching specific workers
    abstract suspend fun getWorkerByProfession(): List<Worker>

    // Sort workers by proximity to the user's location
    suspend fun getSortedWorkers(context: Context): List<Worker> {
        val userLocation = getLocation(context)
        val workers = getWorkerByProfession()

        return workers.sortedBy { worker ->
            userLocation?.let { userLoc ->
                calculateDistance(userLoc.lat, userLoc.long, worker.location.lat, worker.location.long)
            } ?: 0.0
        }
    }
}
