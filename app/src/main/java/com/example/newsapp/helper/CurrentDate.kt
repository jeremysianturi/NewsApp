package com.example.newsapp.helper

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object CurrentDate {

    fun getToday() : String {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val answer: String = current.format(formatter)
            answer

        } else {
            val date = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val answer: String = formatter.format(date)
            answer

        }
    }
}