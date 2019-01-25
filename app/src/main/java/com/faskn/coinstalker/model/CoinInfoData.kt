package com.faskn.coinstalker.model

import com.google.gson.annotations.SerializedName

data class CoinInfoData(
    @SerializedName("base")
    val base: Base,
    @SerializedName("coin")
    val coin: Coin
)