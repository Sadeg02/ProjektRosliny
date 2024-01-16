package com.example.roslinki

import java.time.LocalDate

fun przekształcNaDwuwymiarowąListę(listaStringow: List<String>): List<List<Any>> {
    val dwuwymiarowaLista = mutableListOf<List<Any>>()

    for (stringDanych in listaStringow) {
        val regex = """(\d{4}-\d{2}-\d{2}): \(([\d.]+),([\d.]+),([\d.]+)\)""".toRegex()
        val matchResult = regex.find(stringDanych)

        if (matchResult != null && matchResult.groupValues.size == 5) {
            try {
                val data = LocalDate.parse(matchResult.groupValues[1])
                val liczby = matchResult.groupValues.subList(2, 5).map { it.toDouble() }

                val wiersz = listOf<Any>(data, *liczby.toTypedArray())
                dwuwymiarowaLista.add(wiersz)
            } catch (e: Exception) {
                println("Błąd podczas przetwarzania wpisu: $stringDanych")
                e.printStackTrace()
            }
        } else {
            println("Błąd w formacie danych dla wpisu: $stringDanych")
        }
    }

    return dwuwymiarowaLista
}

fun przekształćListę(sfabrykowaneDane: List<String>): List<List<Any>> {
    return sfabrykowaneDane.map { wpis ->
        val (data, wartości) = wpis.split(": ")
        val liczby = wartości.removeSurrounding("(", ")").split(", ").map { if (it == "None") null else it.toDoubleOrNull() ?: 0.0 }
        listOf(data, liczby.getOrNull(0) ?: 0.0, liczby.getOrNull(1) ?: 0.0, liczby.getOrNull(2) ?: 0.0)
    }
}