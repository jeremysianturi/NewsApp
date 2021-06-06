package com.example.core.data.source.local.entity.apple

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "apple")
data class AppleEntity (

//    @PrimaryKey(autoGenerate = true)
//    @NotNull
//    @ColumnInfo(name = "object_identifier")
//    val objectIdentifier: Int,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = "content")
    val content: String,

        )