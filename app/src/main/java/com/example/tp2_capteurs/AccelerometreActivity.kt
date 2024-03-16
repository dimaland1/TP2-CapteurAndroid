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
                val color = Color.rgb((x*25).toInt(), (y*25).toInt(), (z*25).toInt())
                val layout = findViewById<LinearLayout>(R.id.accelerometre)
                layout.setBackgroundColor(color)
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