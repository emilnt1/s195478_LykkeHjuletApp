package com.example.s195478_lykkehjuletapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startFragment = StartFragment()
        val wordFragment = wordsFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragScreen, startFragment)
            commit()
        }

    }
}