package com.example.vepay_go_md.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Histori(

    val username: String,
    var name: String

) : Parcelable
