package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.source.local.room.MovieDatabase
import com.example.core.data.source.local.room.dao.apple.AppleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java, "MOVIE.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideAppleDao(database: MovieDatabase) : AppleDao = database.appleDao()

}