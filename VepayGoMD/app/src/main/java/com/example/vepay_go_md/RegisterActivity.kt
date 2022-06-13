package com.example.vepay_go_md

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.vepay_go_md.API.APIConfig
import com.example.vepay_go_md.API.appRespons
import com.example.vepay_go_md.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {
    private lateinit var vBinding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var uID: String
    private lateinit var store: FirebaseFirestore

    /*companion object{
        const val USER_ID = "PzjJM7ArZudOZGCo3dUV9QhjSMo1"
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        vBinding=ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(vBinding.root)
        auth=FirebaseAuth.getInstance()
        store=FirebaseFirestore.getInstance()


        vBinding.btnRegis.setOnClickListener { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            val fullname = vBinding.fullname.text.toString()
            val email = vBinding.email.text.toString().trim()
            //val pin = vBinding.pin
            val password = vBinding.password.text.toString().trim()
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
            /*if (pin.text.toString().trim().isEmpty() ){
                vBinding.pin.error="pin tidak boleh kosong"
                vBinding.pin.requestFocus()
                return@setOnClickListener
            }
            if (pin.text.toString().trim().length != 6){
                vBinding.pin.error="isi dengan 6 angka"
                vBinding.pin.requestFocus()
                return@setOnClickListener
            }*/
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
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        Toast.makeText(this, "register berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)

                    }else{
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                    uID= auth!!.currentUser!!.uid
                    val documentReference = store!!.collection("users").document(
                        uID!!
                    )
                    val user: MutableMap<String, Any> = HashMap()
                    user["email"] = email
                    documentReference.set(user)

                    FirebaseMessaging.getInstance().token.addOnCompleteListener(
                        OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                                return@OnCompleteListener
                            }

                            // Get new FCM registration token
                            val token = task.result

                            // Log and toast

                            val msg = getString(R.string.msg_token_fmt, token)
                            Log.d(ContentValues.TAG, msg)
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                            //postToken(msg)
                            postToken(msg)




                        })


        }


    }



    }

    private fun postToken(msg: String) {
        uID= auth!!.currentUser!!.uid
        val client = APIConfig.getApiService().postToken(uID, msg )
        client.enqueue(object : Callback<appRespons> {
            override fun onResponse(
                call: Call<appRespons>,
                response: Response<appRespons>
            ){
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<appRespons>, t: Throwable) {

                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

    }


}