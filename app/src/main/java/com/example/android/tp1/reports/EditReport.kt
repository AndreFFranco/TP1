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

    private lateinit var desc: EditText
    private lateinit var tit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_report)

        val desc_edit = intent.getStringExtra(REPD)
        findViewById<EditText>(R.id.reportdesc).setText(desc_edit)

        val tit_edi = intent.getStringExtra(REPT)
        findViewById<EditText>(R.id.reporttitle).setText(tit_edi)

        val save_Edit = findViewById<Button>(R.id.save)
        save_Edit.setOnClickListener {
            guardarEdit2()
            finish()
        }

        val cancel_edit = findViewById<Button>(R.id.cancel_edit)
        cancel_edit.setOnClickListener {
            val intent = Intent(this, UserReports::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun guardarEdit2() {
        tit = findViewById(R.id.reporttitle)
        desc = findViewById(R.id.reportdesc)


        var id_oco_edit: Int = intent.getIntExtra(REPID, 0)
        val tituEnv: String = tit.text.toString()
        val descEnv: String = desc.text.toString()

        val replyIntent = Intent()
        if (TextUtils.isEmpty(desc.text.toString()) && TextUtils.isEmpty(tit.text.toString())) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
            Toast.makeText(this, R.string.emptyfield, Toast.LENGTH_SHORT).show()

        } else {

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.editReport(id_oco_edit, tituEnv, descEnv)
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