package com.example.newsapp.ui.apple

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.apple.AppleUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
class AppleViewModel @ViewModelInject constructor(
    private val appleUsecase: AppleUsecase) : ViewModel()  {

    val searchQuery = MutableStateFlow("")

    private val appleFlow = searchQuery.flatMapLatest {
        appleUsecase.getSearchApple(it)
    }

    val search = appleFlow.asLiveData()

    fun getApple(type: String, apiKey: String) =
        appleUsecase.getApple(type, apiKey).asLiveData()

}