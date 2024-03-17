package com.example.tp2_capteurs

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class DirectionActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private var gravity: Sensor? = null
    private var gravityListener : SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val layout = findViewById<ImageView>(R.id.image_direction)
                val texte = findViewById<TextView>(R.id.direction_text)
                if (x > 0.5) {
                    // aller à
                    layout.setImageResource(R.drawable.fleche_gauche)
                    texte.text = "gauche"
                } else if (x < -0.5) {
                    // aller à
                    layout.setImageResource(R.drawable.fleche_droite)
                    texte.text = "droite"
                } else if (y > 0.5) {
                    // aller en
                    layout.setImageResource(R.drawable.fleche_haut)
                    texte.text = "haut"
                } else if (y < -0.5) {
                    // aller en
                    layout.setImageResource(R.drawable.fleche_bas)
                    texte.text = "bas"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction)

        // EXERCICE : Définir une application qui affiche sur l'écran la direction du mouvement : gauche,
        //droite, haut, bas.

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

    }

    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(gravityListener, gravity, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(gravityListener)
    }
}