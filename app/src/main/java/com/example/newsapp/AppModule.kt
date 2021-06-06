package com.example.newsapp

import com.example.core.domain.usecase.apple.AppleInteractor
import com.example.core.domain.usecase.apple.AppleUsecase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideAppleUsecase(appleInteractor: AppleInteractor) : AppleUsecase

}