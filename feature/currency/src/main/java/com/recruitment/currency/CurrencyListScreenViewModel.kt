package com.recruitment.currency

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recruitment.domain.model.Currency
import com.recruitment.domain.usecase.ObserveCurrenciesUseCase
import com.recruitment.domain.usecase.RefreshCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CurrencyListUiState(
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
)

@HiltViewModel
class CurrencyListScreenViewModel @Inject constructor(
    private val observeCurrenciesUseCase: ObserveCurrenciesUseCase,
    private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrencyListUiState(isLoading = true))
    val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()

    init {
        observeRates()
        refreshRates()
    }

    private fun observeRates() {
        viewModelScope.launch {
            observeCurrenciesUseCase().collect { currencies ->
                _uiState.update { it.copy(currencies = currencies) }
            }
        }
    }

    private fun refreshRates(force: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            refreshCurrenciesUseCase(force)
                .onSuccess { _uiState.update { it.copy(isLoading = false) } }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    Log.d("CurrencyListScreenViewModel", "Error refreshing rates", it)

                }
        }
    }
}
