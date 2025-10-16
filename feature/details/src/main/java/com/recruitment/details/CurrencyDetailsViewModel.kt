package com.recruitment.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recruitment.domain.model.CurrencyCode
import com.recruitment.domain.model.RateHistoryPoint
import com.recruitment.domain.usecase.GetLast14DaysHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CurrencyDetailsUiState(
    val history: List<RateHistoryPoint> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class CurrencyDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLast14DaysHistoryUseCase: GetLast14DaysHistoryUseCase
) : ViewModel() {

    val currencyCode: String = savedStateHandle.get<String>("currencyCode")!!

    private val _uiState = MutableStateFlow(CurrencyDetailsUiState())
    val uiState: StateFlow<CurrencyDetailsUiState> = _uiState.asStateFlow()

    init {
        fetchHistory()
    }

    private fun fetchHistory() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getLast14DaysHistoryUseCase(CurrencyCode(currencyCode))
                .onSuccess { history ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            history = history
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.localizedMessage ?: "An unknown error occurred"
                        )
                    }
                }
        }
    }
}
