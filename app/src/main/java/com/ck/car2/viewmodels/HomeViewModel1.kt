package com.ck.car2.viewmodels

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

sealed interface Home1UiState {
    val isLoading: Boolean

    data class Home1SearchIcons(
        val hotIcons: List<HotIcon>? = null,
        override val isLoading: Boolean,
    ) : Home1UiState
}

private data class Home1ViewModelState(
    val hotIcons: List<HotIcon>? = null,
    val isLoading: Boolean = false,
) {
    fun toUiState(): Home1UiState =
        Home1UiState.Home1SearchIcons(
            isLoading = isLoading,
            hotIcons = hotIcons,
        )
}


class Home1ViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(Home1ViewModelState(isLoading = true))

    val uiState = viewModelState.map { it.toUiState() }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        viewModelState.value.toUiState()
    )

    init {
        refreshHotIcons()
    }

    private fun refreshHotIcons() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = homeRepository.getHotIcons()
            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(hotIcons = result.data, isLoading = false)
                    is Result.Error -> {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
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

