package com.example.tp2_capteurs

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class ListeCapteurActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_capteur)

        // lister les capteurs disponibles
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val sensorList = findViewById<LinearLayout>(R.id.linearLayout)

        for (sensor in deviceSensors) {
            // creer un texte view pour chaque capteur et si il est disponible ou non par une couleur verte ou rouge
            val sensorTextView = TextView(this)
            sensorTextView.text = sensor.name
            sensorTextView.textSize = 20f
            if (sensor.isWakeUpSensor) {
                sensorTextView.setBackgroundColor(getColor(R.color.green))
            } else {
                sensorTextView.setBackgroundColor(getColor(R.color.red))
            }
            sensorTextView.setPadding(0, 10, 0, 10)
            sensorList.addView(sensorTextView)

            // ajouter une bare de separation
            val separator = TextView(this)
            separator.text = "-------------------"
            sensorList.addView(separator)
        }



    }
}