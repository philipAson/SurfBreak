package com.example.surfbreak.maps

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.surfbreak.DataManager
import com.example.surfbreak.R
import com.example.surfbreak.databinding.ActivityDisplayAllMapBinding
import com.example.surfbreak.mapsInfoWindowAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class DisplayAllMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDisplayAllMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayAllMapBinding.inflate(layoutInflater)
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

        createBreaks()
        for (spot in DataManager.spots) {
            Log.d("???", "Name = ${spot.name.toString()}. " +
                    "RightHander = ${spot.goesRight.toString()}. " +
                    "PointBreak = ${spot.pointBreak.toString()}")
        }
    }

    fun createBreaks() {

        val boundsBuilder = LatLngBounds.builder()
        for (spot in DataManager.spots) {
            //REEEEEEMIX
            val location = spot.latitude?.let { spot.longitude?.let { it1 -> LatLng(it, it1) } }
            if (location != null) {
                boundsBuilder.include(location)
            }
            val marker = location?.let { MarkerOptions().position(it) }?.let { mMap.addMarker(it) }
            marker?.tag = spot
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000,1000,0))
    }
}