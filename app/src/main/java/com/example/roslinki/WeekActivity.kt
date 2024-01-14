package com.example.roslinki

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.roslinki.databinding.ActivityWeekBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class WeekActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeekBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lineChart: LineChart = binding.wykres

        // Odbierz wyniki
        val results = intent.getStringArrayListExtra("results")!!

        // Przekształć dane
        val dane = przekształcNaDwuwymiarowąListę(results)

        // Utwórz listę punktów na wykresie
        val entries: ArrayList<Entry> = ArrayList()
        for (i in dane.indices) {
            // Zakładam, że drugi element wewnętrznej listy to liczba
            if (dane[i].size > 1) {
                val value = dane[i][1]
                if (value is Number) {
                    entries.add(Entry(i.toFloat(), value.toFloat()))
                }
            }
        }

        // Utwórz dataset
        val dataSet = LineDataSet(entries, "Zmiana wartości")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

        // Utwórz LineData
        val lineData = LineData(dataSet)

        // Ustaw dane na wykresie
        lineChart.data = lineData

        // Ustawienie opisu dla wykresu
        val description = Description()
        description.text = "Zmiana wartości w czasie"
        lineChart.description = description

        // Ustawienie osi X na podstawie dat
        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(getDatesFromData(dane))
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)

        // Odświeżenie wykresu
        lineChart.invalidate()
    }

    private fun getDatesFromData(data: List<List<Any>>): List<String> {
        return data.map { it[0].toString() }
    }
}
