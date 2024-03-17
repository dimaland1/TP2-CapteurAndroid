package com.example.tp2_capteurs

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout

/*
Définir une application qui affiche des couleurs différentes comme fond d’écran en
fonction des valeurs de l'accéléromètre. Répartir les valeurs en trois catégories : valeurs
inférieures : vert ; valeurs moyennes : noir ; valeurs supérieures : rouge

 */

class AccelerometreActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private var accelerometerListener : SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val layout = findViewById<LinearLayout>(R.id.accelerometre)
                if (x < -5.0 || y < -5.0 || z < -5.0) {
                    layout.setBackgroundColor(Color.GREEN)
                } else if (x > 5.0 || y > 5.0 || z > 5.0) {
                    layout.setBackgroundColor(Color.RED)
                } else {
                    layout.setBackgroundColor(Color.BLACK)
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometre)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(accelerometerListener)
    }



}