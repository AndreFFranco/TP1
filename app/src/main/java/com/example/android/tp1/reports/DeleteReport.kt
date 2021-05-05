package com.example.android.tp1.reports

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.tp1.R
import com.example.android.tp1.adapter.DELID
import com.example.android.tp1.api.EndPoints
import com.example.android.tp1.api.Report
import com.example.android.tp1.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteReport : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var idrep = intent.getIntExtra(DELID, 0)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.alertreport)
        builder.setPositiveButton(R.string.yesdelete){ dialog, which ->
            Toast.makeText(applicationContext, R.string.reportdeleted, Toast.LENGTH_LONG).show()
            val request =  ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.deleteReport(idrep)
            call.enqueue(object : Callback<Report> {
                override fun onResponse(call: Call<Report>, response: Response<Report>) {
                    if (response.isSuccessful){
                        Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@DeleteReport, UserReports::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<Report>, t: Throwable) {
                    Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
            finish()
        }
        builder.setNegativeButton(R.string.nodelete){ dialog, which ->
            val intent = Intent(this, UserReports::class.java)
            startActivity(intent)
            finish()
        }
        builder.show()

    }
}