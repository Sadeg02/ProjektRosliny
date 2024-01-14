package com.example.roslinki

import java.security.KeyStore
import java.time.LocalDate
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


fun przekształcNaDwuwymiarowąListę(listaStringow: List<String>): List<List<Any>> {
    val dwuwymiarowaLista = mutableListOf<List<Any>>()

    for (stringDanych in listaStringow) {
        val regex = """(\d{4}-\d{2}-\d{2}): \(([\d.]+),([\d.]+),([\d.]+)\)""".toRegex()
        val matchResult = regex.find(stringDanych)

        if (matchResult != null && matchResult.groupValues.size == 5) {
            val data = LocalDate.parse(matchResult.groupValues[1])
            val liczba1 = matchResult.groupValues[2].toFloat()
            val liczba2 = matchResult.groupValues[3].toFloat()
            val liczba3 = matchResult.groupValues[4].toFloat()

            val wiersz = listOf<Any>(data, liczba1, liczba2, liczba3)
            dwuwymiarowaLista.add(wiersz)
        } else {
            println("Błąd w formacie danych dla wpisu: $stringDanych")
        }
    }

    return dwuwymiarowaLista
}


