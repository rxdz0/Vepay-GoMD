package com.example.vepay_go_md

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vepay_go_md.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

    private lateinit var vBinding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        vBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(vBinding.root)
        auth=FirebaseAuth.getInstance()

        vBinding.tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        vBinding.btnLogin.setOnClickListener {

            val email= vBinding.etEmail.text.toString()
            val password= vBinding.password.text.toString()

            if (email.isEmpty()){
                vBinding.etEmail.error="email wajib diisi"
                vBinding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                vBinding.etEmail.error="format email salah"
                vBinding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                vBinding.password.error="password wajib diisi"
                vBinding.password.requestFocus()
                return@setOnClickListener
            }
            loginUser(email,password)

        }



    }

    private fun loginUser(email:String, password: String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Selamat Datang ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            Toast.makeText(this, "Selamat datang kembali", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            Toast.makeText(this, "anda belum login", Toast.LENGTH_LONG).show()
        }
    }
}