package com.example.vepay_go_md

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.vepay_go_md.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var vBinding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        vBinding=ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(vBinding.root)
        auth=FirebaseAuth.getInstance()

        vBinding.btnRegis.setOnClickListener {
            val fullname = vBinding.fullname.text.toString()
            val email = vBinding.email.text.toString()
            val pin = vBinding.pin
            val password = vBinding.password.text.toString()
            val confirm = vBinding.confirmPassword.text.toString()

            if (fullname.isEmpty()){
                vBinding.fullname.error = "nama wajib diisi"
                vBinding.fullname.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()){
                vBinding.email.error="email wajib diisi"
                vBinding.email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                vBinding.email.error="format email salah"
                vBinding.email.requestFocus()
                return@setOnClickListener
            }
            if (pin.text.toString().trim().isEmpty() ){
                vBinding.pin.error="pin tidak boleh kosong"
                vBinding.pin.requestFocus()
                return@setOnClickListener
            }
            if (pin.text.toString().trim().length != 6){
                vBinding.pin.error="isi dengan 6 angka"
                vBinding.pin.requestFocus()
                return@setOnClickListener
            }
            if (password.length<6){
                vBinding.password.error="password terlalu pendek"
                vBinding.password.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                vBinding.password.error="password wajib diisi"
                vBinding.password.requestFocus()
                return@setOnClickListener
            }
            if (confirm != password){
                vBinding.confirmPassword.error="password salah"
                vBinding.confirmPassword.requestFocus()
                vBinding.confirmPassword.clearComposingText()
                return@setOnClickListener
            }
            saveRegist(email,password)
        }
    }

    private fun saveRegist(email: String, password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "register berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}