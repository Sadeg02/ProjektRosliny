package com.example.roslinki

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MonthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)

        // Odbierz wyniki
        val results = intent.getStringArrayListExtra("results")

        // Sprawdź, czy results nie jest nullem przed użyciem
        if (results != null) {
            // Wyświetl wyniki w interfejsie użytkownika (ListView)
            val listView: ListView = findViewById(R.id.listView)

            // Poprawiona inicjalizacja adaptera
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)
            listView.adapter = adapter
        } else {
            // Obsługa przypadku, gdy results są nullem
            // Możesz tutaj umieścić odpowiednią logikę obsługi błędu
        }
    }
}