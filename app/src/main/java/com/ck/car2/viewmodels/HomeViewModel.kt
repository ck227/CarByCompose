package com.ck.car2.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ck.car2.R
import com.ck.car2.data.Result
import com.ck.car2.data.home.HomeRepository
import com.ck.car2.data.mockdata.Dog
import com.ck.car2.model.HotIcon
import com.ck.car2.network.Api
import com.ck.car2.utils.ErrorMessage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

sealed interface HomeUiState {
    data class Success(val dog: Dog) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}


class HomeViewModel : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    //顶部轮播图的颜色
    private val _bannerColorMap: MutableStateFlow<Map<String, Color>> = MutableStateFlow(emptyMap())
    val bannerColorMap: StateFlow<Map<String, Color>> get() = _bannerColorMap

    //选中的分类index
    private val _selectTypePosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectTypePosition: StateFlow<Int> get() = _selectTypePosition


    init {
        getBanners()

//        viewModelScope.launch {
//            homeRepository.observeBannerColor().collect { bannerColorMap ->
//                _bannerColorMap.value = bannerColorMap
//            }
//        }
    }


    private fun getBanners() {
        viewModelScope.launch {
            homeUiState = try {
                val listResult = Api.retrofitService.getBanners()
                HomeUiState.Success(listResult)
            } catch (e: Exception) {
                e.message?.let { Log.e("ck", it) }
                HomeUiState.Error
            }
        }
    }

    /*private fun refreshHotIcons() {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = homeRepository.getHotIcons()
            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(hotIcons = result.data, isLoading = false)
                    is Result.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }*/

    /*fun addBannerColor(position: String, color: Color) {
        viewModelScope.launch {
            homeRepository.addBannerColor(position, color)
        }
    }

    fun updateTypeSelectIndex(index: Int) {
        _selectTypePosition.value = index
    }*/


    /*companion object {
        fun provideFactory(
            homeRepository: HomeRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(homeRepository) as T
            }
        }
    }*/

}

