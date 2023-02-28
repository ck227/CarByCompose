package com.ck.car2.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ck.car2.data.mockdata.Dog
import com.ck.car2.network.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data class Success(val dog: Dog) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}


class HomeViewModel : ViewModel() {

    //这个地方需要的是屏幕状态
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    //顶部轮播图的颜色
    private val _bannerColorMap: MutableStateFlow<Map<String, Color>> = MutableStateFlow(emptyMap())
    val bannerColorMap: StateFlow<Map<String, Color>> get() = _bannerColorMap

    //选中的分类index
//    private val _selectTypePosition: MutableStateFlow<Int> = MutableStateFlow(0)
//    val selectTypePosition: StateFlow<Int> get() = _selectTypePosition

    init {
        getBanners()
    }

    private fun getBanners() {
        viewModelScope.launch {
            homeUiState = try {
                val listResult = Api.retrofitService.getBanners()
                HomeUiState.Success(listResult)
            } catch (e: Exception) {
                HomeUiState.Error
            }
        }
    }

    fun addBannerColor(position: String, color: Color) {
        viewModelScope.launch {
            val map = _bannerColorMap.value.toMutableMap()
            map[position] = color
            _bannerColorMap.value = map.toMap()
        }
    }


    /*
    fun updateTypeSelectIndex(index: Int) {
        _selectTypePosition.value = index
    }*/


}

