package com.example.android.tp1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

const val PARAM1_NAME = "PARAM1_NAME"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        /*Example
        val soundValue = sharedPref.getBoolean(getString(R.string.sound), false)
        Log.d("****SHAREDPREF", "Read $soundValue")

        if(soundValue) {
            findViewById<CheckBox>(R.idcheckBox).isChecked = true
        }*/

    }
/*
    fun checkboxClicked(view: View) {
        if(view is CheckBox) {
            val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putBoolean(getString(R.string.sound), view.isChecked)
                commit()
            }
            Log.d("****SHAREDPREF", "Changed to ${view.isChecked}")
        }
    }*/

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