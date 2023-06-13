package com.example.projekt_loty.Activities

import android.os.Bundle
import com.example.projekt_loty.R
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.projekt_loty.ViewModels.LotyViewModal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import com.google.android.material.snackbar.Snackbar


// klasa obsługująca logikę widoku wpiszmiasto.xml, ktore bedzie uruchamialo aktywność MiastoInfo
class WpiszMiasto : AppCompatActivity() {

    private lateinit var viewModel: LotyViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wpiszmiasto)

        val zatwierdzButton: Button = findViewById(R.id.buttonZaatwierdz)
        val miastoEditText: EditText = findViewById(R.id.editWpiszMiasto)
        val mainContainer: View = findViewById(R.id.mainContainer)

        // podpinamy viewModel do aktywności
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(LotyViewModal::class.java)

        mainContainer.setOnClickListener {
            // Ukrycie klawiatury po kliknięciu pustej przestrzeni na ekranie
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }

        zatwierdzButton.setOnClickListener(OnClickListener {
            val miasto = miastoEditText.text.toString()

            // Rozpoczęcie asynchronicznej pracy
            lifecycleScope.launch {
                // Wykonanie funkcji viewModel.ilemiast(miasto) w wątku tła
                val iloscMiast = withContext(Dispatchers.IO) {
                    viewModel.ilemiast(miasto)
                }


                // Obsługa zdarzenia po naciśnięciu przycisku "Wyszukaj loty po dacie"
                if (iloscMiast != 0) {
                    val intent = Intent(this@WpiszMiasto, MiastoInfo::class.java)
                    intent.putExtra("Miasto", miasto)
                    startActivity(intent)
                }
                else {
                    Log.d("Bledne miasto", "Nie ma takiego miasta w bazie danych")
                    val snackbar = Snackbar.make(
                        findViewById(android.R.id.content),
                        "Nie ma takiego miasta w bazie danych",
                        3000
                    )
                    snackbar.show()
                }

            }
        })

    }
}