package com.faskn.coinstalker.model

import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("price")
    val price: String,
    @SerializedName("timestamp")
    val timestamp: Long
)