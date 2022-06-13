package com.example.vepay_go_md

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

//import com.google.firebase.messaging.FirebaseMessaging

private lateinit var store: FirebaseFirestore
private lateinit var uID: String
private lateinit var auth: FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        store= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()





    }





    /*fun postToFirestore(msg: String) {

        auth = FirebaseAuth.getInstance()
        store= FirebaseFirestore.getInstance()
        uID= auth!!.currentUser!!.uid

        store.collection("users/${uID}/token").document("token").set(msg)
    }*/


    fun toProfile(view: View){
        val intent= Intent(this,ProfilActivity::class.java)
        startActivity(intent)
    }

    fun toKendaraan(view: View){
        val intent= Intent(this,DaftarKendaraanActivity::class.java)
        startActivity(intent)
    }

}