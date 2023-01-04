package com.example.surfbreak

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.surfbreak.maps.CreateMapActivity
import com.example.surfbreak.maps.DisplayAllMapActivity

class RecyclerActivity2 : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val thisAdapter = SurfBreakRecycleAdapter(this, DataManager.spots, object : SurfBreakRecycleAdapter.OnClickListener{

            override fun onItemClick(position: Int) {
                // Navigating from main activity to maps activity
                Log.d("!!!", "main calling pos: $position")

            }
        })
        recyclerView.adapter = thisAdapter

        val signOutButton = findViewById<ImageButton>(R.id.signOutButton)
        val mapButton = findViewById<ImageButton>(R.id.mapButton)
        val addLocationButton = findViewById<ImageButton>(R.id.addButton)


        signOutButton.setOnClickListener {
            signOut()
        }
        mapButton.setOnClickListener {
            val intent = Intent(this, DisplayAllMapActivity::class.java)
            startActivity(intent)
        }
        addLocationButton.setOnClickListener {
            val intent = Intent(this, CreateMapActivity::class.java)
            startActivity(intent)
        }
    }
    //Inflater for Menu edit user list
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recycler_view_user_list, menu)
        return super.onCreateOptionsMenu(menu)
    }
    // Menu user edit list button functionality
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mIUserList)
        userListFragment()

        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


    }
    private fun userListFragment() {
        val userListFragment = supportFragmentManager.findFragmentByTag("userListFragment")

        if (userListFragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(userListFragment)
            transaction.commit()
        } else {
            val userListFragment = UserListFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, userListFragment, "userListFragment")
            transaction.commit()

        }
    }
}