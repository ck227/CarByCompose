package com.ck.car2.data.home

import com.ck.car2.model.HotIcon
import com.ck.car2.data.Result

interface HomeRepository {
    suspend fun getHotIcons(): Result<List<HotIcon>>
}