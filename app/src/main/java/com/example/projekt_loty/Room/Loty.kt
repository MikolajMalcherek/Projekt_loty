package com.example.projekt_loty.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import java.text.SimpleDateFormat

@Entity(tableName = "TabelaLotow")
class Loty
    (@ColumnInfo(name = "KrajWylotu")val KrajWylotu:String,
     @ColumnInfo(name = "MiastoWylotu")val MiastoWylotu:String,
    @ColumnInfo(name = "DataWylotu")val DataWylotu:Date,
    @ColumnInfo(name = "DataPowrotu")val DataPowrotu:Date,
    @ColumnInfo(name = "MiastoDocelowe")val MiastoDocelowe:String,
     @ColumnInfo(name = "KrajDocelowy")val KrajDocelowy:String,
     @ColumnInfo(name = "Bezpieczenstwo")val Bezpieczenstwo:String,
     @ColumnInfo(name = "Cena")val Cena:Int

) {
    @PrimaryKey(autoGenerate = true)
    var id = 0


    override fun toString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return "Lot: Kraj wylotu=$KrajWylotu, Miasto wylotu=$MiastoWylotu, " +
                "Data wylotu=${dateFormat.format(DataWylotu)}, " +
                "Data powrotu=${dateFormat.format(DataPowrotu)}, " +
                "Miasto docelowe=$MiastoDocelowe, Kraj docelowy=$KrajDocelowy, Cena=$Cena"
    }
}