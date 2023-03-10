package com.ck.car2.data.home

import com.ck.car2.data.mockdata.Dog
import com.ck.car2.network.ApiService

interface HomeRepository {
    suspend fun getBanner(): Dog
}

class DefaultHomeRepository(private val apiService: ApiService) : HomeRepository {
    override suspend fun getBanner(): Dog {
        return apiService.getBanners()
    }
}