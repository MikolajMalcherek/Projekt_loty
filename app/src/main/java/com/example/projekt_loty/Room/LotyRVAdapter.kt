package com.example.projekt_loty.Room

import android.content.Context
import android.icu.text.IDNA.Info
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import com.example.projekt_loty.R
import android.util.Log


class LotyRVAdapter(val context: Context, val MiastoInfoInterface: MiastoInfoInterface) : RecyclerView.Adapter<LotyRVAdapter.MyViewHolder>() {
    private val wszystkieloty = ArrayList<Loty>()

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val wylot: TextView = view.findViewById(R.id.wylot)
        val powrot: TextView = view.findViewById(R.id.powrot)
        val miastowylotu: TextView = view.findViewById(R.id.miastowylotu)
        val krajpodrozy: TextView = view.findViewById(R.id.krajpodrozy)
        val krajdocelowy: TextView = view.findViewById(R.id.krajdocelowy)
        val miastodocelowe: TextView = view.findViewById(R.id.miastodocelowe)
        val cena: TextView = view.findViewById(R.id.cena)

        // Przycisk przejscia do aktywnosci MiastoInfo.kt
        val MiastoInfoButton: Button = view.findViewById(R.id.infoOMiescieButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.jedenlot,parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.wylot.text = "Data wylotu:" + wszystkieloty[position].DataWylotu.toString()
        holder.powrot.text = "Data powrotu: " + wszystkieloty[position].DataPowrotu.toString()
        holder.krajpodrozy.text = "Kraj wylotu: " +wszystkieloty[position].KrajWylotu
        holder.miastowylotu.text = "Miasto wylotu: " +wszystkieloty[position].MiastoWylotu
        holder.miastodocelowe.text = "Miasto docelowe: " +wszystkieloty[position].MiastoDocelowe
        holder.krajdocelowy.text = "Kraj docelowy: " +wszystkieloty[position].KrajDocelowy
        holder.cena.text = "Cena: " +wszystkieloty[position].Cena.toString() + " zl"

        holder.MiastoInfoButton.setOnClickListener {
            MiastoInfoInterface.onMiasoInfoButtonClick(wszystkieloty.get(position))
        }

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

interface MiastoInfoInterface {
    fun onMiasoInfoButtonClick(loty: Loty)
}




