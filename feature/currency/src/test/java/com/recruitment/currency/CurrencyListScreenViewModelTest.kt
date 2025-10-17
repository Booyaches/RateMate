package com.recruitment.currency

import androidx.compose.runtime.collectAsState
import app.cash.turbine.test
import com.recruitment.domain.model.Currency
import com.recruitment.domain.model.CurrencyCode
import com.recruitment.domain.model.TableSource
import com.recruitment.domain.usecase.ObserveCurrenciesUseCase
import com.recruitment.domain.usecase.RefreshCurrenciesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class CurrencyListScreenViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private val observeCurrenciesUseCase: ObserveCurrenciesUseCase = mockk()
    private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `observeCurrencies emits currencies when called`() = runTest {
        val stream = MutableSharedFlow<List<Currency>>()

        coEvery { observeCurrenciesUseCase.invoke() } returns stream
        coEvery { refreshCurrenciesUseCase.invoke(false) } returns Result.success(Unit)

        val viewModel =
            CurrencyListScreenViewModel(observeCurrenciesUseCase, refreshCurrenciesUseCase)

        val valueToEmit = Currency(
            CurrencyCode("PLN"),
            name = "złoty",
            currentMid = 0.234,
            table = TableSource("A"),
            effectiveDate = LocalDate.now()
        )

        viewModel.uiState.test {
            awaitItem()
            advanceUntilIdle()
            awaitItem()
            stream.emit(listOf(valueToEmit))

            val updated = awaitItem()

            assertThat(updated.currencies).containsExactly(valueToEmit)
            assertThat(updated.isLoading).isFalse()

            cancelAndIgnoreRemainingEvents()
        }
    }
}