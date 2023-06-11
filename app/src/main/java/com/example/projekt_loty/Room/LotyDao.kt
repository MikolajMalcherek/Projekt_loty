package com.example.projekt_loty.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.Date

@Dao
interface LotyDao {

    // Jeżeli przy wpisywaniu do bazy pojawią się dwa takie same ID to ignorujemy to
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(lot: Loty)

    @Update
    fun update(lot: Loty)

    @Delete
    fun delete(lot: Loty)

    @Query("Select * from TabelaLotow order by id ASC")
    fun getAllFlights(): LiveData<List<Loty>>

    // Policz ile jest rekordów w bazie
    @Query("SELECT COUNT(*) FROM TabelaLotow")
    fun getRowCount(): LiveData<Int>

    // Wyszukaj loty miedzy podanymi datami
    @Query("Select * from TabelaLotow Where DataWylotu >= :startDate And DataPowrotu <= :endDate ")
    fun wyszukajPoDacie(startDate: Date, endDate: Date): LiveData<List<Loty>>

    // Usun wszystkie rekordy z bazy
    // Policz ile jest rekordów w bazie
    @Query("DELETE FROM TabelaLotow")
    fun ClearAll()
}