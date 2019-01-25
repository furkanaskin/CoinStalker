package com.faskn.coinstalker.network.responses

import com.faskn.coinstalker.model.Data
import com.google.gson.annotations.SerializedName

data class CoinsResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)