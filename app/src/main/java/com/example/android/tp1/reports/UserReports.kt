package com.example.android.tp1.reports

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.R
import com.example.android.tp1.adapter.ReportAdapter
import com.example.android.tp1.api.EndPoints
import com.example.android.tp1.api.Report
import com.example.android.tp1.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserReports: AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reports)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val request = ServiceBuilder.buildService(EndPoints::class.java)

        sharedPreferences = getSharedPreferences(getString(R.string.sharedpref), Context.MODE_PRIVATE)

        val id = sharedPreferences.getInt(R.string.spid.toString(), 0)
        val call = request.getUserRep(id)

        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(
                    call: Call<List<Report>>,
                    response: Response<List<Report>>
            ) {
                if (response.isSuccessful) {
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@UserReports)
                        adapter = ReportAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@UserReports, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

}