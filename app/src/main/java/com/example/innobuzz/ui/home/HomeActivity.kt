package com.example.innobuzz.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.innobuzz.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
    }
}