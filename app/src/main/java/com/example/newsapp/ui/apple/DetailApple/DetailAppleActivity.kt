package com.example.newsapp.ui.apple.DetailApple

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.domain.model.Apple
import com.example.newsapp.databinding.ActivityDetailAppleBinding

class DetailAppleActivity : AppCompatActivity() {

    private val tag = DetailAppleActivity::class.java.simpleName
    private lateinit var binding : ActivityDetailAppleBinding

    var dataApple: Apple? = null
    private lateinit var url : String

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAppleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get data intent
        dataApple = intent.getParcelableExtra<Apple>(EXTRA_DATA)
        val detailDescription = dataApple?.description
        url = dataApple?.url ?: ""

        // set description text
        binding.detailDescription.text = detailDescription

        // onclick
        onclick()
    }

    private fun onclick() {
        binding.apply {
            btnWebview.setOnClickListener {
                val webViewUrl = url
                val intent1 = Intent(this@DetailAppleActivity, Webview::class.java)
                intent1.putExtra("url", webViewUrl)
                startActivity(intent1)
                println("print webview url : $webViewUrl")
            }
        }
    }
}