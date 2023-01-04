package com.example.surfbreak

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class mapsInfoWindowAdapter(val context: Context) : GoogleMap.InfoWindowAdapter {

    val layoutInflater = LayoutInflater.from(context)

    override fun getInfoContents(p0: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View? {
        val infoWindow = layoutInflater.inflate(R.layout.maps_info_window, null)

        val imageView = infoWindow.findViewById<ImageView>(R.id.spotImageView)
        val titleView = infoWindow.findViewById<TextView>(R.id.spotNameInfoWinTextView)
        val snippetView = infoWindow.findViewById<TextView>(R.id.spotAttrInfoWinTextView)

        var direction = "Leftie"
        var type = "Beach break"

        val spot = marker.tag as? SurfBreak

        if (spot != null) {
            direction = if (spot.goesRight == true) {
                "Righthand"
            } else {
                "Leftie"
            }
        }

        if (spot != null) {
            type = if (spot.pointBreak == true) {
                "Point break"
            } else {
                "Beach break"
            }
        }

        titleView.text = spot?.name
        snippetView.text = "$direction, $type."
        imageView

        return infoWindow
    }
}