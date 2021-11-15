package com.spot.acronyms.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log


fun Context.initiateNetworkListener(onStatusChanged: (NetworkStatus)->Unit = {}){
    if(!isNetworkConnected(this@initiateNetworkListener)){
        onStatusChanged.invoke(NetworkStatus.DISCONNECTED)
    }
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val networkRequest = NetworkRequest.Builder().build()
    connectivityManager?.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.i("Tag", "active connection")
            onStatusChanged.invoke(NetworkStatus.CONNECTED)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.i("Tag", "losing active connection : ${isNetworkConnected(this@initiateNetworkListener)}")
            if(!isNetworkConnected(this@initiateNetworkListener)){
                onStatusChanged.invoke(NetworkStatus.DISCONNECTED)
            }
        }
    })
}

fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw      = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }
}

enum class NetworkStatus{
    CONNECTED,
    DISCONNECTED
}