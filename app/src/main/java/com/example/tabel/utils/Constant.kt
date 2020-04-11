package com.example.tabel.utils

import androidx.lifecycle.MutableLiveData

class Constant {

    companion object{
       const val BASE_URL="https://c.citynet.uz/api.php/"

        var userName=""
        var password=""
        var token:String=""
        var appId:String=""

        internal const val maxHour=20
        internal const val minHour=8

        val isNetworkConnection=MutableLiveData<Boolean>()

        internal const val networkNoConnection="Network is not connection"
    }


}