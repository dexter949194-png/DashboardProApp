package com.example.dashboard

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.OutputStream
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var logText: TextView
    private val logs = mutableListOf<String>()
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    private val scooterMacAddress = "D5:3A:41:46:2B:41" 
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    override fun onCreate(savedInstanceState: Bundle?) {connectToScooter()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        private fun connectToScooter() {
        try {
            private fun sendCommand(command: String) {
    try {
        outputStream?.write(command.toByteArray())
        outputStream?.flush()
    } catch (e: Exception) {
        Snackbar.make(logText, "Błąd wysyłania komendy!", Snackbar.LENGTH_LONG).show()
    }
}

        val adapter = BluetoothAdapter.getDefaultAdapter()
        val device: BluetoothDevice = adapter.getRemoteDevice(scooterMacAddress)

        bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
        bluetoothSocket?.connect()

        outputStream = bluetoothSocket?.outputStream

        Snackbar.make(logText, "Połączono z hulajnogą", Snackbar.LENGTH_SHORT).show()

    } catch (e: Exception) {
        Snackbar.make(logText, "Błąd połączenia!", Snackbar.LENGTH_LONG).show()
    }
}

        logText = findViewById(R.id.logText)

        setupButton(R.id.btn5, "5 km/h")
        setupButton(R.id.btn15, "15 km/h")
        setupButton(R.id.btn25, "25 km/h")
        setupButton(R.id.btn45, "45 km/h")

        findViewById<MaterialButton>(R.id.btnExport).setOnClickListener {
            exportLogs()
        }
    }

    private fun setupButton(id: Int, speed: String) {
        val btn = findViewById<MaterialButton>(id)
        btn.setOnClickListener {

            btn.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
                btn.animate().scaleX(1f).scaleY(1f).duration = 100
            }

            val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            val entry = "$time -> Wybrano $speed"
            logs.add(entry)
            logText.append("$entry\n")

            Snackbar.make(btn, "Ustawiono $speed", Snackbar.LENGTH_SHORT).show()
        }
        sendCommand(speed)

    }

    private fun exportLogs() {
        try {
            val file = File(getExternalFilesDir(null), "dashboard_logs.txt")
            val writer = FileWriter(file)
            logs.forEach { writer.append(it + "\n") }
            writer.flush()
            writer.close()

            Snackbar.make(logText, "Zapisano do: ${file.absolutePath}", Snackbar.LENGTH_LONG).show()

        } catch (e: Exception) {
            Snackbar.make(logText, "Błąd zapisu!", Snackbar.LENGTH_LONG).show()
        }
    }
}
