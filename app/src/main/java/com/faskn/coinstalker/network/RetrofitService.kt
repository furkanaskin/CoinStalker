package com.faskn.coinstalker.network

import com.faskn.coinstalker.network.responses.CoinHistoryResponse
import com.faskn.coinstalker.network.responses.CoinInfoResponse
import com.faskn.coinstalker.network.responses.CoinsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("coins?&sort=price&limit=100")
    fun getCoins(@Query("base") coinBase: String): Deferred<Response<CoinsResponse>>

    @GET("coin/{coinID}?base=TRY")
    fun getCoinData(@Path("coinID") coinID: Int): Deferred<Response<CoinInfoResponse>>

    @GET("coin/{coinID}/history/24h?base=TRY")
    fun getCoinHistory(@Path("coinID") coinID: Int): Deferred<Response<CoinHistoryResponse>>

}

