package com.example.ej3alarmas

import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val timeFormat = SimpleDateFormat("hh:mm")
    override fun onCreate(savedInstanceState: Bundle?) {

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.SET_ALARM) == PackageManager.PERMISSION_GRANTED){
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            btnAlarma.setOnClickListener{
                getTime()
            }
            btnMostrar.setOnClickListener{
                showAlarms()
            }
        }
    }

    private fun getTime() {
        val now = Calendar.getInstance()
        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)
            val tiempo = timeFormat.format(selectedTime.time)
            val tiempoCortado = tiempo.split(":")
            val hora: Int = tiempoCortado[0].toInt()
            var min: Int = tiempoCortado[1].toInt()
            createAlarm("alarma de prueba", hora, min)
        },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
        timePicker.show()
    }

    fun createAlarm(message: String, hour: Int, minutes: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        }
        startActivity(intent)
    }

    fun showAlarms() {
        val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS).apply {}
        startActivity(intent)
    }

}