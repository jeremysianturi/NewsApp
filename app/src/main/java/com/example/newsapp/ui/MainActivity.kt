package com.example.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.apple.AppleActivity
import com.example.newsapp.ui.tesla.TeslaActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var type : String
    private lateinit var apiKey : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onclick()

    }

    private fun onclick() {
        binding.apply {
            apiKey = "05c8d007a3cc4209a1e4493cbb2e3388"
            // apple
            buttonApple.setOnClickListener {
                type = "apple"
                val mIntent = Intent(this@MainActivity, AppleActivity::class.java)
                mIntent.putExtra("type",type)
                mIntent.putExtra("api_key",apiKey)
                startActivity(mIntent)
            }

        }
    }

}