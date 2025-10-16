package com.recruitment.currency.list

import androidx.lifecycle.ViewModel
import com.recruitment.domain.model.Currency
import com.recruitment.domain.repository.RatesRepository
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
    private val ratesRepository: RatesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrencyListUiState(isLoading = true))
    val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()

    init {
//        observeRates()
//        refreshRates()
    }

//    private fun observeRates() {
//        viewModelScope.launch {
//            ratesRepository.observeLatestRates().collect { currencies ->
//                _uiState.update { it.copy(currencies = currencies) }
//            }
//        }
//    }
//
//    private fun refreshRates(force: Boolean = false) {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true) }
//            ratesRepository.refreshLatestRates(force)
//                .onSuccess {
//                    _uiState.update { it.copy(isLoading = false) }
//                }
//                .onFailure { exception ->
//                    // TODO: Handle error case, e.g., show a snackbar
//                    _uiState.update { it.copy(isLoading = false) }
//                }
//        }
//    }
}
