package com.faskn.coinstalker.viewmodels

import android.app.Application
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.faskn.coinstalker.di.createConnectionManager
import com.faskn.coinstalker.di.makeRetrofitService
import com.faskn.coinstalker.model.CoinHistoryData
import com.faskn.coinstalker.model.CoinInfoData
import com.faskn.coinstalker.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


class CoinsViewModel(application: Application) : AndroidViewModel(application) {

    private val connectivityManager = createConnectionManager(application, application)
    private val service = makeRetrofitService() // From RemoteDataModule


    val connectionStatusLiveData = MutableLiveData<Boolean>()
    val coinsLiveData = MutableLiveData<Data>()
    val coinInfoLiveData = MutableLiveData<CoinInfoData>()
    val coinHistoryLiveData = MutableLiveData<CoinHistoryData>()


    fun checkConnectionStatus() {

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected) {
            connectionStatusLiveData.postValue(true)
        } else {
            connectionStatusLiveData.postValue(false)
        }

    }

    fun getCoins(base: String?, sort: String?, timePeriod: String?) {
        GlobalScope.launch(Dispatchers.Main) {

            val coinsData = service.getCoins(base, sort, timePeriod).await()

            if (coinsData.isSuccessful) {
                val list = coinsData.body()!!.data
                list.let {
                    coinsLiveData.postValue(list)

                }
            }

            Timer("getCoinsTimer", false).schedule(3000) {
                getCoins(base, sort, timePeriod)
            }
        }
    }

    fun getCoinInfo(coinID: Int, base: String?) {
        GlobalScope.launch(Dispatchers.Main) {

            val coinInfoData = service.getCoinData(coinID, base).await()
            val coinData = coinInfoData.body()!!.data

            if (coinInfoData.isSuccessful) {
                coinInfoLiveData.postValue(coinData)
            }

        }
    }

    fun getCoinHistory(coinID: Int, base: String?) {
        GlobalScope.launch(Dispatchers.Main) {

            val coinHistoryData = service.getCoinHistory(coinID, base).await()
            val coinHistory = coinHistoryData.body()!!.data

            if (coinHistoryData.isSuccessful) {
                coinHistoryLiveData.postValue(coinHistory)
            }
        }
    }

}