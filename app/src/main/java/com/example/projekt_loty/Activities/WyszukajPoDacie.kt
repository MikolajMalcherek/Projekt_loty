package com.example.projekt_loty.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.DatePicker
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale
import android.view.inputmethod.InputMethodManager
import com.example.projekt_loty.R


class WyszukajPoDacie : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wyszukajpodacie)

        var startDate: String = ""
        var endDate: String = ""

        val datawylotuCalendarButton: Button = findViewById(R.id.datawylotuCalendarButton)
        val datapowrotuCalendarButton: Button = findViewById(R.id.datapowrotuCalendarButton)
        val startDatePicker: DatePicker = findViewById(R.id.startDatePicker)
        val endDatePicker: DatePicker = findViewById(R.id.endDatePicker)
        val searchbutton: Button = findViewById(R.id.wyszukajButton)
        val miastowylotu: EditText = findViewById(R.id.miastoEditText)
        val datawylotu: TextView = findViewById(R.id.datawylotuTextView)
        val datapowrotu: TextView = findViewById(R.id.datapowrotuTextView)
        // mainContainer to dodatek, dzięki któremu po wciśnięciu pustej przestrzeni
        // w aplikacji kalendarze się zamkną
        val mainContainer: View = findViewById(R.id.mainContainer)

        mainContainer.setOnClickListener {
            startDatePicker.visibility = View.GONE
            endDatePicker.visibility = View.GONE
            // Ukrycie klawiatury po kliknięciu pustej przestrzeni na ekranie
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }

        datawylotuCalendarButton.setOnClickListener {
            endDatePicker.visibility = View.GONE
            startDatePicker.visibility = View.VISIBLE
        }

        datapowrotuCalendarButton.setOnClickListener {
            startDatePicker.visibility = View.GONE
            endDatePicker.visibility = View.VISIBLE
        }

        startDatePicker.init(startDatePicker.year, startDatePicker.month, startDatePicker.dayOfMonth) { _, year, monthOfYear, dayOfMonth ->
            startDate = formatDate(year, monthOfYear, dayOfMonth)
            datawylotu.text = "Wybrana data początkowa: $startDate"
            datawylotu.visibility = View.VISIBLE
        }

        endDatePicker.init(endDatePicker.year, endDatePicker.month, endDatePicker.dayOfMonth) { _, year, monthOfYear, dayOfMonth ->
            endDate = formatDate(year, monthOfYear, dayOfMonth)
            datapowrotu.text = "Wybrana data końcowa: $endDate"
            datapowrotu.visibility = View.VISIBLE
        }

        searchbutton.setOnClickListener(OnClickListener {
            // Przejście do widoku wyświetlającego listę lotów miedzy datami
            val intent = Intent(this, ListaMiedzyDatami::class.java)
            intent.putExtra("datawylotu", startDate)
            intent.putExtra("datapowrotu", endDate)
            startActivity(intent)
        })

    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}