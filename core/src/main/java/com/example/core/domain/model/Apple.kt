package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Apple (
    val id : String,
    val name : String,
    val author : String,
    val title : String,
    val description : String,
    val url : String,
    val urlToImage : String,
    val publishedAt : String,
    val content : String
    ) : Parcelable