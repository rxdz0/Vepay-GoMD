package com.example.vepay_go_md

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vepay_go_md.databinding.ActivityProfilBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var vBinding: ActivityProfilBinding

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        vBinding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent =Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}