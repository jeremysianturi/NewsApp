package com.example.core.domain.usecase.apple

import com.example.core.data.Resource
import com.example.core.domain.model.Apple
import kotlinx.coroutines.flow.Flow

interface AppleUsecase {

    fun getApple(type: String, apiKey: String): Flow<Resource<List<Apple>>>

    fun getSearchApple(search: String): Flow<List<Apple>>

}