package com.example.android.tp1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.tp1.api.EndPoints
import com.example.android.tp1.api.LoginCheck
import com.example.android.tp1.api.ServiceBuilder
import com.example.android.tp1.api.User
import kotlinx.android.synthetic.main.notes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val PARAM1_NAME = "PARAM1_NAME"

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var user: EditText
    private lateinit var pass: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            login()
        }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener{
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }

        user = findViewById(R.id.editTextTextPersonName)
        pass = findViewById(R.id.editTextTextPassword)

        btnLogin = findViewById(R.id.button)

        btnLogin.setOnClickListener() {
            login()
        }
/*
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUsers()

        call.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = UserAdapter(response.body()!!)
                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
*/
        //SHARED PREFERENCES
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

    fun login() {
        val username =  user.text.toString()
        val password = pass.text.toString()

        if(username.isNotEmpty() && password.isNotEmpty()){
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.login(username, password)

            call.enqueue(object : Callback<LoginCheck> {
                override fun onResponse(call: Call<LoginCheck>, response: Response<LoginCheck>) {
                    if (response.isSuccessful) {
                        val safe : LoginCheck=response.body()!!
                        Toast.makeText(this@MainActivity,safe.message,Toast.LENGTH_SHORT).show()

                        if(safe.status == true) {
                            val sharedPreferences = getSharedPreferences(getString(R.string.sharedpref), Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                putInt(R.string.id_sharedpref.toString(), safe.id)
                                commit()

                            }
                            val intent = Intent(this@MainActivity, MapsActivity::class.java)
                            intent.putExtra("id",safe.id)
                            startActivity(intent)
                        }

                    }

                }

                override fun onFailure(call: Call<LoginCheck>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"${t.message}",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}