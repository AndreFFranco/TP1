package com.example.android.tp1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val name = intent.getStringExtra(PARAM1_NAME)

        val teste = findViewById<TextView>(R.id.teste).apply {
            text = name
        }

    }

}