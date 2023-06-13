package com.example.projekt_loty.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Loty::class), version = 9, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BazaLotow :RoomDatabase() {
    abstract fun getFlightsDao(): LotyDao

    companion object{

        @Volatile
        private var INSTANCE: BazaLotow? = null

        fun getDatabase(context: Context): BazaLotow {
            // Jeśli INSTANCE nie jest nullem to zwróć
            // Jeśli jest nullem, to utwórz nową bazę
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BazaLotow::class.java,
                    "baza_lotow"
                // tutaj pozwalam na destruktywne migracje, czyli po kazdej migracji dane sa usuwane i tworzona jest nowa, pusta baza danych
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}