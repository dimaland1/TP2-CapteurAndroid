package com.example.tp2_capteurs

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.math.pow
import kotlin.math.sqrt



class FlashActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null
    private var isFlashOn = false
    private val shakeThreshold = 120.0f // Seuil de secousse pour allumer/éteindre le flash (en m/s^2)
    private var lastUpdate: Long = 100 // Temps de la dernière mise à jour
    private var last_x: Float = 0.0f
    private var last_y: Float = 0.0f
    private var last_z: Float = 0.0f

    private var accelerometerListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                val curTime = System.currentTimeMillis() // Temps actuel en millisecondes
                if ((curTime - lastUpdate) > 1000) { // Vérifier si le temps écoulé depuis la dernière mise à jour est supérieur à 1000ms
                    val diffTime = (curTime - lastUpdate) // Calculer le temps écoulé
                    lastUpdate = curTime // Mettre à jour le temps actuel

                    val x = event.values[0] // Récupérer les valeurs de l'accéléromètre
                    val y = event.values[1] // Récupérer les valeurs de l'accéléromètre
                    val z = event.values[2] // Récupérer les valeurs de l'accéléromètre

                    val speed = sqrt(x.pow(2) + y.pow(2) + z.pow(2)) / diffTime * 10000 // Calculer la vitesse de secousse

                    if (speed > shakeThreshold) { // Vérifier si la vitesse de secousse est supérieure au seuil
                        if (isFlashOn) { // Vérifier si le flash est allumé
                            toggleFlashLight(false) // Éteindre le flash
                        } else { // Si le flash est éteint
                            toggleFlashLight(true) // Allumer le flash
                        }
                    }

                    last_x = x // Mettre à jour les valeurs précédentes
                    last_y = y // Mettre à jour les valeurs précédentes
                    last_z = z  // Mettre à jour les valeurs précédentes
                }
            }
        }
    }

    private fun toggleFlashLight(status: Boolean) {
        try {
            cameraManager?.setTorchMode(cameraId!!, status)
            isFlashOn = status
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager?.cameraIdList?.get(0)

        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(accelerometerListener)
        toggleFlashLight(false) // Éteindre le flash lors de la pause pour économiser la batterie
    }

    override fun onResume() {
        super.onResume()
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(accelerometerListener)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(accelerometerListener)
    }
}
