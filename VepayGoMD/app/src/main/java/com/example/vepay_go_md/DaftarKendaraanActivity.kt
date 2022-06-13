package com.example.vepay_go_md

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.vepay_go_md.API.APIConfig
import com.example.vepay_go_md.API.APIConfig2
import com.example.vepay_go_md.API.appRespons
import com.example.vepay_go_md.databinding.ActivityDaftarKendaraanBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private lateinit var vBinding: ActivityDaftarKendaraanBinding
private lateinit var auth: FirebaseAuth
private lateinit var uID: String
class DaftarKendaraanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        vBinding= ActivityDaftarKendaraanBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(vBinding.root)
        auth= FirebaseAuth.getInstance()

        vBinding.btnKendaraan.setOnClickListener {

            val plat= vBinding.etPlate.text.toString()
            val kendaraan= vBinding.etVehicle.text.toString()

            if(plat.isEmpty()){
                vBinding.etPlate.error="plat wajib diisi"
                vBinding.etPlate.requestFocus()
                return@setOnClickListener
            }

            if (kendaraan.isEmpty()){
                vBinding.etVehicle.error="Kendaraan wajib diisi"
                vBinding.etVehicle.requestFocus()
                return@setOnClickListener
            }


            postVehicle(kendaraan,plat)
            val intent= Intent(this, DashboardActivity::class.java)
            startActivity(intent)


        }

    }
}

private fun postVehicle(vehicle:String, plate: String) {
    uID = auth!!.currentUser!!.uid
    val client = APIConfig2.getService().postKendaraan(uID,vehicle ,plate)
    client.enqueue(object : Callback<appRespons> {
        override fun onResponse(
            call: Call<appRespons>,
            response: Response<appRespons>
        ) {
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {

            } else {
                Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<appRespons>, t: Throwable) {

            Log.e(ContentValues.TAG, "onFailure: ${t.message}")
        }

    })
}

