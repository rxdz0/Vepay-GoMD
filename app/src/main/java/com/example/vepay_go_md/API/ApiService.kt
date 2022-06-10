package com.example.vepay_go_md.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    interface ApiService {
        @GET("detail/{id}")
        fun getRestaurant(
            @Path("id") id: String
        ): Call<users>
    }

}