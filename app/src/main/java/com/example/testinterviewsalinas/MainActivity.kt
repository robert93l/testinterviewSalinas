package com.example.testinterviewsalinas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testinterviewsalinas.databinding.ActivityMainBinding
import com.example.testinterviewsalinas.di.TestComponent
import com.example.testinterviewsalinas.webservices.LocationService
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var testComponent: TestComponent

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            var isGranted = true
            permissions.entries.forEach { isGranted = isGranted && it.value }
            if (isGranted) {
                Toast.makeText(this, R.string.succes_permission, Toast.LENGTH_SHORT).show()
                initService()
            } else {
                Toast.makeText(this, R.string.failed_permission, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        testComponent = MyApplication().appComponent.testComponent().create()
        testComponent.inject(this)
        super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar.toolbar)
        initServiceGPS()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movies, R.id.navigation_location, R.id.navigation_storage
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
         navView.setupWithNavController(navController)
    }

    private fun initServiceGPS() {
        if(!checkPermissionRequest()){
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_attention))
                .setMessage(getString(R.string.permission_message))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    Toast.makeText(this, R.string.failed_permission, Toast.LENGTH_SHORT).show()
                }
                .show()
        } else {
            initService()
        }
    }

    private fun checkPermissionRequest(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
        val result2 = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED
    }

    private fun initService() {
        ContextCompat.startForegroundService(this, Intent(this, LocationService::class.java))
    }
}