package com.example.projekt_loty.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt_loty.ViewModels.LotyViewModal
import com.example.projekt_loty.R
import com.example.projekt_loty.Room.Loty
import com.example.projekt_loty.Room.LotyRVAdapter
import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat
import android.widget.Toast
import android.os.Handler
import android.os.Looper
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import com.example.projekt_loty.Room.MiastoInfoInterface

class ListaMiedzyDatami : AppCompatActivity(), MiastoInfoInterface {

    private lateinit var viewModel: LotyViewModal
    private lateinit var LotyAdapter: LotyRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var listalotow: LiveData<List<Loty>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listamiedzydatami)

        // przepisanie wartosci z aktywności WyszukajPoDacie
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val datawylotuString = intent.getStringExtra("datawylotu")
        val datapowrotuString = intent.getStringExtra("datapowrotu")


        val miastowylotu = intent.getStringExtra("miastowylotu")

        // podpinamy viewModel do aktywności
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(LotyViewModal::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val lotyRVAdapter = LotyRVAdapter(this,this)
        LotyAdapter = lotyRVAdapter
        recyclerView.adapter = LotyAdapter

        if(datawylotuString == "" || datapowrotuString == ""){
            val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "Jedno lub oba pola dat bylo puste - wypisuje wszystkie rekordy z bazy danych",
                6000
            )
            snackbar.show()
            listalotow = viewModel.getAllFlights()
            if(datawylotuString==null) {
                Log.d("Cos jest puste", "Puste: Data Wylotu")
            }
            if(datapowrotuString==null){
                Log.d("Cos jest puste", "Puste: Data Powrotu")
            }
            if(miastowylotu==null){
                Log.d("Cos jest puste", "Puste: Miasto wylotu")
            }
        }
        else if(miastowylotu != null && datapowrotuString != null && datawylotuString != null) {
            if(miastowylotu == ""){
                val snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    "Pole miasta wylotu jest puste - wyświetlam loty spełniające kryterium dat z wszystkich miast",
                    6000
                )
                snackbar.show()
            }
            val datawylotu: Date = dateFormat.parse(datawylotuString)
            val datapowrotu: Date = dateFormat.parse(datapowrotuString)
            Log.d("DataWylotu", "Data wylotu: $datawylotu")
            Log.d("Data Powrotu", "Data powrotu: $datapowrotu")
            Log.d("Miasto wylotu", "Miasto wylotu: $miastowylotu")
            listalotow = viewModel.szukajMiedzyDatami(startDate = datawylotu, endDate = datapowrotu, miastowylotu = miastowylotu)
        }

        listalotow.observe(this) { list ->
            list?.let {
                LotyAdapter.updateList(it)
                val ile = LotyAdapter.itemCount
                Log.d("ListaMiedzyDatami", "Ilosc rekordow: $ile")
            }
        }

    }

    override fun onMiasoInfoButtonClick(loty: Loty) {
        val intent = Intent(this, MiastoInfo::class.java)
        intent.putExtra("Miasto",loty.MiastoDocelowe)
        startActivity(intent)
        this.finish()
    }

}