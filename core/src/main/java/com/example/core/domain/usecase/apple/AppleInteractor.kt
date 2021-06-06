package com.example.core.domain.usecase.apple

import com.example.core.data.Resource
import com.example.core.data.repository.AppleRepository
import com.example.core.domain.model.Apple
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppleInteractor @Inject constructor(private val appleRepository : AppleRepository) : AppleUsecase {

    override fun getApple(type: String, apiKey: String): Flow<Resource<List<Apple>>> =
        appleRepository.getApple(type,apiKey)

    override fun getSearchApple(search: String): Flow<List<Apple>> =
        appleRepository.getSearchApple(search)

}