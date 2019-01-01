package com.faskn.coinstalker.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class CoinsViewModel(application: Application) : AndroidViewModel(application) {


    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val tag = "CoinsViewModel"


    val connectionStatusLiveData = MutableLiveData<Boolean>()

    fun checkConnectionStatus() {

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected) {
            connectionStatusLiveData.postValue(true)
        } else {
            connectionStatusLiveData.postValue(false)
        }

    }

}