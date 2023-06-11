package com.example.projekt_loty.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.projekt_loty.R
import com.example.projekt_loty.ViewModels.LotyViewModal








class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LotyViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(LotyViewModal::class.java)

        //viewModel = ViewModelProvider
        //    .AndroidViewModelFactory
        //    .getInstance(application)
        //    .create(LotyViewModal::class.java)

        viewModel.ilelotow.observe(this, { liczbaRekordow ->
            if (liczbaRekordow != null && liczbaRekordow > 0) {
                // Baza danych zawiera rekordy, można wykonać odpowiednie działania
                val niejestpusta = "Baza ma rekordy"
                println(niejestpusta)
                //viewModel.usunwszystko()
            } else {
                // Baza danych jest pusta, można podjąć odpowiednie działania
                val jestpusta = "Baza jest pusta, dodaje dane..."
                println(jestpusta)
                viewModel.sprawdzBazeDanych()
            }
        })


        viewModel.ilelotow.observe(this, { value ->
            // Tutaj otrzymujesz wartość liczby rekordów
            val tekst = "Wartość: $value"

            val toast = Toast.makeText(applicationContext, tekst, Toast.LENGTH_SHORT)
            toast.show()
        })











        val buttonSearchFlights = findViewById<Button>(R.id.buttonSearchFlights)
        buttonSearchFlights.setOnClickListener(OnClickListener {
            // Obsługa zdarzenia po naciśnięciu przycisku "Wyszukaj loty po dacie"
            val intent = Intent(this, WyszukajPoDacie::class.java)
            startActivity(intent)
        })


    }


}

