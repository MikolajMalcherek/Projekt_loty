package com.example.projekt_loty.Room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt_loty.R
import android.util.Log


class LotyRVAdapter(val context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    private val wszystkieloty = ArrayList<Loty>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.jedenlot,parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.wylot.text = wszystkieloty[position].DataWylotu.toString()
        holder.powrot.text = wszystkieloty[position].DataPowrotu.toString()
        holder.krajpodrozy.text = wszystkieloty[position].KrajWylotu
        holder.miastowylotu.text = wszystkieloty[position].MiastoWylotu
        holder.miastodocelowe.text = wszystkieloty[position].MiastoDocelowe
        holder.krajdocelowy.text = wszystkieloty[position].KrajDocelowy
        holder.cena.text = wszystkieloty[position].Cena.toString()

    }

    override fun getItemCount(): Int {
        return wszystkieloty.size
    }


    fun updateList(newList: List<Loty>) {
        wszystkieloty.clear()
        wszystkieloty.addAll(newList)
        notifyDataSetChanged()

        // Dodaj ten wiersz logowania, aby sprawdzić zawartość listy.
        Log.d("LotyRVAdapter", "Nowa lista: $wszystkieloty")
    }

}

// Tutaj zmienne po .id są brane z pliku jedenlot.xml
class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
    val wylot: TextView = view.findViewById(R.id.wylot)
    val powrot: TextView = view.findViewById(R.id.powrot)
    val miastowylotu: TextView = view.findViewById(R.id.miastowylotu)
    val krajpodrozy: TextView = view.findViewById(R.id.krajpodrozy)
    val krajdocelowy: TextView = view.findViewById(R.id.krajdocelowy)
    val miastodocelowe: TextView = view.findViewById(R.id.miastodocelowe)
    val cena: TextView = view.findViewById(R.id.cena)
}