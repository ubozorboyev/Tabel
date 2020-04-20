package com.example.tabel.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.example.tabel.utils.Constant

class NetworkChangeReciver :BroadcastReceiver(){

    companion object{
        var networkListener:NetworkListener?=null
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        val isConnected=isOnline(context)
        Constant.isNetworkConnection.value=isConnected
        networkListener?.isOnline(isConnected)
    }

    fun isOnline(context: Context?):Boolean{
        val connectivityManager=context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val netInfo=connectivityManager.activeNetworkInfo

        return netInfo!=null && netInfo.isConnected
    }


    interface NetworkListener{
        fun isOnline(isConnected:Boolean)
    }

}