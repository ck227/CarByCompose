package com.ck.car2.data

import com.ck.car2.data.home.DefaultHomeRepository
import com.ck.car2.data.home.HomeRepository
import com.ck.car2.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    //根据业务需求，添加多个repository
    val homeRepository: HomeRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://dog.ceo/api/breeds/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
//    .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val homeRepository: HomeRepository by lazy {
        DefaultHomeRepository(retrofitService)
    }
}

