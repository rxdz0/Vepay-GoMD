package com.example.vepay_go_md.API

import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("user/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<appRespons>


    //(https://us-central1-vepay-go.cloudfunctions.net/user/registration/token/
    @FormUrlEncoded
    @Headers("Accept: application/json")
        @POST("{id}")
        fun postToken(

        @Path ("id") id: String,
        @Field("token") token : String



        ): Call<appRespons>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("{id}")
    fun postKendaraan(

        @Path("id") id:String,
        @Field("vehicleType") vehicleType: String,
        @Field("licenseNumber") licenseNumber : String

    ): Call<appRespons>


}



