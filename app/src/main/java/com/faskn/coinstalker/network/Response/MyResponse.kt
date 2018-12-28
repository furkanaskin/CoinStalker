package com.faskn.coinstalker.network.Response

import com.faskn.coinstalker.model.Data
import com.google.gson.annotations.SerializedName

data class MyResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)