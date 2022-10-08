package com.ck.car2.data

import android.content.Context
import com.ck.car2.data.home.FakeHomeRepository
import com.ck.car2.data.home.HomeRepository

interface AppContainer {
    val homeRepository: HomeRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer  {
    override val homeRepository: HomeRepository by lazy {
        FakeHomeRepository()
    }
}