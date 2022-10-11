package com.ck.car2.data.home

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.ck.car2.data.Result
import com.ck.car2.model.HotIcon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class FakeHomeRepository : HomeRepository {
    private val tag = "FakeHomeRepository"

    private val mutex = Mutex()
    private val bannerColors = MutableStateFlow<Map<String, Color>>(mapOf())

    override suspend fun getHotIcons(): Result<List<HotIcon>> {
        return withContext(Dispatchers.IO) {
            delay(800) // pretend we're on a slow network
            if (shouldRandomlyFail()) {
                Result.Error(IllegalStateException())
            } else {
                Log.i(tag, homeIcons.size.toString())
                Result.Success(homeIcons)
            }
        }
    }

    override fun observeBannerColor(): Flow<Map<String, Color>> = bannerColors

    override suspend fun addBannerColor(position: String, color: Color) {
        mutex.withLock {
            val map = bannerColors.value.toMutableMap()
            map[position] = color
            bannerColors.value = map.toMap()
        }
    }

    // used to drive "random" failure in a predictable pattern, making the first request always
    // succeed
    private var requestCount = 0

    /**
     * Randomly fail some loads to simulate a real network.
     *
     * This will fail deterministically every 5 requests
     */
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0

}