package com.yourpackage.designsystem.chart

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

/**
 *
 * FULLY GENERATED!
 *
 * Generic, lightweight line chart with point labels and bottom x-axis labels.
 *
 * @param items Data points of any type.
 * @param x Map item -> X value (continuous).
 * @param y Map item -> Y value (continuous).
 * @param pointColor Color for each point (defaults to [lineColor]).
 * @param segmentColor Color for each segment (a -> b). If null, [lineColor] is used.
 * @param lineColor Fallback color for line/points.
 * @param strokeWidth Line thickness in px.
 * @param showPoints Draw circular markers on points.
 * @param pointRadius Marker radius in px.
 * @param valueLabel Text shown near each point (return null/empty to skip).
 * @param xAxisLabel Text shown on the bottom for each item (e.g., dates).
 * @param valueLabelTextSizeSp Label text size in sp.
 * @param xAxisTextSizeSp X-axis label text size in sp.
 * @param xAxisLabelEvery Draw every Nth x-axis label (e.g., 2 = every second).
 * @param contentPadding Inner padding around plot area.
 * @param axisBottomPadding Extra bottom space so x labels don’t clip.
 * @param height Chart height.
 */
@Composable
fun <T> SimpleLineChart(
    items: List<T>,
    x: (T) -> Float,
    y: (T) -> Float,
    pointColor: (T) -> Color = { Color.Black },
    segmentColor: ((T, T) -> Color)? = null,
    lineColor: Color = Color(0xFF1976D2),
    modifier: Modifier = Modifier,
    strokeWidth: Float = 4f,
    showPoints: Boolean = true,
    pointRadius: Float = 4f,
    valueLabel: (T) -> String? = { null },
    xAxisLabel: (T) -> String? = { null },
    valueLabelTextSizeSp: Float = 10f,
    xAxisTextSizeSp: Float = 10f,
    xAxisLabelEvery: Int = 1,
    contentPadding: Dp = 8.dp,
    axisBottomPadding: Dp = 20.dp,
    height: Dp = 200.dp
) {
    if (items.isEmpty()) return

    val density = LocalDensity.current
    val valueTextPx = with(density) { valueLabelTextSizeSp.dp.toPx() } // a lightweight px calc
    val xTextPx = with(density) { xAxisTextSizeSp.dp.toPx() }
    val bottomPadPx = with(density) { axisBottomPadding.toPx() }

    Canvas(
        modifier = modifier
            .height(height)
            .padding(start = contentPadding, end = contentPadding, top = contentPadding, bottom = 0.dp) // bottom reserved for x labels
    ) {
        // Build arrays
        val xs = items.map(x)
        val ys = items.map(y)

        // Ranges
        val xMin = xs.minOrNull() ?: 0f
        val xMax = xs.maxOrNull() ?: 1f
        val yMin = ys.minOrNull() ?: 0f
        val yMax = ys.maxOrNull() ?: 1f

        val xRange = max(1e-6f, xMax - xMin)
        val yRange = max(1e-6f, yMax - yMin)

        // Plot area height excludes bottom labels area
        val plotHeight = max(0f, size.height - bottomPadPx)

        fun mapX(v: Float): Float = ((v - xMin) / xRange) * size.width
        fun mapY(v: Float): Float = plotHeight - ((v - yMin) / yRange) * plotHeight

        // Draw segments
        if (items.size >= 2) {
            for (i in 0 until items.lastIndex) {
                val a = items[i]
                val b = items[i + 1]
                val color = segmentColor?.invoke(a, b) ?: lineColor
                drawLine(
                    color = color,
                    start = Offset(mapX(xs[i]), mapY(ys[i])),
                    end = Offset(mapX(xs[i + 1]), mapY(ys[i + 1])),
                    strokeWidth = strokeWidth
                )
            }
        }

        // Draw points + value labels
        if (showPoints) {
            // Simple collision-safe offset for labels
            val labelYOffset = 6f + pointRadius
            for (i in items.indices) {
                val item = items[i]
                val cx = mapX(xs[i])
                val cy = mapY(ys[i])
                val pColor = pointColor(item)

                // point
                drawCircle(
                    color = pColor,
                    radius = pointRadius,
                    center = Offset(cx, cy)
                )

                // value label (above the point)
                val txt = valueLabel(item)
                if (!txt.isNullOrBlank()) {
                    drawIntoCanvas { canvas ->
                        val paint = Paint().apply {
                            isAntiAlias = true
                            color = android.graphics.Color.BLACK
                            textSize = valueTextPx
                        }
                        // Center the label horizontally above the point
                        val textWidth = paint.measureText(txt)
                        val tx = (cx - textWidth / 2f).coerceIn(0f, size.width - textWidth)
                        val ty = max(0f + valueTextPx, cy - labelYOffset)
                        canvas.nativeCanvas.drawText(txt, tx, ty, paint)
                    }
                }
            }
        }

        // Draw x-axis labels (bottom)
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                isAntiAlias = true
                color = android.graphics.Color.DKGRAY
                textSize = xTextPx
            }
            for (i in items.indices step max(1, xAxisLabelEvery)) {
                val label = xAxisLabel(items[i]) ?: continue
                val tx = mapX(xs[i])
                val tw = paint.measureText(label)
                // Place baseline near bottom with a small margin
                val bx = (tx - tw / 2f).coerceIn(0f, size.width - tw)
                val by = size.height - 4f
                canvas.nativeCanvas.drawText(label, bx, by, paint)
            }
        }
    }
}