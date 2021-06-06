package com.example.newsapp.ui.tesla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityAppleBinding
import com.example.newsapp.databinding.ActivityTeslaBinding

class TeslaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeslaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeslaBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}