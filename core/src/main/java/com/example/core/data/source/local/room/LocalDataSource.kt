package com.example.core.data.source.local.room

import com.example.core.data.source.local.entity.apple.AppleEntity
import com.example.core.data.source.local.room.dao.apple.AppleDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor (
    private val mAppleDao: AppleDao,
){

    // apple
    fun getApple(): Flow<List<AppleEntity>> = mAppleDao.getApple()
    suspend fun insertApple(apple : List<AppleEntity>) = mAppleDao.insertAndDeleteApple(apple)
    fun getSearchApple(search: String): Flow<List<AppleEntity>> = mAppleDao.getSearchApple(search)

}