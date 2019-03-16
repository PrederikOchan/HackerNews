package com.rozedfrozzy.hackernews.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkStatus(private val applicationContext: Context) {
    fun isOnline(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}