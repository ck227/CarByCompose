package com.ck.car2.data.home

import androidx.compose.ui.graphics.Color
import com.ck.car2.model.HotIcon
import com.ck.car2.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHotIcons(): Result<List<HotIcon>>

    suspend fun addBannerColor(position: String, color: Color)

    fun observeBannerColor(): Flow<Map<String, Color>>
}