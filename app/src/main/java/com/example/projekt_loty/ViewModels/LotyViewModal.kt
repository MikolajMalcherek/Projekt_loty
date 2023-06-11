package com.example.projekt_loty.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.projekt_loty.Room.BazaLotow
import com.example.projekt_loty.Room.Loty
import com.example.projekt_loty.Room.LotyDao
import com.example.projekt_loty.Room.LotyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat



// Tego ModelView uzywamy w MainActivity zeby otrzymac dane z bazy

class LotyViewModal(application: Application) : AndroidViewModel(application) {
    val wszystkieLoty: LiveData<List<Loty>>
    val repository: LotyRepository
    val ilelotow: LiveData<Int>


    init {
        val dao = BazaLotow.getDatabase(application).getFlightsDao()
        repository = LotyRepository(dao)
        ilelotow = repository.ilerekordow
        wszystkieLoty = repository.wszystkieLoty
    }

    fun usunLot(loty: Loty) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(loty)
    }

    fun edytujLot(loty: Loty) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(loty)
    }

    fun dodajLot(loty: Loty) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(loty)
    }

    fun usunwszystko() = viewModelScope.launch(Dispatchers.IO) {
        repository.ClearDb()
    }

    fun getAllFlights(): LiveData<List<Loty>> {
        return repository.getAllFlights()
    }




    fun sprawdzBazeDanych() {
        viewModelScope.launch(Dispatchers.IO) {
            val dataWylotu = listOf(
                "20.06.2023", "21.06.2023", "22.06.2023", "23.06.2023", "24.06.2023",
                "25.06.2023", "26.06.2023", "27.06.2023", "28.06.2023", "29.06.2023",
                "30.06.2023", "01.07.2023", "02.07.2023", "03.07.2023", "04.07.2023",
                "05.07.2023", "06.07.2023", "07.07.2023", "08.07.2023", "09.07.2023",
                "10.07.2023", "11.07.2023", "12.07.2023", "13.07.2023", "14.07.2023",
                "15.07.2023", "16.07.2023", "17.07.2023", "18.07.2023", "19.07.2023",
                "20.07.2023", "21.07.2023", "22.07.2023", "23.07.2023", "24.07.2023",
                "25.07.2023", "26.07.2023", "27.07.2023", "28.07.2023", "29.07.2023",
                "30.07.2023", "31.07.2023", "01.08.2023", "02.08.2023", "03.08.2023",
                "04.08.2023", "05.08.2023", "06.08.2023", "07.08.2023", "08.08.2023"
            )

            val dataPowrotu = listOf(
                "22.06.2023", "23.06.2023", "24.06.2023", "26.06.2023", "27.06.2023",
                "29.06.2023", "01.07.2023", "04.07.2023", "06.07.2023", "09.07.2023",
                "11.07.2023", "13.07.2023", "16.07.2023", "18.07.2023", "21.07.2023",
                "23.07.2023", "25.07.2023", "28.07.2023", "30.07.2023", "02.08.2023",
                "04.08.2023", "07.08.2023", "09.08.2023", "12.08.2023", "14.08.2023",
                "16.08.2023", "19.08.2023", "21.08.2023", "24.08.2023", "26.08.2023",
                "29.08.2023", "31.08.2023", "03.09.2023", "05.09.2023", "08.09.2023",
                "10.09.2023", "12.09.2023", "15.09.2023", "17.09.2023", "20.09.2023",
                "22.09.2023", "25.09.2023", "27.09.2023", "30.09.2023", "02.10.2023",
                "04.10.2023", "07.10.2023", "09.10.2023", "12.10.2023", "14.10.2023"
            )

            val miastaWylotu = listOf(
                "Poznan", "Wroclaw", "Gdansk", "Warszawa", "Radom",
                "Szczecin", "Krakow", "Katowice", "Wroclaw", "Szczecin",
                "Poznan", "Warszawa", "Gdansk", "Radom", "Krakow",
                "Katowice", "Poznan", "Gdansk", "Wroclaw", "Krakow",
                "Warszawa", "Radom", "Katowice", "Szczecin", "Poznan",
                "Wroclaw", "Gdansk", "Katowice", "Warszawa", "Szczecin",
                "Radom", "Krakow", "Poznan", "Gdansk", "Szczecin",
                "Wroclaw", "Radom", "Warszawa", "Krakow", "Katowice",
                "Poznan", "Gdansk", "Wroclaw", "Radom", "Szczecin",
                "Krakow", "Warszawa", "Katowice", "Poznan", "Gdansk"
            )

            val miastaDocelowe = listOf(
                "Londyn", "Paryz", "Frankfurt", "Amsterdam", "Madryt",
                "Barcelona", "Rzym", "Ateny", "Dublin", "Wieden",
                "Lizbona", "Berlin", "Monachium", "Zurych", "Sztokholm",
                "Kopenhaga", "Oslo", "Helsinki", "Bruksela", "Wiedeń",
                "Luksemburg", "Ryga", "Tallin", "Wilno", "Praga",
                "Budapeszt", "Warszawa", "Krakow", "Katowice", "Gdansk",
                "Porto", "Lizbona", "Faro", "Neapol", "Mediolan",
                "Piza", "Neapol", "Florencja", "Sycylia", "Neapol",
                "Rzym", "Wenecja", "Neapol", "Palermo", "Ateny",
                "Saloniki", "Kreta", "Kos", "Rodos", "Larnaka",
                "Berlin", "Monachium", "Larnaka", "Kreta", "Palma de Mallorca"
            )

            val panstwaDocelowe = listOf(
                "Wielka Brytania", "Francja", "Niemcy", "Holandia", "Hiszpania",
                "Hiszpania", "Włochy", "Grecja", "Irlandia", "Austria",
                "Portugalia", "Niemcy", "Niemcy", "Szwajcaria", "Szwecja",
                "Dania", "Norwegia", "Finlandia", "Belgia", "Austria",
                "Luksemburg", "Łotwa", "Estonia", "Litwa", "Czechy",
                "Węgry", "Polska", "Polska", "Polska", "Polska",
                "Portugalia", "Portugalia", "Portugalia", "Włochy", "Włochy",
                "Włochy", "Włochy", "Włochy", "Włochy", "Grecja",
                "Grecja", "Grecja", "Grecja", "Cypr", "Cypr",
                "Niemcy", "Niemcy", "Cypr", "Grecja", "Hiszpania"
            )

            val cenyLotow = listOf(
                324, 576, 789, 246, 463,
                852, 175, 632, 954, 381,
                517, 283, 647, 425, 198,
                732, 579, 348, 627, 932,
                419, 584, 713, 245, 378,
                563, 961, 294, 819, 487,
                653, 917, 826, 579, 351,
                768, 423, 195, 672, 513,
                842, 395, 548, 731, 967,
                613, 269, 486, 728, 547
            )

            for (i in 0 until dataWylotu.size) {
                val lot = Loty(
                    KrajWylotu = "Polska",
                    MiastoWylotu = miastaWylotu[i],
                    DataWylotu = formatToDateFormat(dataWylotu[i]),
                    DataPowrotu = formatToDateFormat(dataPowrotu[i]),
                    MiastoDocelowe = miastaDocelowe[i],
                    KrajDocelowy = panstwaDocelowe[i],
                    Cena = cenyLotow[i]
                )
                dodajLot(lot)
            }

        }
    }

    fun formatToDateFormat(dateString: String): Date {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.parse(dateString)
    }

    private fun formatDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

}