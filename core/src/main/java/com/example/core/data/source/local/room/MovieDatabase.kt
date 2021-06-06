package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.apple.AppleEntity
import com.example.core.data.source.local.room.dao.apple.AppleDao

@Database(
    entities = [
        AppleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun appleDao(): AppleDao

}