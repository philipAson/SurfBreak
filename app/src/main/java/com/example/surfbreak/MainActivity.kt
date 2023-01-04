package com.example.surfbreak

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

val db = Firebase.firestore
val docRef = db.collection("SurfBreak")
val auth = Firebase.auth

class MainActivity : AppCompatActivity() {

    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            signUp()
        }

        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            signIn()
        }

        if (auth.currentUser != null) {
            goToRecyclerActivity()
        }

        /*Setting up a success listener in main that makes sure we have the data from db, before we start next activity.
          Chosing success listener due tue relatively low consumption of capacity, and that is not constantly listening. */

    }
    fun signUp() {
        val email = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("!!!", "create success")
                    goToRecyclerActivity()
                } else {
                    Toast.makeText(this, "user not created: ${task.exception.toString()}", Toast.LENGTH_LONG)
                    Log.d("!!!", "user not created: ${task.exception}")
                }
            }
    }

    fun signIn() {
        val email = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("!!!", "signed in")
                    goToRecyclerActivity()
                } else {
                    Toast.makeText(this, "user not signed in: ${task.exception.toString()}", Toast.LENGTH_LONG)
                    Log.d("!!!", "user not signed in: ${task.exception}")
                }
            }
    }

    fun goToRecyclerActivity() {
        docRef.get().addOnSuccessListener { documentSnapShot ->
            val intent = Intent(this, RecyclerActivity2::class.java)
            DataManager.spots.clear()
            for (document in documentSnapShot.documents) {
                val spot = document.toObject<SurfBreak>()
                if (spot != null) {
                    DataManager.spots.add(spot)
                }
            }
            startActivity(intent)
        }
    }
}



//        db.collection("SurfBreak".whereEqualTo("user", uid)
//            .addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                return@addSnapshotListener
//            }
//
//            if (snapshot != null) {
//                spots.clear()
//                for (document in snapshot.documents) {
//                    val surfBreak = document.toObject<spots>()
//                    if (surfBreak != null) {
//                        spots.add(surfBreak)a
//                    }
//                }
//            }
//
//            for (spot in spots) {
//                if (spot.user != null) {
//                    db.collection("user").document(spot.user!!).get().addOnSuccessListener { snapshot ->
//                        val user = snapshot.toObject<User>()
//                        Log.d("!!!", "${spot.name} ${user.name}")
//                    }
//                }
//            }
//        }
