package com.faskn.coinstalker.model

import com.google.gson.annotations.SerializedName

data class AllTimeHigh(
    @SerializedName("price")
    val price: String,
    @SerializedName("timestamp")
    val timestamp: Number
)