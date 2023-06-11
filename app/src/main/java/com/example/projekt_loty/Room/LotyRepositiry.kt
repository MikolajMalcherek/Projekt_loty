package com.example.projekt_loty.Room

import androidx.lifecycle.LiveData

class LotyRepository(private val lotyDao: LotyDao) {

    //zdobadz wszystkie loty
    val wszystkieLoty: LiveData<List<Loty>> = lotyDao.getAllFlights()
    val ilerekordow: LiveData<Int> = lotyDao.getRowCount()

    suspend fun insert(loty: Loty){
        lotyDao.insert(loty)
    }

    suspend fun delete(loty: Loty){
        lotyDao.delete(loty)
    }

    suspend fun update(loty: Loty){
        lotyDao.update(loty)
    }

    suspend fun ClearDb(){
        lotyDao.ClearAll()
    }

    fun getAllFlights(): LiveData<List<Loty>> {
        return lotyDao.getAllFlights()
    }
}