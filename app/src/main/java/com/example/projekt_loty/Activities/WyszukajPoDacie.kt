package com.example.projekt_loty.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projekt_loty.R
import com.example.projekt_loty.ViewModels.LotyViewModal
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WyszukajPoDacie : AppCompatActivity(){

    private lateinit var viewModel: LotyViewModal
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

        // podpinamy viewModel do aktywności
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(LotyViewModal::class.java)

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
            val miasto = miastowylotu.text.toString()

            // Rozpoczęcie asynchronicznej pracy
            lifecycleScope.launch {
                // Wykonanie funkcji viewModel.ilemiast(miasto) w wątku tła
                val iloscMiast = withContext(Dispatchers.IO) {
                    viewModel.czymiastowylotu(miasto)
                }

                if(miasto != "") {
                    // Obsługa zdarzenia po naciśnięciu przycisku "Wyszukaj loty po dacie"
                    if (iloscMiast != 0) {
                        // Przejście do widoku wyświetlającego listę lotów miedzy datami
                        val intent = Intent(this@WyszukajPoDacie, ListaMiedzyDatami::class.java)
                        intent.putExtra("datawylotu", startDate)
                        intent.putExtra("datapowrotu", endDate)
                        intent.putExtra("miastowylotu", miasto)
                        startActivity(intent)
                    } else {
                        Log.d("Bledne miasto", "Nie ma takiego miasta w bazie danych")
                        val snackbar = Snackbar.make(
                            findViewById(android.R.id.content),
                            "Nie ma takiego miasta w bazie danych",
                            3000
                        )
                        snackbar.show()
                    }
                }
                else{
                    // Przejście do widoku wyświetlającego listę lotów miedzy datami
                    val intent = Intent(this@WyszukajPoDacie, ListaMiedzyDatami::class.java)
                    intent.putExtra("datawylotu", startDate)
                    intent.putExtra("datapowrotu", endDate)
                    intent.putExtra("miastowylotu", miasto)
                    startActivity(intent)
                }
            }
        })

    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}