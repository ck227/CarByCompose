package com.ck.car2

import android.app.Application
import com.ck.car2.data.AppContainer
import com.ck.car2.data.DefaultAppContainer

class CarByComposeApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}