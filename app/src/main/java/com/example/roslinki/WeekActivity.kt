package com.example.roslinki

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class WeekActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week)

        // Odbierz wyniki
        val results = intent.getStringArrayListExtra("results")

        // Inicjalizuj ListView
        val listView: ListView = findViewById(R.id.listView)
        if (results != null) {
            // Inicjalizuj adapter dla danych
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)

            // Ustaw adapter dla ListView
            listView.adapter = adapter
        }else {

        }
    }
}