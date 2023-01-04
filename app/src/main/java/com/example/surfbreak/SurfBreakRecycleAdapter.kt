package com.example.surfbreak

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surfbreak.maps.DisplayThisMapActivity

class SurfBreakRecycleAdapter(val context: Context, val spots: List<SurfBreak>
, val onClickListener: OnClickListener) : RecyclerView.Adapter<SurfBreakRecycleAdapter.ViewHolder>() {

    var layoutInflater = LayoutInflater.from(context)

    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.spot_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val surfBreak = DataManager.spots[position]

        holder.itemView.setOnClickListener {
            Log.d("!!!", "adapter calling pos: $position")

            onClickListener.onItemClick(position)

            val intent = Intent(context, DisplayThisMapActivity::class.java)
            intent.putExtra("position", position)
            context.startActivity(intent)
        }
        holder.nameTextView.text = surfBreak.name
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var nameTextView = itemView.findViewById<TextView>(R.id.userSpotTextView)
    }
}