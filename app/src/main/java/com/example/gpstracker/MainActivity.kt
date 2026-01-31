package com.example.gpstracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var latText: TextView
    private lateinit var lngText: TextView
    private var myMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = packageName
        setContentView(R.layout.activity_main)

        latText = findViewById(R.id.lat_display)
        lngText = findViewById(R.id.lng_display)
        map = findViewById(R.id.mapView)

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(18.0)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Dito natin tatawagin ang permission request
        requestLocationPermissions()
    }

    private fun requestLocationPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val fineLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLoc != PackageManager.PERMISSION_GRANTED || coarseLoc != PackageManager.PERMISSION_GRANTED) {
            // Ito ang maglalabas ng popup na nasa image_cce734.jpg
            ActivityCompat.requestPermissions(this, permissions, 100)
        } else {
            startTracking()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Pag-click ng "While using the app", lilitaw ang coordinates
                startTracking()
            } else {
                Toast.makeText(this, "Kailangan ng permission para sa GPS.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startTracking() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000).build()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    val loc = result.lastLocation ?: return
                    val point = GeoPoint(loc.latitude, loc.longitude)

                    latText.text = "Latitude: ${loc.latitude}"
                    lngText.text = "Longitude: ${loc.longitude}"

                    if (myMarker == null) {
                        myMarker = Marker(map)
                        map.overlays.add(myMarker)
                    }
                    myMarker?.position = point
                    map.controller.animateTo(point)
                }
            }, Looper.getMainLooper())
        }
    }

    override fun onResume() { super.onResume(); map.onResume() }
    override fun onPause() { super.onPause(); map.onPause() }
}