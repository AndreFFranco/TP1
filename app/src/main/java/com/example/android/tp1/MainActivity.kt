package com.example.android.tp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.EditText
import android.widget.Toast

const val PARAM1_NAME = "PARAM1_NAME"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View) {

        var user = findViewById<EditText>(R.id.editTextTextPersonName)
        var pass = findViewById<EditText>(R.id.editTextTextPassword)

        Toast.makeText(this, user.text, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(PARAM1_NAME, user.text.toString())
        }
        startActivity(intent)
    }
}