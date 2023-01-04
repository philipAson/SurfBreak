package com.example.surfbreak.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.surfbreak.DataManager
import com.example.surfbreak.R
import com.example.surfbreak.databinding.ActivityDisplayThisMapBinding
import com.example.surfbreak.mapsInfoWindowAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DisplayThisMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDisplayThisMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayThisMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


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

        val adapter = mapsInfoWindowAdapter(this)
        mMap.setInfoWindowAdapter(adapter)

        val pos = intent.getIntExtra("position", -1)
        val spot = DataManager.spots[pos]
        val location = spot.longitude?.let { spot.latitude?.let { it1 -> LatLng(it1, it) } }

        val marker = location?.let { MarkerOptions().position(it) }?.let { mMap.addMarker(it) }
        marker?.tag = spot

        supportActionBar?.title = spot.name

        // center marker
        location?.let { CameraUpdateFactory.newLatLngZoom(it, 7f) }?.let { mMap.moveCamera(it) }
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(7f))
    }
}