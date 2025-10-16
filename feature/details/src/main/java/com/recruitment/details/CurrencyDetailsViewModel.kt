package com.recruitment.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.recruitment.domain.model.Currency
import com.recruitment.domain.usecase.GetLast14DaysHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class CurrencyDetailsUiState(
    val currency: Currency? = null,
    val isLoading: Boolean = false,
)

@HiltViewModel
class CurrencyDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getLast14DaysHistoryUseCase: GetLast14DaysHistoryUseCase
) : ViewModel() {

    val currencyCode: String = savedStateHandle.get<String>("currencyCode")!!

}
