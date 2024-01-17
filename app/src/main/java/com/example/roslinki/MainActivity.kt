package com.example.roslinki

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*
import java.util.Calendar
import java.util.Locale


interface AsyncTaskCompleteListener {
    fun onTaskComplete(results: List<String>)
}
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pobierz referencję do przycisku za pomocą jego ID
        val weekButton: Button = findViewById(R.id.week_button)
        val monthButton: Button = findViewById(R.id.month_button)
        val monthChart: Button = findViewById(R.id.month_chart_button)

        // Pobierz referencję do TextView
        val textView: TextView = findViewById(R.id.cos)
        val flowerImageView: ImageView = findViewById(R.id.flowerImage)
        val textView2: TextView = findViewById(R.id.textView2)
        val textView3: TextView = findViewById(R.id.textView3)
        val textViewDni: TextView = findViewById(R.id.dni)

        // Pobierz najnowsze dane z serwera
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Calendar.getInstance().time
        val endDate = dateFormatter.format(currentDate)

        // Uzyskaj datę początkową (7 dni wcześniej)
        val startDateCalendar = Calendar.getInstance()
        val startDate = dateFormatter.format(startDateCalendar.time)

        // Uruchom zadanie klienta gniazda w tle z określonym zakresem daty
        val socketClientTask = SocketClientTask(startDate, endDate, object : AsyncTaskCompleteListener {
            override fun onTaskComplete(results: List<String>) {
                // Przyjmij, że dane zawsze są w formacie "yyyy-MM-dd: (val1, val2, val3)"
                if (results.isNotEmpty()) {
                    val lastData = results.last()
                    val values = lastData.substringAfter(": (").substringBefore(")").split(", ")
                    val val3 = values[2].toDoubleOrNull()
                    val val1 = values[0].toDoubleOrNull()
                    val val2 = values[1].toDoubleOrNull()
                    if (val3 != null) {
                        val textToShow = if (val3 < 35) {

                            flowerImageView.setImageResource(R.drawable.sadflower)
                            "PODLEJ KWIATEK!!!"
                        } else if (val3 > 70) {
                            flowerImageView.setImageResource(R.drawable.waterflower)
                            "PRZELAŁEŚ KWIATKA"

                        } else {

                            flowerImageView.setImageResource(R.drawable.happyflower)
                            "KWIATEK DOBRZE NAWODNIONY"
                        }

                        runOnUiThread {
                            textView.text = textToShow
                        }
                    }
                    val temperaturaText: String = "Aktualna temperatura:  $val1"
                    textView2.text = temperaturaText
                    val powietrzeText: String = "Aktualna wilgotność powietrza: $val2"
                    textView3.text = powietrzeText
                }
            }

        })
        socketClientTask.execute()/*

        val sfabrykowaneDane = listOf(
            "2024-01-20: (23.6, 39.4, 26S)"
        )
        if (sfabrykowaneDane.isNotEmpty()) {
            val lastData = sfabrykowaneDane.last()
            val values = lastData.substringAfter(": (").substringBefore(")").split(", ")
            val val3 = values[2].toDoubleOrNull()
            val val2 = values[1].toDoubleOrNull()
            val val1 = values[0].toDoubleOrNull()

            if (val3 != null) {
                val textToShow = if (val3 < 50) {
                    flowerImageView.setImageResource(R.drawable.sadflower)
                    "PODLEJ KWIATEK!!!"

                } else if (val3 > 80) {
                    "PRZELAŁEŚ KWIATKA"
                    flowerImageView.setImageResource(R.drawable.waterflower)
                } else {
                    "KWIATEK DOBRZE NAWODNIONY"
                    flowerImageView.setImageResource(R.drawable.happyflower)
                }

                runOnUiThread {
                    textView.text = textToShow
                }
            }
            val temperaturaText: String = "Aktualna temperatura:  $val1"
            textView2.text = temperaturaText
            val powietrzeText: String = "Aktualna wilgotność powietrza: $val2"
            textView3.text = powietrzeText
        }*/




        //oblicz ile dni-----------------------------------------------
        val dateFormatterr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDateforday = Calendar.getInstance().time
        val endDateforday = dateFormatterr.format(currentDateforday)

        val startDateCalendarforday = Calendar.getInstance()
        startDateCalendarforday.add(Calendar.DAY_OF_MONTH, -7)
        val startDateforday = dateFormatterr.format(startDateCalendar.time)

        val socketClientTask2 = SocketClientTask(startDateforday, endDateforday, object : AsyncTaskCompleteListener {
            override fun onTaskComplete(results: List<String>) {

                val dane: List<List<Any>> = przekształćListę(results)
                val start: Double = dane[0][3]
                val end = 30.0
                val obliczone = obliczdni(dane,start,end)
                val dniText: String = "Podlanie za około: $obliczone"
                textViewDni.text = dniText

            }
        })
        socketClientTask2.execute()



        // przycisk dla tygodnia
        weekButton.setOnClickListener {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            // Uzyskaj dzisiejszą datę
            val currentDate = Calendar.getInstance().time
            val endDate = dateFormatter.format(currentDate)

            // Uzyskaj datę początkową (7 dni wcześniej)
            val startDateCalendar = Calendar.getInstance()
            startDateCalendar.add(Calendar.DAY_OF_MONTH, -6)
            val startDate = dateFormatter.format(startDateCalendar.time)

            /*val sfabrykowaneDane = listOf(
                "2024-01-08: (None, None, None)",
                "2024-01-09: (85.2, 45.3, 72.6)",
                "2024-01-10: (82.3, 52.8, 59.1)",
                "2024-01-11: (87.8, 39.7, 66.7)",
                "2024-01-12: (88.6, 60.4, 71.4)",
                "2024-01-13: (84.7, 33.6, 58.2)",
                "2024-01-14: (86.4, 46.9, 67.8)",
                "2024-01-15: (None, None, None)",
                "2024-01-16: (85.9, 63.1, 73.7)",
                "2024-01-17: (89.5, 35.9, 56.4)",
                "2024-01-18: (84.2, 43.7, 68.3)",
                "2024-01-19: (86.9, 50.6, 61.8)",
                "2024-01-20: (88.0, 37.4, 70.5)"
            )
            val aweek = Intent(this@MainActivity, WeekActivity::class.java)
            aweek.putStringArrayListExtra("results", ArrayList(sfabrykowaneDane))
            startActivity(aweek)*/

            // Uruchom zadanie klienta gniazda w tle z określonym zakresem dat
            val socketClientTask = SocketClientTask(startDate, endDate, object : AsyncTaskCompleteListener {
                override fun onTaskComplete(results: List<String>) {
                    val aweek = Intent(this@MainActivity, WeekActivity::class.java)
                    aweek.putStringArrayListExtra("results", ArrayList(results))
                    startActivity(aweek)
                }
            })
            socketClientTask.execute()


        }

        //przycisk dla miesiac
        monthButton.setOnClickListener {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // Uzyskaj dzisiejszą datę
            val currentDate = Calendar.getInstance().time
            val endDate = dateFormatter.format(currentDate)

            // Uzyskaj pierwszy dzień aktualnego miesiąca
            val startDateCalendar = Calendar.getInstance()
            startDateCalendar.add(Calendar.DAY_OF_MONTH, -30)
            val startDate = dateFormatter.format(startDateCalendar.time)

            // Uruchom zadanie klienta gniazda w tle z określonym zakresem dat
            val socketClientTask = SocketClientTask(startDate, endDate, object : AsyncTaskCompleteListener {
                override fun onTaskComplete(results: List<String>) {
                    val amonth = Intent(this@MainActivity, MonthActivity::class.java)
                    amonth.putStringArrayListExtra("results", ArrayList(results))
                    startActivity(amonth)
                }
            })
            socketClientTask.execute()
            /*val sfabrykowaneDane = listOf(
                "2024-01-09: (85.2, 45.3, 72.6)",
                "2024-01-10: (82.3, 52.8, 59.1)",
                "2024-01-11: (87.8, 39.7, 66.7)",
                "2024-01-12: (88.6, 60.4, 71.4)",
                "2024-01-13: (84.7, 33.6, 58.2)",
                "2024-01-14: (86.4, 46.9, 67.8)",
                "2024-01-15: (87.3, 54.2, 60.9)",
                "2024-01-16: (85.9, 63.1, 73.7)",
                "2024-01-17: (89.5, 35.9, 56.4)",
                "2024-01-18: (84.2, 43.7, 68.3)",
                "2024-01-19: (86.9, 50.6, 61.8)",
                "2024-01-20: (88.0, 37.4, 70.5)"
            )
            val amonth = Intent(this@MainActivity, MonthActivity::class.java)
            amonth.putStringArrayListExtra("results", ArrayList(sfabrykowaneDane))
            startActivity(amonth)*/
        }

        monthChart.setOnClickListener {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // Uzyskaj dzisiejszą datę
            val currentDate = Calendar.getInstance().time
            val endDate = dateFormatter.format(currentDate)

            // Uzyskaj pierwszy dzień aktualnego miesiąca
            val startDateCalendar = Calendar.getInstance()
            startDateCalendar.add(Calendar.DAY_OF_MONTH, -30)
            val startDate = dateFormatter.format(startDateCalendar.time)

            // Uruchom zadanie klienta gniazda w tle z określonym zakresem dat
            val socketClientTask = SocketClientTask(startDate, endDate, object : AsyncTaskCompleteListener {
                override fun onTaskComplete(results: List<String>) {
                    val monthChart = Intent(this@MainActivity, MonthChart::class.java)
                    monthChart.putStringArrayListExtra("results", ArrayList(results))
                    startActivity(monthChart)
                }
            })
            socketClientTask.execute()
            /*val sfabrykowaneDane = listOf(
                "2024-01-09: (85.2, 45.3, 72.6)",
                "2024-01-10: (82.3, 52.8, 59.1)",
                "2024-01-11: (87.8, 39.7, 66.7)",
                "2024-01-12: (88.6, 60.4, 71.4)",
                "2024-01-13: (84.7, 33.6, 58.2)",
                "2024-01-14: (86.4, 46.9, 67.8)",
                "2024-01-15: (87.3, 54.2, 60.9)",
                "2024-01-16: (85.9, 63.1, 73.7)",
                "2024-01-17: (89.5, 35.9, 56.4)",
                "2024-01-18: (84.2, 43.7, 68.3)",
                "2024-01-19: (86.9, 50.6, 61.8)",
                "2024-01-20: (88.0, 37.4, 70.5)"
            )
            val monthChart = Intent(this@MainActivity, MonthChart::class.java)
            monthChart.putStringArrayListExtra("results", ArrayList(sfabrykowaneDane))
            startActivity(monthChart)*/
        }
    }
}

