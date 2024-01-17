package com.example.roslinki

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roslinki.databinding.ActivityWeekBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MonthChart : AppCompatActivity() {
    private lateinit var binding: ActivityWeekBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lineChart: LineChart = binding.wykres

        // Odbierz wyniki
        val results = intent.getStringArrayListExtra("results")!!

        // Przekształć dane
        val dane = przekształćListę(results)

        // Utwórz listę punktów na wykresie
        val entriesWilgotnoscGleby: ArrayList<Entry> = ArrayList()
        val entriesWilgotnoscPowietrza: ArrayList<Entry> = ArrayList()

        val dates = dane.map { it[0].toString() } // Zakładam, że daty są w pierwszym elemencie wewnętrznej listy

        for (i in dane.indices) {
            // Zakładam, że drugi element wewnętrznej listy to liczba
            if (dane[i].size > 2) {
                val wilgotnoscGleby = dane[i][3]
                val wilgotnoscPowietrza = dane[i][2]

                if (wilgotnoscGleby is Number) {
                    entriesWilgotnoscGleby.add(Entry(i.toFloat(), wilgotnoscGleby.toFloat()))
                }

                if (wilgotnoscPowietrza is Number) {
                    entriesWilgotnoscPowietrza.add(Entry(i.toFloat(), wilgotnoscPowietrza.toFloat()))
                }
            }
        }

        // Utwórz datasety
        val dataSetWilgotnoscGleby = LineDataSet(entriesWilgotnoscGleby, "Wilgotność gleby")
        dataSetWilgotnoscGleby.color = Color.BLUE
        dataSetWilgotnoscGleby.valueTextColor = Color.BLACK

        val dataSetWilgotnoscPowietrza = LineDataSet(entriesWilgotnoscPowietrza, "Wilgotność powietrza")
        dataSetWilgotnoscPowietrza.color = Color.RED
        dataSetWilgotnoscPowietrza.valueTextColor = Color.BLACK

        // Utwórz LineData z dwoma datasetami
        val lineData = LineData(dataSetWilgotnoscGleby, dataSetWilgotnoscPowietrza)

        // Ustawienie formattera dla osi X
        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = MyXAxisValueFormatter(dates)

        // Ustaw dane na wykresie
        lineChart.data = lineData

        // ... (pozostała część Twojego kodu)

        // Odświeżenie wykresu
        lineChart.invalidate()
}}