package com.example.surfbreak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserListRecyclerAdapter( val userList: List<SurfBreak>):


    RecyclerView.Adapter<UserListRecyclerAdapter.ViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item,
        parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userList = userList[position]

        holder.nameTextView.text = userList.name
        holder.userSpotPosition = position

    }

    override fun getItemCount() = userList.size

    fun removeUserSpot(position: Int) {
        DataManager.userSpots.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView = itemView.findViewById<TextView>(R.id.userSpotTextView)
        val deleteSpotButton = itemView.findViewById<ImageButton>(R.id.deleteImageButton)

        var userSpotPosition = 0

        init {
            deleteSpotButton.setOnClickListener {
                removeUserSpot(userSpotPosition)
            }
        }
    }

}