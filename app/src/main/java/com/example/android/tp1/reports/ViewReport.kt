package com.example.android.tp1.reports

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.tp1.MapsActivity
import com.example.android.tp1.R
import com.example.android.tp1.api.EndPoints
import com.example.android.tp1.api.Report
import com.example.android.tp1.api.ServiceBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewReport : AppCompatActivity() {
    private lateinit var report: List<Report>
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_report)

        title = findViewById(R.id.reptitle)
        description = findViewById(R.id.repdescription)
        image = findViewById(R.id.repimage)

        val buttonBack = findViewById<Button>(R.id.backbtn)
        buttonBack.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(
                    call: Call<List<Report>>,
                    response: Response<List<Report>>
            ) {
                if (response.isSuccessful) {
                    report = response.body()!!
                    for (rep in report) {
                        description.setText(rep.description)
                        title.setText(rep.title)
                        Picasso.with(this@ViewReport)
                                .load("https://smartcityandrefranco.000webhostapp.com/myslim/api/uploads/" + rep.image + ".png")
                                .into(image)
                    }
                }
            }

            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@ViewReport, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
