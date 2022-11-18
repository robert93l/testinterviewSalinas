package com.example.testinterviewsalinas.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager


class GPS {

    private var LOCATION_REFRESH_TIME = 300000
    private var LOCATION_REFRESH_DISTANCE = 0

    @SuppressLint("MissingPermission")
    fun startListeningUserLocation(context: Context, myListener: MyLocationListener) {
        val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                myListener.onLocationChanged(location)
            }
        }
        mLocationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            LOCATION_REFRESH_TIME.toLong(),
            LOCATION_REFRESH_DISTANCE.toFloat(),
            locationListener
        )
    }

    interface MyLocationListener {
        fun onLocationChanged(location: Location?)
    }
}