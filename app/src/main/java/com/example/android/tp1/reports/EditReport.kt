package com.example.android.tp1.reports

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.tp1.R
import com.example.android.tp1.adapter.REPD
import com.example.android.tp1.adapter.REPID
import com.example.android.tp1.adapter.REPT
import com.example.android.tp1.api.EndPoints
import com.example.android.tp1.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditReport : AppCompatActivity() {

    private lateinit var description: EditText
    private lateinit var title: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_report)

        val editDescription = intent.getStringExtra(REPD)
        findViewById<EditText>(R.id.reportdesc).setText(editDescription)

        val editTitle = intent.getStringExtra(REPT)
        findViewById<EditText>(R.id.reporttitle).setText(editTitle)

        val save = findViewById<Button>(R.id.save)
        save.setOnClickListener {
            saveChanges()
            finish()
        }

        val back = findViewById<Button>(R.id.cancel)
        back.setOnClickListener {
            val intent = Intent(this, UserReports::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun saveChanges() {
        title = findViewById(R.id.reporttitle)
        description = findViewById(R.id.reportdesc)


        var idRep: Int = intent.getIntExtra(REPID, 0)
        val newTitle: String = title.text.toString()
        val newDesc: String = description.text.toString()

        val replyIntent = Intent()
        if (TextUtils.isEmpty(description.text.toString()) && TextUtils.isEmpty(title.text.toString())) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
            Toast.makeText(this, R.string.emptyfield, Toast.LENGTH_SHORT).show()

        } else {

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.editReport(idRep, newTitle, newDesc)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, R.string.save, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@EditReport, UserReports::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })


        }
        finish()
    }

}