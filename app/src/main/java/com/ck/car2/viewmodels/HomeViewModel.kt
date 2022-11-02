package com.ck.car2.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ck.car2.R
import com.ck.car2.data.Result
import com.ck.car2.data.home.HomeRepository
import com.ck.car2.model.HotIcon
import com.ck.car2.utils.ErrorMessage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val searchInput: String

    //没有数据的情况
    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState

    //有数据的情况
    data class HasPosts(
        val hotIcons: List<HotIcon>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState
}

private data class HomeViewModelState(
    val hotIcons: List<HotIcon>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {
    fun toUiState(): HomeUiState = if (hotIcons == null) {
        HomeUiState.NoPosts(
            isLoading = isLoading, errorMessages = errorMessages, searchInput = searchInput
        )
    } else {
        HomeUiState.HasPosts(
            hotIcons = hotIcons,
            isLoading = isLoading,
            errorMessages = errorMessages,
            searchInput = searchInput
        )
    }
}


class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    //顶部轮播图的颜色
    private val _bannerColorMap: MutableStateFlow<Map<String, Color>> = MutableStateFlow(emptyMap())
    val bannerColorMap: StateFlow<Map<String, Color>> get() = _bannerColorMap

    //选中的分类index
    private val _selectTypePosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectTypePosition: StateFlow<Int> get() = _selectTypePosition

    val uiState = viewModelState.map { it.toUiState() }.stateIn(
        viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState()
    )

    init {
        refreshHotIcons()

        viewModelScope.launch {
            homeRepository.observeBannerColor().collect { bannerColorMap ->
                _bannerColorMap.value = bannerColorMap
            }
        }
    }

    private fun refreshHotIcons() {
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
    }

    fun addBannerColor(position: String, color: Color) {
        viewModelScope.launch {
            homeRepository.addBannerColor(position, color)
        }
    }

    fun updateTypeSelectIndex(index: Int) {
        _selectTypePosition.value = index
    }


    companion object {
        fun provideFactory(
            homeRepository: HomeRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(homeRepository) as T
            }
        }
    }

}

