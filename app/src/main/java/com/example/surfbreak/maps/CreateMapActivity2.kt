//package com.example.surfbreak.maps
//
//import android.annotation.SuppressLint
//import android.content.DialogInterface
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.EditText
//import android.widget.Switch
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import com.example.surfbreak.*
//import com.example.surfbreak.databinding.ActivityCreateMapBinding
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.firestore.ktx.toObject
//
//class CreateMapActivity2 : AppCompatActivity(), OnMapReadyCallback {
//
//    private lateinit var mMap: GoogleMap
//    private var spots: MutableList<SurfBreak> = mutableListOf()
//    private var markers: MutableList<Marker> = mutableListOf()
//    private lateinit var binding: ActivityCreateMapBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityCreateMapBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//        // Setting up a Snackbar to give instructions on how to add a SurfBreak
//        mapFragment.view?.let {
//            Snackbar.make(it, "Long press on the location you want to add your SurfBreak", Snackbar.LENGTH_INDEFINITE)
//                .setAction("Ok", {})
//                .setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
//                .show()
//        }
//
//
//    }
//    //Inflater for Menu save button
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_create_surf_break, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//    //Menu save button functionality
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.miSave){
//            if (spots.isEmpty()) {
//                Toast.makeText(this,"there must be atleast one marker on the map", Toast.LENGTH_LONG).show()
//                return true
//            }
//
//            for (spot in spots) {
//                db.collection("SurfBreak").add(spot)
//            }
//            spots.clear()
//            // Unnecessary?
//            docRef.get().addOnSuccessListener { documentSnapShot ->
//                DataManager.spots.clear()
//                for (document in documentSnapShot.documents) {
//                    val spot = document.toObject<SurfBreak>()
//                    if (spot != null) {
//                        DataManager.spots.add(spot)
//                    }
//                }
//                finish()
//            }
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        val adapter = mapsInfoWindowAdapter(this)
//        mMap.setInfoWindowAdapter(adapter)
//
//        // tap on infoWindow to delete marker and spot(SurfBreak)
//        mMap.setOnInfoWindowClickListener { markerToDelete ->
//            for (spot in spots) {
//                if (spot.name.equals(markerToDelete.title)) {
//                    spots.remove(spot)
//                } else {
//                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT)
//                }
//            }
//            markers.remove(markerToDelete)
//            markerToDelete.remove()
//            Log.d("???", spots.size.toString())
//        }
//        // LongPress on Map to call call create SurfBreak function
//        mMap.setOnMapLongClickListener { latLng ->
//            showAlertDialog(latLng)
//            }
//        }
//    // Create SurfBreak Function (calling AlertDialog)
//    @SuppressLint("UseSwitchCompatOrMaterialCode")
//    private fun showAlertDialog(latLng: LatLng) {
//
//        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_surf_break,null)
//        val dialog =
//            AlertDialog.Builder(this)
//                .setTitle("Create a SurfBreak")
//                .setView(placeFormView)
//                .setNegativeButton("Cancel",null)
//                .setPositiveButton("Ok",null)
//                .show()
//        // When you press OK button this happens
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
//            val name = placeFormView.findViewById<EditText>(R.id.spotNameEditText).text.toString()
//            val goesRight: Boolean = placeFormView.findViewById<Switch>(R.id.goesRightSwitch).isChecked
//            val isPointBreak: Boolean = placeFormView.findViewById<Switch>(R.id.pointBreakSwitch).isChecked
//
//            if (name.trim().isEmpty()) {
//                Toast.makeText(this,"SurfBreak must have (Name :) filled in.", Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }
//
//
////            val marker = mMap.addMarker(MarkerOptions().position(latLng).title(name).snippet("spo"))
//
//            val spot = SurfBreak(isPointBreak, name, latLng.latitude, latLng.longitude, goesRight)
//            val marker = spot.latitude?.let { it1 ->
//                spot.longitude?.let { it2 ->
//                    LatLng(
//                        it1, it2
//                    )
//                }
//            }?.let { it2 -> MarkerOptions().position(it2) }?.let { it3 -> mMap.addMarker(it3) }
//            marker?.tag = spot
//            if (marker != null) {
//                markers.add(marker)
//                spots.add(spot)
//                dialog.dismiss()
//            }
//            Log.d("???", spots.size.toString())
//            Log.d("???", "this spot in list name: ${ spots.last().name.toString() }")
//            Log.d("???", "and isPointbreak = ${ spots.last().pointBreak.toString() }")
//            Log.d("???", "goesRight = ${ spots.last().goesRight.toString() }")
//            Log.d("???", "OG pointBreak: Boolean is declared: $isPointBreak")
//            Log.d("???", "OG righthander: Boolean is declared: $goesRight")
//        }
//    }
//}