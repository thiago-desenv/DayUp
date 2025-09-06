package com.example.dayup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.dayup.ui.DayUpApp
import com.example.dayup.viewmodel.DayUpViewModel
import com.example.dayup.viewmodel.DayUpViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : ComponentActivity() {
    private val dayUpViewModel: DayUpViewModel by viewModels {
        DayUpViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        enableEdgeToEdge()
        setContent {
            DayUpApp(viewModel = dayUpViewModel)
        }
    }
}