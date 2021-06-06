package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.domain.model.Apple
import kotlinx.coroutines.flow.Flow

interface IAppleRepository {

    fun getApple(type: String, apiKey: String ) : Flow<Resource<List<Apple>>>

    fun getSearchApple(search: String): Flow<List<Apple>>

}