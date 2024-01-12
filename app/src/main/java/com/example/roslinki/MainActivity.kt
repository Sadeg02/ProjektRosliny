package com.example.roslinki

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pobierz referencję do przycisku za pomocą jego ID
        val connectButton: Button = findViewById(R.id.button)

        // Ustaw nasłuchiwacz kliknięcia na przycisku
        connectButton.setOnClickListener {
            // Uruchom zadanie klienta gniazda w tle
            val socketClientTask = SocketClientTask()
            socketClientTask.execute()
        }
    }
}

class SocketClientTask : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String {
        val serverAddress = "192.168.1.49"
        val serverPort = 8081

        val startDateStr = "2024-01-01"
        val endDateStr = "2024-01-12"

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
            val result = StringBuilder()

            var i = 1
            while (i < daysDifference) {
                val data = inputStream.readLine()
                if (data.isNullOrEmpty()) {
                    break
                }

                result.append(data).append("\n")

                outputStream.write("gotowe".toByteArray())
                outputStream.flush()

                i++
            }

            socket.close()
            return result.toString()

        } catch (e: Exception) {
            e.printStackTrace()
            return "Error: ${e.message}"
        }
    }

    override fun onPostExecute(result: String?) {
        // Tutaj możesz obsługiwać wynik po zakończeniu zadania
        println(result)
    }
}