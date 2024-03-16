package com.example.tp2_capteurs

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val liste_capt = findViewById<Button>(R.id.liste_capteurs)
        liste_capt.setOnClickListener {
            val intent = Intent(this, ListeCapteurActivity::class.java)
            startActivity(intent)
        }

        val accelerometre = findViewById<Button>(R.id.accelerometre)
        accelerometre.setOnClickListener {
            val intent = Intent(this, AccelerometreActivity::class.java)
            startActivity(intent)
        }

        val direction = findViewById<Button>(R.id.direction)
        direction.setOnClickListener {
            val intent = Intent(this, DirectionActivity::class.java)
            startActivity(intent)
        }

        val flash = findViewById<Button>(R.id.flash)
        flash.setOnClickListener {
            val intent = Intent(this, FlashActivity::class.java)
            startActivity(intent)
        }

        val proximite = findViewById<Button>(R.id.proximite)
        proximite.setOnClickListener {
            val intent = Intent(this, ProximiteActivity::class.java)
            startActivity(intent)
        }

        val position = findViewById<Button>(R.id.position)
        position.setOnClickListener {
            val intent = Intent(this, PositionActivity::class.java)
            startActivity(intent)
        }

    }
}