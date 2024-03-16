package com.example.tp2_capteurs

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ProximiteActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private val proximitySensorListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Do something here if sensor accuracy changes.
        }

        override fun onSensorChanged(event: SensorEvent) {
            val distance = event.values[0]
            val image = findViewById<ImageView>(R.id.position_image)
            // plus la valeur de distance est petite, plus la taille (width et height) de l'image augmente
            if( distance > 10.0 ) {
                image.layoutParams.width = 50
                image.layoutParams.height = 50
                image.requestLayout()
            }else {
                image.layoutParams.width = ((10 - distance) * 100 + 100).toInt()
                image.layoutParams.height = ((10 - distance) * 100 + 100).toInt()
                image.requestLayout()
            }
        }
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximite)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    }

    override fun onResume() {
        super.onResume()
        proximitySensor?.also { proximity ->
            sensorManager.registerListener(
                proximitySensorListener,
                proximity,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(proximitySensorListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(proximitySensorListener)
    }

    override fun onRestart() {
        super.onRestart()
        sensorManager.registerListener(
            proximitySensorListener,
            proximitySensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(proximitySensorListener)
    }

}