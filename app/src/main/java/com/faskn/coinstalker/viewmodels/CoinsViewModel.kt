package com.faskn.coinstalker.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.faskn.coinstalker.model.CoinHistoryData
import com.faskn.coinstalker.model.CoinInfoData
import com.faskn.coinstalker.model.Data
import com.faskn.coinstalker.network.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


class CoinsViewModel(application: Application) : AndroidViewModel(application) {


    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val tag = "CoinsViewModel"


    val connectionStatusLiveData = MutableLiveData<Boolean>()
    val coinsLiveData = MutableLiveData<Data>()
    val progressLiveData = MutableLiveData<Boolean>()
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
            val service = RetrofitFactory.makeRetrofitService()
            val coinsData = service.getCoins(base, sort, timePeriod).await()

            if (coinsData.isSuccessful) {
                val list = coinsData.body()!!.data
                list.let { coinsLiveData.postValue(list) }
            }

            Timer("getCoinsTimer", false).schedule(3000) {
                getCoins(base, sort, timePeriod)
            }
        }
    }

    fun getCoinInfo(coinID: Int, base: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val service = RetrofitFactory.makeRetrofitService()
            val coinInfoData = service.getCoinData(coinID, base).await()
            val coinData = coinInfoData.body()!!.data

            if (coinInfoData.isSuccessful) {
                coinInfoLiveData.postValue(coinData)
            }

        }
    }

    fun getCoinHistory(coinID: Int, base: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val service = RetrofitFactory.makeRetrofitService()
            val coinHistoryData = service.getCoinHistory(coinID, base).await()
            val coinHistory = coinHistoryData.body()!!.data

            if (coinHistoryData.isSuccessful) {
                coinHistoryLiveData.postValue(coinHistory)
            }
        }
    }

}