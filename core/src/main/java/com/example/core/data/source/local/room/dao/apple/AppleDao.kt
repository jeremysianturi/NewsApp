package com.example.core.data.source.local.room.dao.apple

import androidx.room.*
import com.example.core.data.source.local.entity.apple.AppleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppleDao {

    @Query("SELECT * FROM apple")
    fun getApple(): Flow<List<AppleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApple(apple: List<AppleEntity>)

    @Query("DELETE FROM apple")
    suspend fun deleteApple()

    @Transaction
    suspend fun insertAndDeleteApple(content : List<AppleEntity>) {
        deleteApple()
        insertApple(content)
    }

    @Transaction
    @Query("SELECT * FROM apple where title LIKE '%'|| :search || '%'")
    fun getSearchApple(search: String): Flow<List<AppleEntity>>

}