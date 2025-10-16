package com.recruitment.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourpackage.designsystem.chart.SimpleLineChart

@Composable
fun CurrencyDetailsScreen(
    viewModel: CurrencyDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = viewModel.currencyCode,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
            }
        } else {
            SimpleLineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                items = uiState.history,
                x = { point -> uiState.history.indexOf(point).toFloat() },
                y = { point -> point.mid.toFloat() },
                pointColor = { point -> if (point.is10PctAwayFromCurrent) Color.Red else Color.Black },
                segmentColor = { _, _ -> Color.Black  },
                valueLabel = { point -> String.format("%.4f", point.mid) },
                xAxisLabel = { point -> point.date.toString().substring(5) },
                xAxisLabelEvery = 2,
                pointRadius = 5f,
                height = 220.dp,
                axisBottomPadding = 24.dp,
                valueLabelTextSizeSp = 11f,
                xAxisTextSizeSp = 11f,
                lineColor = MaterialTheme.colorScheme.primary,
                strokeWidth = 4f,
                showPoints = true
            )
        }
    }
}