class SocketClientTask(private val startDateStr: String, private val endDateStr: String, private val listener: AsyncTaskCompleteListener) : AsyncTask<Void, Void, List<String>>() {
    override fun doInBackground(vararg params: Void?): List<String> {
        val serverAddress = "192.168.1.49"
        val serverPort = 8081

        val resultList = mutableListOf<String>()

        try {
            val startDateTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(startDateStr)
            val endDateTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(endDateStr)

            val daysDifference = ((endDateTime.time - startDateTime.time) / (24 * 60 * 60 * 1000)).toInt() + 2

            val socket = Socket(serverAddress, serverPort)
            val outputStream: OutputStream = socket.getOutputStream()

            outputStream.write(startDateStr.toByteArray())
            outputStream.flush()

            outputStream.write(endDateStr.toByteArray())
            outputStream.flush()

            val inputStream = BufferedReader(InputStreamReader(socket.getInputStream()))

            var i = 1
            while (i < daysDifference) {
                val data = inputStream.readLine()
                if (data.isNullOrEmpty()) {
                    break
                }

                resultList.add(data)

                outputStream.write("gotowe".toByteArray())
                outputStream.flush()

                i++
            }

            socket.close()
            return resultList

        } catch (e: Exception) {
            e.printStackTrace()
            return listOf("Error: ${e.message}")
        }
    }

    override fun onPostExecute(result: List<String>) {
        // Tutaj możesz obsługiwać wynik po zakończeniu zadania
        listener.onTaskComplete(result)
    }
}