package com.example.android.tp1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.android.tp1.adapter.REPD
import com.example.android.tp1.adapter.REPT
import com.example.android.tp1.api.EndPoints
import com.example.android.tp1.api.Report
import com.example.android.tp1.api.ServiceBuilder
import com.example.android.tp1.api.User
import com.example.android.tp1.reports.AddReport
import com.example.android.tp1.reports.UserReports
import com.example.android.tp1.reports.ViewReport
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var report: List<Report>
    private lateinit var sharedPref: SharedPreferences

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallBack: LocationCallback
    private lateinit var locationRequest: LocationRequest

    private  var newWordActivityRequestCode = 1

    private var click = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //MARKERS
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        var position: LatLng
        sharedPref = getSharedPreferences(getString(R.string.sharedpref), Context.MODE_PRIVATE)
        val id = sharedPref.getInt(R.string.spid.toString(), 0)
        val loc = Location("dummyprovider")
        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>) {
                if (response.isSuccessful) {
                    report = response.body()!!
                    for (rep in report) {
                        val dist = calculateDistance(loc.latitude, loc.longitude, rep.latitude.toDouble(), rep.longitude.toDouble())
                        if (id == rep.user_id) {
                            position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                            mMap.addMarker(MarkerOptions().position(position).title(rep.title + " - " + rep.description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                        } else {
                            position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                            mMap.addMarker(MarkerOptions().position(position).title(rep.title + " - " + rep.description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        //added to implement location periodic updates
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                //mMap.addMarker(MarkerOptions().position(loc).title("Marker"))
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                findViewById<TextView>(R.id.coords).setText("Lat: " + loc.latitude + " - Long: " + loc.longitude)
                Log.d("**** ANDRE", "new location received - " + loc.latitude + " - " + loc.longitude)
            }
        }
        //request creation
        createLocationRequest()

        //FABS

        val fab = findViewById<FloatingActionButton>(R.id.insertbtn)
        fab.setOnClickListener {
            val intent = Intent(this@MapsActivity, AddReport::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        val listFab = findViewById<FloatingActionButton>(R.id.reportsFab)

        listFab.setOnClickListener {
            val intent = Intent(this@MapsActivity, UserReports::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)

        }

        val notesFab = findViewById<FloatingActionButton>(R.id.notesFab)

        notesFab.setOnClickListener {
            val intent = Intent(this@MapsActivity, NotesActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)

        }

        val fabLogout = findViewById<FloatingActionButton>(R.id.logoutFab)

        fabLogout.setOnClickListener {
            val editor: SharedPreferences.Editor= sharedPref.edit()
            editor.clear()
            editor.commit()
            editor.apply()
            val intent = Intent(this@MapsActivity, MainActivity::class.java)
            startActivity(intent)

        }
/*
        val fabmenu = findViewById<ExtendedFloatingActionButton>(R.id.openfabs)
        fabmenu.shrink()
        fabmenu.setOnClickListener {
            if (!click){
                fabLogout.visibility = View.VISIBLE
                fab.visibility = View.VISIBLE
                listFab.visibility = View.VISIBLE
                notesFab.visibility = View.VISIBLE

                fabmenu.extend()
                click = true
            } else {

                fabLogout.visibility = View.GONE
                fab.visibility = View.GONE
                listFab.visibility = View.GONE
                notesFab.visibility = View.GONE

                fabmenu.shrink()
                click = false
            }

        }
*/
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        } else {
            mMap.isMyLocationEnabled = true
        }

        mMap.setOnInfoWindowClickListener(this)

        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        setUpMap()
    }

    override fun onInfoWindowClick(marker: Marker) {
        val intent = Intent(this, ViewReport::class.java).apply {
            putExtra(REPT, marker.title)
            putExtra(REPD, marker.snippet)
        }
        startActivity(intent)
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        // distance in meter
        return results[0]
    }

    fun setUpMap() {

        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
        } else {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null) {
                    lastLocation = location
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, null)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallBack)
        Log.d("**** ANDRE", "onPause - removeLocationUpdates")
    }

    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("**** ANDRE", "onResume - startLocationUpdates")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menumapa, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.filterAll -> {
                mMap.clear()
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.filterAccident -> {
                mMap.clear()
                val id = sharedPref.getInt(R.string.spid.toString(), 0)
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getReports()
                var position: LatLng
                call.enqueue(object : Callback<List<Report>> {
                    override fun onResponse(
                            call: Call<List<Report>>,
                            response: Response<List<Report>>
                    ) {
                        if (response.isSuccessful) {
                            report = response.body()!!
                            for (rep in report) {
                                if(rep.type_id == 1) {
                                    if (id == rep.user_id) {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.title + " - " + rep.description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                                    } else {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.title + " - " + rep.description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                true
            }
            R.id.filterConstruction -> {
                mMap.clear()
                val id = sharedPref.getInt(R.string.spid.toString(), 0)
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getReports()
                var position: LatLng
                call.enqueue(object : Callback<List<Report>> {
                    override fun onResponse(
                            call: Call<List<Report>>,
                            response: Response<List<Report>>
                    ) {
                        if (response.isSuccessful) {
                            report = response.body()!!
                            for (rep in report) {
                                if(rep.type_id == 2) {
                                    if (id == rep.user_id) {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.title + " - " + rep.description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                                    } else {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.title + " - " + rep.description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                true
            }
            R.id.filterSanitation -> {
                mMap.clear()
                val id = sharedPref.getInt(R.string.spid.toString(), 0)
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getReports()
                var position: LatLng
                call.enqueue(object : Callback<List<Report>> {
                    override fun onResponse(
                            call: Call<List<Report>>,
                            response: Response<List<Report>>
                    ) {
                        if (response.isSuccessful) {
                            report = response.body()!!
                            for (rep in report) {
                                if(rep.type_id == 3) {
                                    if (id == rep.user_id) {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.title + " - " + rep.description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                                    } else {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.title + " - " + rep.description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}