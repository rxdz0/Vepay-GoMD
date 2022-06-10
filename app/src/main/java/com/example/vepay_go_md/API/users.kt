package com.example.vepay_go_md.API

import com.google.gson.annotations.SerializedName

data class users(

@field:SerializedName("fullname")
val fullname:String,

@field:SerializedName("plate")
val plate:String,

@field:SerializedName("viachel")
val viachel:String


)
