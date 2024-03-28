package com.khedma.salahny.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
class GetLocation {
fun getCurrentLocation(context: Context, onSuccess: (Location) -> Unit, onFailure: (String) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: android.location.Location? ->
                location?.let {
                    onSuccess(
                        Location(
                        it.longitude,
                        it.latitude
                    )

                    )
                    Log.i("mioLocation",it.toString())
                } ?: run {
                    onFailure("Location not available")
                }
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Location request failed")
            }
    } else {
        onFailure("Location permission not granted")
    }
}}