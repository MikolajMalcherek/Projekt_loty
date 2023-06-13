package com.example.projekt_loty.Activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projekt_loty.R
import com.example.projekt_loty.ViewModels.LotyViewModal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope

class MiastoInfo : AppCompatActivity() {

    private lateinit var viewModel: LotyViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.miastoinfo)

        val miasto = intent.getStringExtra("Miasto")
        val bezpieczenstwoTextView: TextView = findViewById(R.id.textViewBezpieczenstwo)
        val miastoTextView: TextView = findViewById(R.id.textViewMiasto)
        val imageViewIcon = findViewById<ImageView>(R.id.imageViewIcon)

        // podpinamy viewModel do aktywności
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(LotyViewModal::class.java)

        if (miasto == null || miasto == "") {
            Log.d("Miasto bezpieczenstwo", "Nazwa miasta jest pusta")
        } else {
            lifecycleScope.launch {
                val stopienBezpieczenstwa = withContext(Dispatchers.IO) {
                    // Wykonaj operacje związane z bazą danych na wątku tła
                    viewModel.stopienBezpieczenstwa(miasto)
                }

                when (stopienBezpieczenstwa) {
                    "bezpieczne" -> imageViewIcon.setImageResource(R.drawable.safe)
                    "umiarkowane" -> imageViewIcon.setImageResource(R.drawable.middlesafe)
                    "niebezpieczne" -> imageViewIcon.setImageResource(R.drawable.notsafe)
                }

                bezpieczenstwoTextView.append(stopienBezpieczenstwa)
                miastoTextView.append(miasto)
            }

        }
    }
}