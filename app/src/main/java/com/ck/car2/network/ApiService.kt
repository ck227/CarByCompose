package com.ck.car2.network

import com.ck.car2.data.mockdata.Dog
import retrofit2.http.GET

interface ApiService {
    @GET("image/random")
    suspend fun getBanners(): Dog
}

