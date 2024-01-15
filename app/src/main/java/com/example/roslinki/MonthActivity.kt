package com.example.roslinki

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MonthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)

        // Odbierz wyniki
        val results = intent.getStringArrayListExtra("results")

        // Sprawdź, czy results nie jest nullem przed użyciem
        if (results != null) {
            // Dodaj pusty wiersz na początku listy
            val resultsWithHeader = mutableListOf("")
            resultsWithHeader.addAll(results)

            // Wyświetl wyniki w interfejsie użytkownika (ListView) z dostosowanym adapterem
            val listView: ListView = findViewById(R.id.listView)
            val adapter = ResultsAdapter(this, R.layout.list_item_layout, resultsWithHeader)
            listView.adapter = adapter
        } else {
            // Obsługa przypadku, gdy results są nullem
            // Możesz tutaj umieścić odpowiednią logikę obsługi błędu
        }
    }
}


class ResultsAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.list_item_layout, parent, false)

        val result = getItem(position)

        // Sprawdź, czy result nie jest nullem przed użyciem
        if (result != null) {
            val components = result.split(": ", "(", ",", ")").map { it.trim() }

            val textDate = rowView.findViewById<TextView>(R.id.textDate)
            val textTemperature = rowView.findViewById<TextView>(R.id.textTemperature)
            val textAirHumidity = rowView.findViewById<TextView>(R.id.textAirHumidity)
            val textSoilHumidity = rowView.findViewById<TextView>(R.id.textSoilHumidity)

            // Jeśli to pierwszy wiersz, ustaw nagłówki
            if (position == 0) {
                textDate.text = "Data"
                textTemperature.text = "Temperatura"
                textAirHumidity.text = "Wilgotność powietrza"
                textSoilHumidity.text = "Wilgotność gleby"
            } else {
                // Dla pozostałych wierszy, ustaw tylko wartości
                textDate.text = components.getOrElse(0) { "" }
                textTemperature.text = components.getOrElse(2) { "" }
                textAirHumidity.text = components.getOrElse(3) { "" }
                textSoilHumidity.text = components.getOrElse(4) { "" }
            }
        }

        return rowView
    }
}

