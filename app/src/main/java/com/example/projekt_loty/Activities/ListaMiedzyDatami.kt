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

class ListaMiedzyDatami : AppCompatActivity() {

    private lateinit var viewModel: LotyViewModal
    private lateinit var LotyAdapter: LotyRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var listalotow: LiveData<List<Loty>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listamiedzydatami)

        // przepisanie wartosci z aktywności WyszukajPoDacie
        val datawylotu = intent.getStringExtra("datawylotu")
        val datapowrotu = intent.getStringExtra("datapowrotu")

        // podpinamy viewModel do aktywności
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(LotyViewModal::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val lotyRVAdapter = LotyRVAdapter(this)
        LotyAdapter = lotyRVAdapter
        recyclerView.adapter = LotyAdapter

        listalotow = viewModel.getAllFlights()
        listalotow.observe(this) { list ->
            list?.let {
                LotyAdapter.updateList(it)
                val ile = LotyAdapter.itemCount
                Log.d("ListaMiedzyDatami", "Ilosc rekordow: $ile")
            }
        }

    }
}