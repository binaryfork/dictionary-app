package com.bf.dict.utils

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityUtils(private val context: Context) {

    val isOnline: Boolean
        get() {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
}
