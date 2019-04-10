package com.bezzo.utama

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.orhanobut.hawk.Hawk

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidNetworking.initialize(this)
        Hawk.init(this).build()
    }
}