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

fun obliczdni(dwuwymiarowaLista: List<List<Any>>,start:Double,end:Double): Int {
    val wilgotnoscgleby = dwuwymiarowaLista.mapNotNull { it.getOrNull(3) as? Double }
    val temperatura = dwuwymiarowaLista.mapNotNull { it.getOrNull(1) as? Double }
    val wilgotnoscpow = dwuwymiarowaLista.mapNotNull { it.getOrNull(2) as? Double }
    // Oblicz różnice między kolejnymi wartościami

    val roznice = mutableListOf<Double>()
    for (i in 1 until wilgotnoscgleby.size) {
        val poprzedniaWartosc = wilgotnoscgleby[i - 1]
        val obecnaWartosc = wilgotnoscgleby[i]
        val temp = temperatura[i]
        val wilgotnosc = wilgotnoscpow[i]
        var dodatki = 0.0;
        // Pomijaj krok, jeśli jedna z wartości wynosi 0
        if (poprzedniaWartosc == 0.0 || obecnaWartosc == 0.0) {
            continue
        }
        if (temp >= 25) {
            dodatki = temp / 40.0
        }
        if (wilgotnosc < 45) {
            dodatki = (100.0 - wilgotnosc) / 80.0
        }


        val roznica = poprzedniaWartosc - obecnaWartosc + dodatki
        if (roznica > 0 && roznica < 20 + dodatki) {
            roznice.add(roznica)
        }
    }
    var wynik = 0
    var srednia = roznice.average()
    if(srednia.isNaN() || srednia > 20 || srednia <1){
        srednia = 8.0
    }
    var startowa = start
    val koncowa = end
    while (startowa > koncowa) {
        startowa = startowa - srednia
        wynik = wynik + 1
    }


    return wynik
}


