package com.faskn.coinstalker.network.responses

import com.faskn.coinstalker.model.CoinInfoData
import com.google.gson.annotations.SerializedName

data class CoinInfoResponse(
    @SerializedName("data")
    val `data`: CoinInfoData,
    @SerializedName("status")
    val status: String
)