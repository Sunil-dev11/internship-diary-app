package com.example.internshipdiary.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeeklyChart(
    hours: List<Float>,
    dates: List<String>,
    modifier: Modifier = Modifier
) {

    val textColor = android.graphics.Color.rgb(
        (MaterialTheme.colorScheme.onSurface.red * 255).toInt(),
        (MaterialTheme.colorScheme.onSurface.green * 255).toInt(),
        (MaterialTheme.colorScheme.onSurface.blue * 255).toInt()
    )

    val gridColor = android.graphics.Color.rgb(
        (MaterialTheme.colorScheme.outline.red * 255).toInt(),
        (MaterialTheme.colorScheme.outline.green * 255).toInt(),
        (MaterialTheme.colorScheme.outline.blue * 255).toInt()
    )

    val barColor = android.graphics.Color.rgb(
        (MaterialTheme.colorScheme.primary.red * 255).toInt(),
        (MaterialTheme.colorScheme.primary.green * 255).toInt(),
        (MaterialTheme.colorScheme.primary.blue * 255).toInt()
    )

    AndroidView(
        modifier = modifier,
        factory = { context ->

            val chart = BarChart(context)

            chart.description.isEnabled = false
            chart.legend.isEnabled = false
            chart.setDrawGridBackground(false)

            chart.axisRight.isEnabled = false

            chart.animateY(1000)

            chart

        },
        update = { chart ->

            val entries = hours.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value)
            }

            val dataSet = BarDataSet(entries, "Weekly Hours")

            dataSet.color = barColor
            dataSet.valueTextColor = textColor
            dataSet.valueTextSize = 12f

            val data = BarData(dataSet)
            data.barWidth = 0.55f

            chart.data = data

            // Convert dates to weekday labels
            val days = dates.map { getDayLabel(it) }

            chart.xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(days)
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setTextColor(textColor)
                setGridColor(gridColor)
                setTextSize(12f)
                setDrawGridLines(false)
            }

            chart.axisLeft.apply {
                setTextColor(textColor)
                setGridColor(gridColor)
                granularity = 1f
                axisMinimum = 0f
                setTextSize(12f)
            }

            chart.axisRight.isEnabled = false

            chart.invalidate()
        }
    )
}

/**
 * Converts date string (e.g., "13-3-2026") to weekday label (Fri)
 */
fun getDayLabel(date: String): String {

    return try {

        val format = SimpleDateFormat("d-M-yyyy", Locale.getDefault())
        val parsedDate = format.parse(date)

        val calendar = Calendar.getInstance()
        calendar.time = parsedDate!!

        SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.time)

    } catch (e: Exception) {
        ""
    }

}