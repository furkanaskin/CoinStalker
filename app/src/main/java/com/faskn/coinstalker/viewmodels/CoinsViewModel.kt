package com.faskn.coinstalker.viewmodels

import android.app.Application
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.faskn.coinstalker.di.createConnectionManager
import com.faskn.coinstalker.di.makeRetrofitService
import com.faskn.coinstalker.model.CoinHistoryData
import com.faskn.coinstalker.model.CoinInfoData
import com.faskn.coinstalker.model.Data
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class CoinsViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val connectivityManager =
        createConnectionManager(application, application) // From ConnectionModule
    private val service = makeRetrofitService() // From RemoteDataModule

    val connectionStatusLiveData = MutableLiveData<Boolean>()
    val coinsLiveData = MutableLiveData<Data>()
    val coinInfoLiveData = MutableLiveData<CoinInfoData>()
    val coinHistoryLiveData = MutableLiveData<CoinHistoryData>()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun checkConnectionStatus() {
        launch {
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

            if (activeNetwork != null && activeNetwork.isConnected) {
                connectionStatusLiveData.postValue(true)
            } else {
                connectionStatusLiveData.postValue(false)
            }

            delay(10000)
            checkConnectionStatus()
        }

    }

    fun getCoins(base: String?, sort: String?, timePeriod: String?) {

        launch {
            try {
                val coinsData = service.getCoins(base, sort, timePeriod).await()

                if (coinsData.isSuccessful) {
                    connectionStatusLiveData.postValue(true)
                    val list = coinsData.body()!!.data
                    list.let {
                        coinsLiveData.postValue(list)
                    }
                }

            } catch (e: Exception) {
                Log.v("getCoins", "Request Failed.")
                connectionStatusLiveData.postValue(false)
                delay(3000) // For safety.
                getCoins(base, sort, timePeriod)
            }
        }
    }

    fun getCoinInfo(coinID: Int, base: String?) {

        launch {
            try {
                val coinInfoData = service.getCoinData(coinID, base).await()
                val coinData = coinInfoData.body()!!.data

                if (coinInfoData.isSuccessful) {
                    connectionStatusLiveData.postValue(true)
                    coinInfoLiveData.postValue(coinData)
                }

            } catch (e: Exception) {
                Log.v("getCoinInfo", "Request Failed.")
                delay(3000) // For safety.
                connectionStatusLiveData.postValue(false)

            }
        }
    }

    fun getCoinHistory(coinID: Int, base: String?) {

        launch {

            try {
                val coinHistoryData = service.getCoinHistory(coinID, base).await()
                val coinHistory = coinHistoryData.body()!!.data

                if (coinHistoryData.isSuccessful) {
                    connectionStatusLiveData.postValue(true)
                    coinHistoryLiveData.postValue(coinHistory)
                }
            } catch (e: Exception) {
                Log.v("getCoinHistory", "Request Failed.")
                connectionStatusLiveData.postValue(false)

                delay(3000) // For safety.
                getCoinHistory(coinID, base)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}