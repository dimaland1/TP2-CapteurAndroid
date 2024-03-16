package com.example.tp2_capteurs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

// afficher la position de l'appareil

class PositionActivity : AppCompatActivity() {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationTextView = findViewById(R.id.position) // Assurez-vous d'avoir un TextView avec cet ID dans votre layout

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationService()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, démarrer les services de localisation
                startLocationService()
            } else {
                // Permission refusée, gérer le cas où l'utilisateur refuse la permission
                finish() // Fermez l'activité si l'utilisateur refuse la permission
            }
        }
    }

    private fun startLocationService() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // La permission n'est pas accordée, demandez-la.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                location?.let {
                    val locationString = "Latitude: ${location.latitude} \n Longitude: ${location.longitude}"

                    locationTextView.text = locationString // Affichez la localisation dans votre TextView


                } ?: run {
                    // Localisation non disponible
                    locationTextView.text = "Localisation non disponible"

                }
            }
    }
}