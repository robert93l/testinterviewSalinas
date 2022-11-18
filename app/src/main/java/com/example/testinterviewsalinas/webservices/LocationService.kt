package com.example.testinterviewsalinas.webservices

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.testinterviewsalinas.R
import com.example.testinterviewsalinas.repository.Repository
import com.example.testinterviewsalinas.utils.GPS


class LocationService : Service(){

    companion object {
        var isServiceStarted: Boolean = false
        private const val NOTIFICATION_CHANNEL_ID = "location_notification"
        var mLocation: Location? = null
    }

    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true
        showNotification()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        GPS().startListeningUserLocation(
            this, object : GPS.MyLocationListener {
                override fun onLocationChanged(location: Location?) {
                    mLocation = location
                    mLocation?.let {
                        Repository().uploadLocation(location)
                    }

                }
            })
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false

    }

    private fun showNotification() {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(false)
                .setContentText(getString(R.string.notification_message))
                .setSmallIcon(R.drawable.ic_baseline_location_on_24)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        }
    }
}