package com.ck.car2.data

import android.content.Context
import com.ck.car2.data.home.ApiService
import com.ck.car2.data.home.FakeHomeRepository
import com.ck.car2.data.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel

interface AppContainer {
    val homeRepository: HomeRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val homeRepository: HomeRepository by lazy {
        FakeHomeRepository()
    }
}