package com.example.vepay_go_md.API

import com.google.gson.annotations.SerializedName

data class appRespons(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("token")
    val token: String

)


